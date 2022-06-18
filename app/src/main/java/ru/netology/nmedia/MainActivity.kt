package ru.netology.nmedia

import android.os.Bundle
import androidx.annotation.DrawableRes
import androidx.appcompat.app.AppCompatActivity
import ru.netology.nmedia.databinding.ActivityMainBinding
import ru.netology.nmedia.R.id.*
import kotlin.math.floor

//import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        val likeButton = findViewById<ImageButton>(R.id.like)
//        likeButton.setOnClickListener {
//            println("Like clicked")
//        }
//        like.setOnClickListener{
//            println("Like clicked")
//        }

        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val post = Post(
            id = 0L,
            authorName = "Res.post_name",
            content = "post",
            date = "06/06/2022",
            like = 0,
            likedByMe = false,
            sum_likes = 0,
            sum_reposts = 10,
            sum_visible = 999_999
        )

        binding.render(post)
        binding.like.setOnClickListener {
            post.likedByMe = !post.likedByMe
            binding.like.setImageResource(getLikeIconResId(post.likedByMe))
            if (post.likedByMe) {
                post.sum_likes++
                binding.sumLikes.setText(countView(post.sum_likes))
            } else post.sum_likes--
            binding.sumLikes.setText(countView(post.sum_likes))
        }

        binding.repost.setOnClickListener {
            post.sum_reposts++
            binding.sumRepost?.setText(countView(post.sum_reposts))
        }
        binding.visible.setOnClickListener {
            binding.sumVisible.text = countView(post.sum_visible)
        }
    }

    fun ActivityMainBinding.render(post: Post) {
        authorName.text = post.authorName
        content?.text = post.content
        date.text = post.date
        like.setImageResource(getLikeIconResId(post.likedByMe))
        sumLikes.text = countView(post.sum_likes)
        sumRepost?.text = countView(post.sum_reposts)
        sumVisible.text = countView(post.sum_visible)
    }

    @DrawableRes
    fun getLikeIconResId(liked: Boolean) =
        if (liked) R.drawable.ic_baseline_favorite_24 else R.drawable.ic_baseline_favorite_border_24

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









