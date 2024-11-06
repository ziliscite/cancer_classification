package com.dicoding.asclepius.view.result

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.navigation.navArgs
import com.dicoding.asclepius.R
import com.dicoding.asclepius.data.dto.ClassificationResult
import com.dicoding.asclepius.databinding.ActivityResultBinding
import com.dicoding.asclepius.helper.ClassificationResultFormatter
import com.dicoding.asclepius.helper.LocalViewModelFactory

class ResultActivity : AppCompatActivity() {
    private lateinit var binding: ActivityResultBinding

    private val viewModel: ResultViewModel by viewModels<ResultViewModel> {
        LocalViewModelFactory.getInstance(this)
    }

    private val args: ResultActivityArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =  ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val result = args.ClassificationResult
        val isSaved = args.isSaved

        setUI(result)
        setButton(result, isSaved)
    }

    private fun setButton(result: ClassificationResult, isSaved: Boolean) {
        if (isSaved) {
            binding.fabSave.setImageResource(R.drawable.baseline_favorite_24)
            binding.fabSave.setOnClickListener {
                viewModel.delete(result.id!!)
                showToast("History is successfully deleted")
                // Result from home doesn't have an id, so it's hard to track which one is in the db.
                // hence why we just go back to the previous page to disable spam click
                finish()
            }
        } else {
            binding.fabSave.setImageResource(R.drawable.baseline_favorite_border_24)
            binding.fabSave.setOnClickListener {
                viewModel.upsert(result)
                showToast("History is successfully saved")
                finish()
            }
        }
    }

    private fun setUI(result: ClassificationResult) {
        binding.resultImage.setImageURI(Uri.parse(result.uri))
        binding.resultText.text = ClassificationResultFormatter.invoke(result)

        if (result.label == "Cancer") {
            binding.linearProgressBar.progress = result.score.times(100).toInt()
        } else {
            // Reversed, to be non-cancer score
            binding.linearProgressBar.progress = 100 - result.score.times(100).toInt()
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
