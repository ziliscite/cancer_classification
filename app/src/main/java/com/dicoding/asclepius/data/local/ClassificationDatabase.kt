package com.dicoding.asclepius.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.dicoding.asclepius.data.dto.ClassificationResult

@Database(entities = [ClassificationResult::class], version = 1, exportSchema = false)
abstract class ClassificationDatabase : RoomDatabase() {
    abstract fun classificationDao(): ClassificationDao

    companion object {
        @Volatile
        private var INSTANCE: ClassificationDatabase? = null

        fun getDatabase(context: Context): ClassificationDatabase {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context,
                    ClassificationDatabase::class.java,
                    "classification_database"
                ).build()
            }
        }
    }
}
