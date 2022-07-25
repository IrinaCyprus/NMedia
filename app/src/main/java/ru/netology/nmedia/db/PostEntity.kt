package ru.netology.nmedia.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

// создает таблицу в БД
@Entity (tableName = "posts")
class PostEntity(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Long,

    @ColumnInfo(name = "authorName")
    val authorName: String,

    @ColumnInfo(name = "content")
    val content: String,

    @ColumnInfo(name = "published")
    val published: String,

    @ColumnInfo(name = "video")
    val video: String?,
//    val like: Int,
    @ColumnInfo(name="likedByMe")
    val likedByMe: Boolean,

    @ColumnInfo(name="sum_likes")
    val sum_likes: Int = 0,

    @ColumnInfo(name="sum_reposts")
    val sum_reposts: Int = 0,

    @ColumnInfo(name="sum_visible")
    val sum_visible: Int = 1
)
