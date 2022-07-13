package ru.netology.nmedia.impl

import android.app.Application
import android.content.Context
import android.provider.Settings.Global.putString
import androidx.core.content.edit
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import ru.netology.nmedia.data.Post
import ru.netology.nmedia.data.PostRepository
import kotlin.properties.Delegates

class SharedPrefsPostRepository(application: Application) : PostRepository {

    private val prefs = application.getSharedPreferences("repo", Context.MODE_PRIVATE)

    private var nextId: Long by Delegates.observable(
        prefs.getLong(NEXT_ID_PREFS_KEY, 0L)
    ) { _, _, newValue ->
        prefs.edit { putLong(NEXT_ID_PREFS_KEY, newValue) }
    }

    private var posts                                //обертка mutableLiveData
        get() = checkNotNull(data.value)
        set(value) {
            prefs.edit {
                val serializedPost = Json.encodeToString(value)
                putString(POSTS_PREFS_KEY, serializedPost)
            }
            data.value = value
        }

    override val data: MutableLiveData<List<Post>>  // MutableLiveData это одна из имплементаций LiveData

    init {
        val serializedPost = prefs.getString(POSTS_PREFS_KEY, null)
        val posts: List<Post> = if (serializedPost != null) {
            Json.decodeFromString(serializedPost)
        } else emptyList()
        data = MutableLiveData(posts)
    }

    override fun like(postId: Long) {
        posts = posts.map { post ->
            if (post.id == postId && !post.likedByMe) {
                post.copy(likedByMe = !post.likedByMe, like = post.sum_likes++)
            } else if (post.id == postId && post.likedByMe) {
                post.copy(like = post.sum_likes--, likedByMe = !post.likedByMe)
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
        //        const val GENERATED_POSTS_AMOUNT = 1000
        const val POSTS_PREFS_KEY = "posts"
        const val NEXT_ID_PREFS_KEY = "posts"
    }
}