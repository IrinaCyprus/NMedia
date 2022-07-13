package ru.netology.nmedia.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.DelicateCoroutinesApi
import ru.netology.nmedia.data.Post
import ru.netology.nmedia.adapter.PostInteractionListener
import ru.netology.nmedia.data.PostRepository
import ru.netology.nmedia.impl.NewFilePostRepository
import ru.netology.nmedia.util.SingleLiveEvent

class PostViewModel(
    application: Application
) : AndroidViewModel(application), PostInteractionListener {
    @OptIn(DelicateCoroutinesApi::class)
    private val repository: PostRepository = NewFilePostRepository(application)
    val data by repository::data
    private val currentPost = MutableLiveData<Post?>(null)
    val sharePostContent = SingleLiveEvent<String>()
    private val navigateToPostContentScreenEvent = SingleLiveEvent<String>()
    private val playVideo = SingleLiveEvent<String>()

    override fun onLikeClicked(post: Post) = repository.like(post.id)
    override fun onShareClicked(post: Post) {
        repository.repost(post.id)
        sharePostContent.value = post.content
    }

    override fun onRemoveClicked(post: Post) = repository.delete(post.id)
    override fun onVisibleClicked(post: Post) = repository.visible()
    override fun onEditeClicked(post: Post) {
        currentPost.value = post
//        navigateToPostContentScreenEvent.value =
//            PostContentActivity.EditPostResult(post.content, post.video).toString()
    }

//    fun onAddListener() {
//        navigateToPostContentScreenEvent.call()
//    }

    fun onCreateNewPost(post: String) {
        navigateToPostContentScreenEvent.value = post
        playVideo.value = post
    }

    fun onSaveButtonClicked(content: String, video: String) {
        if (content.isBlank()) return
        if (video.isBlank()) return
        val post = currentPost.value?.copy(
            content = content,
            video = "url"
        ) ?: Post(
            id = PostRepository.NEW_POST_ID,
            authorName = "Me",
            content = content,
            published = " Today",
            video = "url",
            like = 0,
            likedByMe = false,
            sum_likes = 0,
            sum_reposts = 0,
            sum_visible = 1
        )
        repository.save(post)
        currentPost.value = null
    }
}

