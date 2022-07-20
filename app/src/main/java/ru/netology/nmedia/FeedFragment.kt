package ru.netology.nmedia

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.post_list.view.*
import ru.netology.nmedia.PostFragment.Companion.KEY_ID
import ru.netology.nmedia.adapter.PostAdapter
import ru.netology.nmedia.databinding.FragmentFeedBinding
import ru.netology.nmedia.viewModel.PostViewModel

class FeedFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentFeedBinding.inflate(
            inflater,
            container,
            false
        )

        val viewModel: PostViewModel by viewModels(ownerProducer = ::requireParentFragment)

        val adapter = PostAdapter(viewModel)
        binding.postRecyclerView.adapter = adapter
        viewModel.data.observe(viewLifecycleOwner) { posts ->            // подписались на Livedata  в viewModel
            adapter.submitList(posts)
        }
        val image = registerForActivityResult(ActivityResultContracts.OpenDocument()) {
            Snackbar.make(binding.root, it.toString(), Snackbar.LENGTH_LONG).show()
            binding.postRecyclerView.image.setImageURI(it)
        }
        val video = registerForActivityResult(ActivityResultContracts.OpenDocument()) {
            Snackbar.make(binding.root, it.toString(), Snackbar.LENGTH_LONG).show()
            binding.postRecyclerView.video.setImageURI(it)
        }
        binding.fab?.setOnClickListener {
            viewModel.onAddClicked()
//            video.launch(arrayOf("video/*"))
//            image.launch(arrayOf("image/*"))
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

//        binding.fab?.setOnClickListener {
//            findNavController().navigate(R.id.action_feedFragment_to_newPostFragment)
//        }

        viewModel.sharePostContent.observe(viewLifecycleOwner) { postContent ->
            val intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, postContent)
                type = "text/plain"
            }

            val shareIntent =
                Intent.createChooser(intent, getString(R.string.chooser_share_post))
            startActivity(shareIntent)
        }

        setFragmentResultListener(
            requestKey = NewPostFragment.REQUEST_KEY_CONTENT
        ) { requestKey, bundle ->
            if (requestKey != NewPostFragment.REQUEST_KEY_CONTENT) return@setFragmentResultListener
            val newPostContent =
                bundle.getString(NewPostFragment.RESULT_KEY_CONTENT)
                    ?: return@setFragmentResultListener
            val newPostVideo =
                bundle.getString(NewPostFragment.RESULT_KEY_CONTENT)
                    ?: return@setFragmentResultListener
            viewModel.onSaveButtonClicked(newPostContent, newPostVideo)
        }

        viewModel.navigateToPostContentScreenEvent.observe(viewLifecycleOwner) { initialContent ->
            val direction =
                FeedFragmentDirections.actionFeedFragmentToNewPostFragment(initialContent)
            findNavController().navigate(direction)
        }

        viewModel.openPostContent.observe(viewLifecycleOwner) {
            findNavController().navigate(R.id.action_feedFragment_to_postFragment,
                Bundle().apply {
                    putLong(KEY_ID, it)
                }
            )
        }
        return binding.root
    }
}


























