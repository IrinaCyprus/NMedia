package ru.netology.nmedia.data

import kotlinx.serialization.Serializable

@Serializable
data class Post(
    val id: Long,
    val authorName: String,
    val content: String,
    val published: String,
    val video: String? = null,
//    val like: Int,
    val likedByMe: Boolean,
    var sum_likes: Int,
    var sum_reposts: Int,
    var sum_visible: Int
)

