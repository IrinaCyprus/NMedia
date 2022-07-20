package ru.netology.nmedia

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import ru.netology.nmedia.adapter.PostAdapter
import ru.netology.nmedia.databinding.FragmentFeedBinding
import ru.netology.nmedia.databinding.FragmentNewPostBinding
import ru.netology.nmedia.databinding.FragmentPostBinding
import ru.netology.nmedia.viewModel.PostViewModel

class PostFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentPostBinding.inflate(inflater, container, false)
        val viewModel: PostViewModel by viewModels(ownerProducer = ::requireParentFragment)
        val viewHolder = PostAdapter.ViewHolder(binding.postFragment, viewModel)
        val postId = arguments?.getLong(KEY_ID)

//        viewModel.data.observe(viewLifecycleOwner) { initialPost ->
//            val post = initialPost.find { it.id == postId } ?: return@observe
//            viewHolder.bind(post)
//
//        }

        viewModel.data.observe(viewLifecycleOwner) { initialPost ->
            initialPost.firstOrNull { it.id == postId }?.let {
                viewHolder.bind(it)
                return@observe
            }
            findNavController().navigate(R.id.action_feedFragment_to_postFragment)
        }

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

        viewModel.navigateToPostContentScreenEvent.observe(viewLifecycleOwner) {initialPost->
            val direction = PostFragmentDirections.actionPostFragmentToNewPostFragment(initialPost)
            findNavController().navigate(direction)
        }

        return binding.root
    }

    companion object {
        const val KEY_ID = "id"
    }
}



