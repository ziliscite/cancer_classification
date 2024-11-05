package com.dicoding.asclepius.helper

import android.content.Context
import org.tensorflow.lite.task.vision.classifier.Classifications

// Agar inisialisasi pada fragment tidak terlalu panjang
class ImageClassifierHelperFactory(private val context: Context) {
    fun create(onError: (String) -> Unit, onResults: (List<Classifications>?, Long) -> Unit): ImageClassifierHelper {
        return ImageClassifierHelper(
            context = context,
            classifierListener = object : ImageClassifierHelper.ClassifierListener {
                override fun onError(error: String) {
                    onError(error)
                }

                override fun onResults(results: List<Classifications>?, inferenceTime: Long) {
                    onResults(results, inferenceTime)
                }
            }
        )
    }
}
