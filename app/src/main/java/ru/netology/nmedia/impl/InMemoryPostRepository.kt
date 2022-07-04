package ru.netology.nmedia.impl

import androidx.lifecycle.MutableLiveData
import ru.netology.nmedia.Post
import ru.netology.nmedia.data.PostRepository

//реализация интерфейса PostRepository для хранения памяти

class InMemoryPostRepository : PostRepository {
    private var nextId = GENERATED_POSTS_AMOUNT.toLong()
    private var posts                                //обертка mutableLiveData
        get() = checkNotNull(data.value)
        set(value) {
            data.value = value
        }

    override val data: MutableLiveData<List<Post>>  // MutableLiveData это одна из имплементаций LiveData

    init {
        val initialPosts = List(GENERATED_POSTS_AMOUNT) { index ->
            Post(
                id = index + 1L,
                authorName = "Нетология...",
                content = "post №${index + 1}1",
                published = "06/06/2022",
                like = 0,
                likedByMe = false,
                sum_likes = 1,
                sum_reposts = 10,
                sum_visible = 999_999
            )
        }
        data = MutableLiveData(initialPosts)
    }

    override fun like(postId: Long) {
        posts = posts.map { post ->
            if (post.id == postId && !post.likedByMe) {
                post.copy(likedByMe = !post.likedByMe, like = post.sum_likes++)
            } else if (post.id == postId && post.likedByMe) {
                post.copy(like = post.sum_likes--, likedByMe = !post.likedByMe)
            } else post
        }
    }

    override fun repost(postId: Long) {
        posts = posts.map { post ->
            if (post.id == postId) post.copy(sum_reposts = post.sum_reposts + 1)
            else post
        }
    }

    override fun delete(postId: Long) {
        data.value = posts.filterNot { it.id == postId }
    }

    override fun save(post: Post) {
        if (post.id == PostRepository.NEW_POST_ID) insert(post) else update(post)
    }

    private fun update(post: Post) {
        data.value = posts.map {
            if (it.id == post.id) post else it
        }
    }

    private fun insert(post: Post) {
        data.value = listOf(
            post.copy(id = ++nextId)
        ) + posts
    }

    private companion object {
        const val GENERATED_POSTS_AMOUNT = 1000
    }

    override fun visible() {
        TODO("Not yet implemented")
    }
}
