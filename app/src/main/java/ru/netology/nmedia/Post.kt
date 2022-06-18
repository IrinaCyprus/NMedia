package ru.netology.nmedia

data class Post(
    val id: Long,
    val authorName:String,
    val content: String,
    val date: String,
    var like: Int,
    var likedByMe: Boolean,
    var sum_likes: Int,
    var sum_reposts: Int,
    var sum_visible: Int
)

