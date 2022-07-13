package ru.netology.nmedia

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContract
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_post_content.*
import ru.netology.nmedia.PostContentActivity.Companion.RESULT_KEY
import ru.netology.nmedia.data.Post
import ru.netology.nmedia.databinding.ActivityPostContentBinding

class PostContentActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityPostContentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.contentEditText.requestFocus()                             // фокус на поле редактирования

        binding.saveButton.setOnClickListener {
            onButtonClicked(contentEditText.text?.toString())
        }
    }

    private fun onButtonClicked(postContent: String?) {
        val intent = Intent()
        if (postContent.isNullOrBlank()) {
            setResult(Activity.RESULT_CANCELED, intent)        // результат отменен
        } else {
            intent.putExtra(RESULT_KEY, postContent)           // кладем информацию
            setResult(                                         // сообщаем результат,передаем данные
                Activity.RESULT_OK,
                intent
            )
        }
        finish()
    }

    private companion object {
        const val RESULT_KEY = "postNewContent"
    }

    object ResultContract : ActivityResultContract<Unit, String?>() {
        override fun createIntent(context: Context, input: Unit) =
            Intent(context, PostContentActivity::class.java)

        override fun parseResult(resultCode: Int, intent: Intent?): String? {
            if (resultCode != Activity.RESULT_OK) return null
            intent ?: return null

            return intent.getStringExtra(RESULT_KEY)
        }
    }

//    class EditPostResult(
//        var newContent: String,
//        var newVideoUrl: String?,
//    )

//    object ResultContractEdit : ActivityResultContract<EditPostResult?, Post>() {
//        override fun createIntent(context: Context, input: EditPostResult?) =
//            Intent(context, PostContentActivity::class.java).apply {
//                putExtra("content", input?.newContent)
//                putExtra("video", input?.newVideoUrl)
//            }
//
//        override fun parseResult(resultCode: Int, intent: Intent?): Post {
//            if (resultCode == Activity.RESULT_OK) {
//                EditPostResult(
//                    newContent = intent?.getStringExtra("content")!!,
//                    newVideoUrl = intent.getStringExtra("url")
//                )
//            } else 0
//        }
//    }
}