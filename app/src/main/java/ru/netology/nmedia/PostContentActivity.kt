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

//        binding.contentEditText.requestFocus()                             // фокус на поле редактирования
        binding.contentEditText.setText(intent?.extras?.getString(Intent.EXTRA_TEXT))         //обращаемсч к переданному Intent и извлекайем из него текст

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

    object ResultContract : ActivityResultContract<String, String?>() {       //Добавьляем входной параметр типа String и кладем его в Intent
        override fun createIntent(context: Context, input: String) =
            Intent(context, PostContentActivity::class.java)
                .putExtra(Intent.EXTRA_TEXT,input)

        override fun parseResult(resultCode: Int, intent: Intent?): String? {
            if (resultCode != Activity.RESULT_OK) return null
            intent ?: return null

            return intent.getStringExtra(RESULT_KEY)
        }
    }
}