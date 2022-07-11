package ru.netology.nmedia

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_share.view.*
import ru.netology.nmedia.databinding.ActivityShareBinding

class ShareActivity : AppCompatActivity(){
    override fun onCreate(savedInstanceState:Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityShareBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val intent = intent ?: return
        if(intent.action != Intent.ACTION_SEND) return

        val text = intent.getStringExtra(Intent.EXTRA_TEXT)
        if(text.isNullOrBlank()) {
                Snackbar.make(binding.root, "Контент отсутствует", Snackbar.LENGTH_INDEFINITE)  // показывать бесконечно
                    .setAction(android.R.string.ok) { finish() }
                    .show()
            }else{
                binding.root.text = text
            }
        }
    }

