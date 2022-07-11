package ru.netology.nmedia.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.netology.nmedia.data.Post
import ru.netology.nmedia.R
import ru.netology.nmedia.databinding.PostListBinding
import kotlin.math.floor

internal class PostAdapter(
    private val interactionListener: PostInteractionListener
) : ListAdapter<Post, PostAdapter.ViewHolder>(DiffCallback) {             //буфер из ограниченного колличества вьюх и достает нужгые данные из списка

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        Log.d("PostAdapter", "onCreateViewHolder")
        val inflater = LayoutInflater.from(parent.context)               // создаем инфлейтор
        val binding = PostListBinding.inflate(
            inflater,
            parent,
            false
        )                                                              //!!! адаптер сам регулирует когда добавляет когда убират вьюхи
        return ViewHolder(binding, interactionListener)
    }

    override fun onBindViewHolder(
        holder: ViewHolder, position: Int
    ) {                                                                 //перезаполняет созданную уже вьюху
        Log.d("PostAdapter", "onBindViewHolder:$position")
        holder.bind(getItem(position))
    }

    class ViewHolder(                                                  //держит отдельную вьюху
        private val binding: PostListBinding,
        listener: PostInteractionListener
    ) : RecyclerView.ViewHolder(binding.root) {

        private lateinit var post: Post

          private val popupMenu by lazy {
            PopupMenu(itemView.context, binding.menu).apply {
                inflate(R.menu.option_post)
                setOnMenuItemClickListener { menuItem ->
                    when (menuItem.itemId) {
                        R.id.remove -> {
                            listener.onRemoveClicked(post)
                            true
                        }
                        R.id.edit -> {
                            listener.onEditeClicked(post)
                            true
                        }
                        else -> false
                    }
                }
            }
        }

        init {
            binding.like.setOnClickListener {
                listener.onLikeClicked(post)
            }
            binding.repost.setOnClickListener {
                listener.onShareClicked(post)
            }
            binding.menu.setOnClickListener{popupMenu.show()}
        }

        fun bind(post: Post) {
            this.post = post                                             //инициализируем пост

            with(binding) {
                authorName.text = post.authorName
                content.text = post.content
                published.text = post.published
                like.text = post.sum_likes.toString()
                like.isChecked = post.likedByMe
//                like.setButtonDrawable(getLikeIconResId(post.likedByMe))
                repost.text  = countView(post.sum_reposts)
                visible.text = countView(post.sum_visible)
            }
        }

//        @DrawableRes
//        fun getLikeIconResId(liked: Boolean) =
//            if (liked) R.drawable.ic_baseline_favorite_24 else R.drawable.ic_baseline_favorite_border_24

        @Override
        fun countView(number: Int): String {
            return when {
                number in 0..999 -> number.toString()
                number < 10000 && number % 1000 < 100 -> "${(number / 1000)}K"
                number in 1100..9999 -> "${floor((number.toDouble() / 1000) * 10) / 10}K"
                number in 10000..999999 -> "${(number / 1000)}K"
                number % 1000000 < 100000 -> "${(number / 1000000)}M"
                number in 1000000..999999999 -> "${floor((number.toDouble() / 1000000) * 10) / 10}M"
                else -> "0"
            }
        }
    }

    private object DiffCallback : DiffUtil.ItemCallback<Post>() {
        override fun areItemsTheSame(oldItem: Post, newItem: Post) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Post, newItem: Post) =
            oldItem == newItem
    }
}