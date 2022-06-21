package ru.netology.nmedia.data
import  ru.netology.nmedia.Post
import androidx.lifecycle.LiveData

interface PostRepository {
    val data: LiveData<Post>
    fun like()
    fun repost()
    fun visible()
}