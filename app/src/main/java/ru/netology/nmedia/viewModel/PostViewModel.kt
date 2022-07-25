package ru.netology.nmedia.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import ru.netology.nmedia.adapter.PostInteractionListener
import ru.netology.nmedia.data.Post
import ru.netology.nmedia.data.PostRepository
import ru.netology.nmedia.db.AppDb
import ru.netology.nmedia.impl.PostRepositoryImpl
import ru.netology.nmedia.util.SingleLiveEvent

class PostViewModel(
    application: Application
) : AndroidViewModel(application),
    PostInteractionListener {

    private val repository: PostRepository =
//        NewFilePostRepository(application)
        PostRepositoryImpl(
            dao = AppDb.getInstance(
                context = application
            ).postDao
        )

    val data by repository::data
    private val currentPost = MutableLiveData<Post?>(null)
    val sharePostContent = SingleLiveEvent<String>()
    val navigateToPostContentScreenEvent = SingleLiveEvent<String>()
    private val playVideo = SingleLiveEvent<String?>()
    val openPostContent = SingleLiveEvent<Long>()

    override fun onLikeClicked(post: Post) = repository.like(post.id)
    override fun onRemoveClicked(post: Post) = repository.delete(post.id)
    override fun onVisibleClicked(post: Post) = repository.visible()
    override fun onShareClicked(post: Post) {
        repository.repost(post.id)
        sharePostContent.value = post.content
    }

    override fun onEditeClicked(post: Post) {
        currentPost.value = post
        navigateToPostContentScreenEvent.value = post.content
    }

    override fun onPostClicked(post: Post) {
        openPostContent.value = post.id
    }

    fun onAddClicked() {
        navigateToPostContentScreenEvent.call()
    }

//    fun onCreateNewPost(post: String) {
//        currentPost.value = post
//        navigateToPostContentScreenEvent.value = post
//        playVideo.value = post
//    }

    fun onSaveButtonClicked(content: String, video: String) {
        if (content.isBlank()) return
        if (video.isBlank()) return
        val post = currentPost.value?.copy(
            content = content,
            video = video,
        ) ?: Post(
            id = PostRepository.NEW_POST_ID,
            authorName = "Me",
            content = content,
            published = " Today",
            video = video,
//            like = 0,
            likedByMe = false,
            sum_likes = 0,
            sum_reposts = 0,
            sum_visible = 1
        )
        repository.save(post)
        currentPost.value = null
    }
}

