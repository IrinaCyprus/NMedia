package ru.netology.nmedia.data

import androidx.lifecycle.LiveData

interface PostRepository {
    val data: LiveData<List<Post>>
    fun like(postId: Long)
    fun repost(postId: Long)
    fun visible()
    fun delete(postId: Long)
    fun save(post: Post)
    fun update(post: String?)

    companion object {
        const val NEW_POST_ID = 0L
    }
}