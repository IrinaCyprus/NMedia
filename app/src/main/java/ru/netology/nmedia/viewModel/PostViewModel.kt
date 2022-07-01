package ru.netology.nmedia.viewModel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.DelicateCoroutinesApi
import ru.netology.nmedia.Post
import ru.netology.nmedia.data.PostRepository
import ru.netology.nmedia.impl.InMemoryPostRepository

class PostViewModel : ViewModel() {
    @OptIn(DelicateCoroutinesApi::class)
    val repository: PostRepository = InMemoryPostRepository()
    val data by repository::data
    fun onLikeClicked(post: Post) = repository.like(post.id)
    fun onShareClicked(post: Post) = repository.repost(post.id)
//    fun onVisibleClicked()=repository.visible()
}