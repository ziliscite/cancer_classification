package com.dicoding.asclepius.helper

import com.dicoding.asclepius.data.dto.ClassificationResult
import java.text.NumberFormat

object ClassificationResultFormatter {
    operator fun invoke(result: ClassificationResult): String {
        return "${NumberFormat.getPercentInstance().format(result.score)} ${result.label}"
    }
}
