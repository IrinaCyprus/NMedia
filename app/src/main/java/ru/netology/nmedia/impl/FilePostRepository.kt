package ru.netology.nmedia.impl

import android.app.Application
import android.content.Context
import androidx.core.content.edit
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import ru.netology.nmedia.data.Post
import ru.netology.nmedia.data.PostRepository
import kotlin.properties.Delegates

class FilePostRepository(private val application: Application) : PostRepository {

    private val gson = Gson()
    private val type = TypeToken.getParameterized(List::class.java, Post::class.java).type
    private val prefs = application.getSharedPreferences("repo", Context.MODE_PRIVATE)
    private var nextId: Long by Delegates.observable(
        prefs.getLong(NEXT_ID_PREFS_KEY, 0L)
    ) { _, _, newValue ->
        prefs.edit { putLong(NEXT_ID_PREFS_KEY, newValue) }
    }

    private var posts
        get() = checkNotNull(data.value)
        set(value) {
            application.openFileOutput(                             //открывает OutputStream
                FILE_NAME,Context.MODE_PRIVATE
            ).bufferedWriter()                                      //позволяет получить буферизованный Writer
                .use {
                it.write(gson.toJson(value))                        //записывает данные
            }
            data.value = value
        }

    override val data: MutableLiveData<List<Post>>                   // MutableLiveData это одна из имплементаций LiveData

    init {
        val postsFile = application.filesDir.resolve(FILE_NAME)      //получает File с нужным путём
        val posts:List<Post> = if (postsFile.exists()) {             //проверяет существует ли файл (иначе при попытке чтения получим исключение)
            val inputStream = application.openFileInput(FILE_NAME)   //открывает InputStream
            val reader = inputStream.bufferedReader()                //позволяет получить буферизованный Reader
            reader.use { gson.fromJson(it,type) }                    //позволяет автоматически закрыть ресурс после использования (будет закрыт Reader, который сам закроет InputStream)
        } else emptyList()
        data = MutableLiveData(posts)
    }

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
        //        const val GENERATED_POSTS_AMOUNT = 1000
        const val POSTS_PREFS_KEY = "posts"
        const val NEXT_ID_PREFS_KEY = "posts"
        const val FILE_NAME = "posts.json"
    }
}