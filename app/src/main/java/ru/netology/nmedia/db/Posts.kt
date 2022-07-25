package ru.netology.nmedia.db

import android.database.Cursor
import ru.netology.nmedia.data.Post

//  mapping

internal fun PostEntity.toModel() = Post(
    id = id,
    authorName = authorName,
    content = content,
    published = published,
    video = video,
//    like = like,
    likedByMe = likedByMe,
    sum_likes = sum_likes,
    sum_reposts = sum_reposts,
    sum_visible = sum_visible
)

//  mapping  в другую сторону
internal fun Post.toEntity() = PostEntity(
    id = id,
    authorName = authorName,
    content = content,
    published = published,
    video = video,
//    like = like,
    likedByMe = likedByMe,
    sum_likes = sum_likes,
    sum_reposts = sum_reposts,
    sum_visible = sum_visible
)