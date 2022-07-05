package ru.netology.nmedia.adapter

import ru.netology.nmedia.Post

interface PostInteractionListener {
    fun onLikeClicked(post: Post)
    fun onShareClicked(post: Post)
    fun onRemoveClicked(post: Post)
    fun onVisibleClicked(post: Post)
    fun onEditeClicked(post: Post)
}