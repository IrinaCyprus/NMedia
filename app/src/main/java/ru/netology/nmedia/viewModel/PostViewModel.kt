package ru.netology.nmedia.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.DelicateCoroutinesApi
import ru.netology.nmedia.Post
import ru.netology.nmedia.adapter.PostInteractionListener
import ru.netology.nmedia.data.PostRepository
import ru.netology.nmedia.impl.InMemoryPostRepository

class PostViewModel : ViewModel(), PostInteractionListener {
    @OptIn(DelicateCoroutinesApi::class)
    val repository: PostRepository = InMemoryPostRepository()
    val data by repository::data
    val currentPost = MutableLiveData<Post?>(null)
    override fun onLikeClicked(post: Post) = repository.like(post.id)
    override fun onShareClicked(post: Post) = repository.repost(post.id)
    override fun onRemoveClicked(post: Post) = repository.delete(post.id)
    override fun onVisibleClicked(post: Post) = repository.visible()
    override fun onEditeClicked(post: Post) {
        currentPost.value = post
    }

    fun onSaveButtonClicked(content: String) {
        if (content.isBlank()) return

        val post = currentPost.value?.copy(
            content = content)?: Post(
            id = PostRepository.NEW_POST_ID,
            authorName = "Me",
            content = content,
            published = " Today",
            like = 0,
            likedByMe = false,
            sum_likes = 0,
            sum_reposts = 10,
            sum_visible = 1
        )
        repository.save(post)
        currentPost.value= null
    }
}