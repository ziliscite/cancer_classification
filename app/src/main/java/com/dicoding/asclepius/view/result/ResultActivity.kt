package com.dicoding.asclepius.view.result

import android.annotation.SuppressLint
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.navArgs
import com.dicoding.asclepius.data.dto.ClassificationResult
import com.dicoding.asclepius.databinding.ActivityResultBinding
import java.text.NumberFormat

class ResultActivity : AppCompatActivity() {
    private lateinit var binding: ActivityResultBinding

    private val args: ResultActivityArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =  ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val result = args.ClassificationResult
        updateUI(result)
    }

    @SuppressLint("SetTextI18n")
    private fun updateUI(result: ClassificationResult) {
        binding.resultImage.setImageURI(Uri.parse(result.uri))
        binding.resultText.text = "${NumberFormat.getPercentInstance().format(result.score)} ${result.label}"

        if (result.label == "Cancer") {
            binding.linearProgressBar.progress = result.score.times(100).toInt()
        } else {
            // Dibalik karna bukan cancer, jadi persentase "sehat"
            binding.linearProgressBar.progress = 100 - result.score.times(100).toInt()
        }
    }
}