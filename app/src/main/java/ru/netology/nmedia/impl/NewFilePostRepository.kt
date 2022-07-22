package ru.netology.nmedia.impl

import android.app.Application
import android.content.Context
import androidx.core.content.edit
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import ru.netology.nmedia.data.Post
import ru.netology.nmedia.data.PostRepository
import kotlin.properties.Delegates

class NewFilePostRepository(private val application: Application) : PostRepository {

    private val gson = Gson()
    private val type = TypeToken.getParameterized(List::class.java, Post::class.java).type
    private val idPref = application.getSharedPreferences("id", Context.MODE_PRIVATE)
    private var nextId = 0L

    override val data: MutableLiveData<List<Post>>

    init {
        nextId = idPref.getLong(NEXT_ID_PREFS_KEY, 0)
        val postsId = application.filesDir.resolve(FILE_NAME)
        val posts: List<Post> = if (postsId.exists()) {
            val inputStream = application.openFileInput(FILE_NAME)
            val reader = inputStream.bufferedReader()
            reader.use { gson.fromJson(it, type) }
        } else emptyList()
        data = MutableLiveData(posts)
    }

    private var posts
        get() = checkNotNull(data.value)
        set(value) {
            application.openFileOutput(
                FILE_NAME, Context.MODE_PRIVATE
            )
                .bufferedWriter()
                .use {
                    it.write(gson.toJson(value))
                }
            data.value = value
        }

//    private fun saveId() {
//        idPref.edit().apply {
//            putLong(NEXT_ID_PREFS_KEY, nextId)
//            apply()
//        }
//    }

    override fun like(postId: Long) {
        posts = posts.map { post ->
            if (post.id == postId && !post.likedByMe) {
                post.copy(likedByMe = !post.likedByMe, sum_likes = post.sum_likes++)
            } else if (post.id == postId && post.likedByMe) {
                post.copy(sum_likes = post.sum_likes--, likedByMe = !post.likedByMe)
            } else post
        }
    }

    override fun repost(postId: Long) {
        posts = posts.map { post ->
            if (post.id == postId) post.copy(sum_reposts = post.sum_reposts + 1)
            else post
        }
    }

    override fun visible() {
        TODO("Not yet implemented")
    }

    override fun delete(postId: Long) {
        posts = posts.filterNot { it.id == postId }
    }

    override fun save(post: Post) {
        if (post.id == PostRepository.NEW_POST_ID) insert(post) else update(post)
    }


    override fun update(post: Post) {
        posts = posts.map {
            if (it.id == post.id) post else it
        }
    }

    private fun insert(post: Post) {
        posts = listOf(
            post.copy(id = ++nextId)
        ) + posts
    }

    private companion object {
        const val NEXT_ID_PREFS_KEY = "id_post"
        const val FILE_NAME = "posts.json"
    }
}
