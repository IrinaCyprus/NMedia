package ru.netology.nmedia

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.edit
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.post_list.*
import ru.netology.nmedia.adapter.PostAdapter
import ru.netology.nmedia.data.Post
import ru.netology.nmedia.databinding.ActivityMainBinding
import ru.netology.nmedia.viewModel.PostViewModel

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        run {
//            val preferenses = getPreferences(Context.MODE_PRIVATE)
//            preferenses.edit {
//                putString("key", "value")
//            }
//        }
//
//        run {
//            val preferenses = getPreferences(Context.MODE_PRIVATE)
//            val value = preferenses.getString("key", "no value")
//            if (value == null) return@run
//            Snackbar.make(binding.root, value, Snackbar.LENGTH_INDEFINITE).show()
//        }

        val viewModel: PostViewModel by viewModels()
        val adapter = PostAdapter(viewModel)
        binding.postRecyclerView.adapter = adapter
        viewModel.data.observe(this) { posts ->            // подписались на Livedata  в viewModel
            adapter.submitList(posts)
        }

//        binding.saveButton.setOnClickListener {
//            with(binding.contentEditText) {
//                val content = text.toString()
//                viewModel.onSaveButtonClicked(content)
//                binding.editGroup?.visibility = View.GONE
//            }
//        }
//
//        binding.editButton?.setOnClickListener {
//            viewModel.currentPost.value = null
//            binding.editGroup?.visibility = View.GONE
//        }
//
//        viewModel.currentPost.observe(this) { currentPost ->
//            with(binding.contentEditText) {
//                val content = currentPost?.content
//                setText(content)
//                if (content != null) {
//                    binding.editGroup?.visibility = View.VISIBLE
//                    binding.editTextField?.text = content
//                    requestFocus()                                 //(раздел Advanced) позволяет установить фокус на нужном компоненте
//                    showKeyboard()                                 //функция показывающая клавиатуру
//                } else {
//                    clearFocus()
//                    hideKeyboard()                                 //функция скрывающая клавиатуру
//                    binding.editGroup?.visibility = View.GONE
//                }
//            }
//        }

        viewModel.sharePostContent.observe(this) { postContent ->
            val intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, postContent)
                type = "text/plain"
            }

            val shareIntent =
                Intent.createChooser(intent, getString(R.string.chooser_share_post))
            startActivity(shareIntent)
        }

//        val activityLauncherEdit =
//            registerForActivityResult(PostContentActivity.ResultContractEdit) { post: Post ->                      //передаем результат контракта
//                post.let(viewModel::onEditeClicked)
//                    post.video?.let { viewModel.onSaveButtonClicked(post.content, it) }
//            }

        val activityLauncher =
            registerForActivityResult(PostContentActivity.ResultContract) { post: String? ->                      //передаем результат контракта
                post?.let(viewModel::onCreateNewPost)
                if (post != null) {
                    viewModel.onSaveButtonClicked(content = post, video = "url")
                }
            }

        binding.saveButton.setOnClickListener {
            activityLauncher.launch(Unit)
//            activityLauncherEdit.launch(EditPostResult)
        }

    }
}





















