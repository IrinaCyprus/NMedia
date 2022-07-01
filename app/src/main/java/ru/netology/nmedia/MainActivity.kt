package ru.netology.nmedia

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import ru.netology.nmedia.adapter.PostAdapter
import ru.netology.nmedia.databinding.ActivityMainBinding
import ru.netology.nmedia.viewModel.PostViewModel

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val viewModel:PostViewModel by viewModels()
        val adapter = PostAdapter(
            onLikeClicked ={post ->
               viewModel.onLikeClicked(post)
            },
            onShareListener = {post ->
                viewModel.onShareClicked(post)
            }
        )
        binding.postRecyclerView.adapter = adapter
        viewModel.data.observe(this) { posts ->            // подписались на Livedata  в viewModel
            adapter.submitList(posts)
        }
    }
}

//@DrawableRes
//fun getLikeIconResId(liked: Boolean) =
//    if (liked) R.drawable.ic_baseline_favorite_24 else R.drawable.ic_baseline_favorite_border_24
//
//@Override
//fun countView(number: Int): String {
//    return when {
//        number in 0..999 -> number.toString()
//        number < 10000 && number % 1000 < 100 -> "${(number / 1000)}K"
//        number in 1100..9999 -> "${floor((number.toDouble() / 1000) * 10) / 10}K"
//        number in 10000..999999 -> "${(number / 1000)}K"
//        number % 1000000 < 100000 -> "${(number / 1000000)}M"
//        number in 1000000..999999999 -> "${floor((number.toDouble() / 1000000) * 10) / 10}M"
//        else -> "0"
//    }
//}










