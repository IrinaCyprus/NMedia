package ru.netology.nmedia.data.impl

import androidx.databinding.adapters.TextViewBindingAdapter.setText
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.*
import ru.netology.nmedia.Post
import ru.netology.nmedia.data.PostRepository
import java.util.*
import kotlin.math.floor

//реализация интерфейса PostRepository для хранения памяти
@DelicateCoroutinesApi
class InMemoryPostRepository : PostRepository {
    override val data = MutableLiveData(  // MutableLiveData это одна из имплементаций LiveData
        Post(
            id = 0L,
            authorName = "Res.post_name",
            content = "post",
            published = "06/06/2022",
            like = 0,
            likedByMe = false,
            sum_likes = 1,
            sum_reposts = 10,
            sum_visible = 999_999
        )
    )

    //    init {
//        GlobalScope.launch(Dispatchers.Default) {
//            while (true) {
//                delay(1000)
//                val currentPost = checkNotNull(data.value)
//                val newPost = currentPost.copy(
//                    published = Date().toString()
//                )
//                data.postValue(newPost)
//            }
//        }
//    }
    override fun like() {
        val currentPost =
            checkNotNull(data.value)  //текущий пост должен быть не равен нулю по условию
        val likedPost = currentPost.copy(
            likedByMe = !currentPost.likedByMe,
            like = if (currentPost.likedByMe) currentPost.sum_likes-- else currentPost.sum_likes++
        )
        data.value = likedPost // положили новое значение likedPost в LiveData
    }

    override fun repost() {
        val currentPost = checkNotNull(data.value)
        val repostPost = currentPost.copy(sum_reposts = currentPost.sum_reposts + 1)
        data.value = repostPost
    }

    override fun visible() {
        TODO("Not yet implemented")
    }
}
