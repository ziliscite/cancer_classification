package com.dicoding.asclepius.data.dto

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity
@Parcelize
data class ClassificationResult(
    @field:ColumnInfo(name = "id")
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,

    @field:ColumnInfo("uri")
    val uri: String,

    @field:ColumnInfo("label")
    val label: String,

    @field:ColumnInfo("score")
    val score: Float,
) : Parcelable
