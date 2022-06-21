package ru.netology.nmedia.viewModel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.DelicateCoroutinesApi
import ru.netology.nmedia.data.PostRepository
import ru.netology.nmedia.data.impl.InMemoryPostRepository

class PostViewModel : ViewModel() {
    @OptIn(DelicateCoroutinesApi::class)
    val repository: PostRepository = InMemoryPostRepository()
    val data by repository::data
    fun onLikeClicked() = repository.like()
    fun onRepostClicked() = repository.repost()
    fun onVisibleClicked()=repository.visible()
}