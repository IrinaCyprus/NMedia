package ru.netology.nmedia.db

import ru.netology.nmedia.data.Post

//Data Access Object, осуществлет всю работу по запросам
interface PostDao {
    fun getAll():List<Post>
    fun save(post: Post):Post
    fun likeById(id:Long)
    fun removeById(id:Long)
    fun share(id: Long)
}