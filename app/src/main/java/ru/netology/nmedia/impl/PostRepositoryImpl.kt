package ru.netology.nmedia.impl

import android.provider.SyncStateContract.Helpers.insert
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import ru.netology.nmedia.data.Post
import ru.netology.nmedia.data.PostRepository
import ru.netology.nmedia.db.PostDao
import ru.netology.nmedia.db.toEntity
import ru.netology.nmedia.db.toModel

class PostRepositoryImpl(                       //репозиторий кеширует в памяти данные для ускорения доступа
    private val dao: PostDao                    //работает он c базой через DAO
) : PostRepository {
//        private var nextId = 0L
//    private val posts
//        get() = checkNotNull(data.value)

    override val data = dao.getAll().map { entities ->      //
        entities.map { it.toModel() }
    }

    override fun save(post: Post) {
        if (post.id == PostRepository.NEW_POST_ID) dao.insert(post.toEntity())
        else dao.update(post.id, post.content)
    }
    override fun like(postId: Long) {
        dao.like(postId)
//        data.value = posts.map { post ->
//            if (post.id == postId) post else post.copy(
//                likedByMe = !post.likedByMe,
//                sum_likes = if (post.likedByMe) post.sum_likes - 1 else post.sum_likes + 1
//            )
//        }
    }
    override fun repost(postId: Long) {
        dao.share(postId)
//        data.value = posts.map { post ->
//            if (post.id == postId) post.copy(sum_reposts = post.sum_reposts + 1)
//            else post
//        }
    }
    override fun delete(postId: Long) {
        dao.removeById(postId)
//        data.value = posts.filterNot { it.id == postId }
    }
    override fun update(post: Post) {
        dao.update(post.id,post.content)
//        data.value = posts.map {
//            if (it.id == post.id) post else it
//        }
    }
//    private fun insert(post: Post) {
//        data.value = listOf(
//            post.copy(id = ++nextId)
//        ) + posts
//    }
    override fun visible() {
        TODO("Not yet implemented")
    }
}