package ru.netology.nmedia

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import ru.netology.nmedia.databinding.FragmentNewPostBinding
import ru.netology.nmedia.viewModel.PostViewModel

class NewPostFragment : Fragment() {

    private val args by navArgs<NewPostFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentNewPostBinding.inflate(inflater, container, false)
//      val viewModel: PostViewModel by viewModels(ownerProducer = ::requireParentFragment)

        binding.contentEditText.setText(args.initialContent)
        binding.contentEditText.requestFocus()                                                // фокус на поле редактирования
//        binding.contentEditText.setText(intent?.extras?.getString(Intent.EXTRA_TEXT))         //обращаемсч к переданному Intent и извлекайем из него текст

        binding.saveButton.setOnClickListener {
            onButtonClicked(binding)
        }

//        fun onButtonClicked(postContent: String?) {
//        val intent = Intent()
//        if (postContent.isNullOrBlank()) {
//            activity?.setResult(Activity.RESULT_CANCELED, intent)        // результат отменен
//        } else {
//            intent.putExtra(RESULT_KEY, postContent)                     // кладем информацию
//            activity?.setResult(                                         // сообщаем результат,передаем данные
//                Activity.RESULT_OK,
//                intent
//            )
//        }
//        activity?.finish()
//    }

//    object ResultContract : ActivityResultContract<String, String?>() {       //Добавьляем входной параметр типа String и кладем его в Intent
//        override fun createIntent(context: Context, input: String) =
//            Intent(context, NewPostFragment::class.java)
//                .putExtra(Intent.EXTRA_TEXT,input)
//
//        override fun parseResult(resultCode: Int, intent: Intent?): String? {
//            if (resultCode != Activity.RESULT_OK) return null
//            intent ?: return null
//
//            return intent.getStringExtra(RESULT_KEY)
//        }
//    }
        return binding.root
    }

    private fun onButtonClicked(binding: FragmentNewPostBinding) {
        val text = binding.contentEditText.text
        if (!text.isNullOrBlank()) {                                     // если тексt не интент и не пустой
            val resultBundle = Bundle(1)
            resultBundle.putString(RESULT_KEY_CONTENT, text.toString())
            setFragmentResult(REQUEST_KEY_CONTENT, resultBundle)
        }
        findNavController().popBackStack()
    }

    companion object {
        const val REQUEST_KEY_CONTENT = "requestKeyContent"
        const val RESULT_KEY_CONTENT = "postNewContent"
        const val REQUEST_KEY_VIDEO = "requestKeyVideo"
        const val RESULT_KEY_VIDEO = "postNewVideo"
    }
}

