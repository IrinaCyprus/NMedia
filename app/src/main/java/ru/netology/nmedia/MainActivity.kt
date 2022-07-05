package ru.netology.nmedia

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import ru.netology.nmedia.adapter.PostAdapter
import ru.netology.nmedia.databinding.ActivityMainBinding
import ru.netology.nmedia.util.hideKeyboard
import ru.netology.nmedia.util.showKeyboard
import ru.netology.nmedia.viewModel.PostViewModel

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val viewModel: PostViewModel by viewModels()
        val adapter = PostAdapter(viewModel)
        binding.postRecyclerView.adapter = adapter
        viewModel.data.observe(this) { posts ->            // подписались на Livedata  в viewModel
            adapter.submitList(posts)
        }
        binding.saveButton.setOnClickListener {
            with(binding.contentEditText) {
                val content = text.toString()
                viewModel.onSaveButtonClicked(content)
                binding.editGroup?.visibility = View.GONE
            }
        }

        binding.editButton?.setOnClickListener {
            viewModel.currentPost.value = null
            binding.editGroup?.visibility = View.GONE
        }

        viewModel.currentPost.observe(this) { currentPost ->
            with(binding.contentEditText) {
                val content = currentPost?.content
                setText(content)
                if (content != null) {
                    binding.editGroup?.visibility = View.VISIBLE
                    binding.editTextField?.text = content
                    requestFocus()                                 //(раздел Advanced) позволяет установить фокус на нужном компоненте
                    showKeyboard()                                 //функция показывающая клавиатуру
                } else {
                    clearFocus()
                    hideKeyboard()                                 //функция скрывающая клавиатуру
                    binding.editGroup?.visibility = View.GONE
                }
            }
        }
    }
}













