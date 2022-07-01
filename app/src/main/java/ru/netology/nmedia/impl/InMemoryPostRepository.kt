package ru.netology.nmedia.impl

import androidx.lifecycle.MutableLiveData
import ru.netology.nmedia.Post
import ru.netology.nmedia.data.PostRepository

//реализация интерфейса PostRepository для хранения памяти

class InMemoryPostRepository : PostRepository {

    private var posts                                //обертка mutableLiveData
        get() = checkNotNull(data.value)
        set(value) {
            data.value = value
        }

    override val data: MutableLiveData<List<Post>>  // MutableLiveData это одна из имплементаций LiveData

    init {
        val initialPosts = List(1000) { index ->
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

//    override fun visible() {
//        TODO("Not yet implemented")
//    }

//    like = if (currentPost.likedByMe) currentPost.sum_likes-- else currentPost.sum_likes++
//    )
//    data .value = likedPost // положили новое значение likedPost в LiveData

//override fun repost() {
//    val currentPost = checkNotNull(data.value)
//    val repostPost = currentPost.copy(sum_reposts = currentPost.sum_reposts + 1)
//    data.value = repostPost
//}

}
