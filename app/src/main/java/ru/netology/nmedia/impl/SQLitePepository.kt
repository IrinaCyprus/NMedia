package ru.netology.nmedia.impl

import androidx.lifecycle.MutableLiveData
import ru.netology.nmedia.data.Post
import ru.netology.nmedia.data.PostRepository
//import ru.netology.nmedia.db.PostDao

//class SQLitePepository(
//    private val dao: PostDao
//) : PostRepository {
//    //    private var nextId = GENERATED_POSTS_AMOUNT.toLong()
//    private var posts
//        get() = checkNotNull(data.value)
//        set(value) {
//            data.value = value
//        }
//
//    override val data = MutableLiveData(dao.getAll())
//
//    init {
//        posts = dao.getAll()
//    }
//
//    override fun like(postId: Long) {
//        dao.likeById(postId)
//        data.value = posts.map { post ->
//            if (post.id == postId) post else post.copy(
//                likedByMe = !post.likedByMe,
//                like = if (post.likedByMe) post.sum_likes - 1 else post.sum_likes + 1
//            )
//        }
//    }
//
//    override fun repost(postId: Long) {
//        data.value = posts.map { post ->
//            if (post.id == postId) post.copy(sum_reposts = post.sum_reposts + 1)
//            else post
//        }
//    }
//
//    override fun delete(postId: Long) {
//        dao.removeById(postId)
//        data.value = posts.filterNot { it.id == postId }
//    }
//
//    override fun save(post: Post) {
//        val id = post.id
//        val saved = dao.save(post)
//        data.value = if (id == 0L) {
//            listOf(saved) + posts
//        } else {
//            posts.map {
//                if (it.id != id) it else saved
//            }
//        }
//    }
//
//
//    override fun update(post: Post) {
//        dao.save(post)
//        data.value = posts.map {
//            if (it.id == post.id) post else it
//        }
//    }
//
////    private fun insert(post: Post) {
////        data.value = listOf(
////            post.copy(id = ++nextId)
////        ) + posts
////    }
//
//    override fun visible() {
//        TODO("Not yet implemented")
//    }
//}