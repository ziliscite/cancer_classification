package com.dicoding.asclepius.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dicoding.asclepius.data.dto.ClassificationResult

@Dao
interface ClassificationDao {
    @Query("SELECT * FROM classificationresult ORDER BY id DESC")
    fun getAll(): LiveData<List<ClassificationResult>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(vararg classificationResults: ClassificationResult)

    @Query("DELETE FROM classificationresult WHERE id = :id")
    suspend fun delete(id: Int)
}
