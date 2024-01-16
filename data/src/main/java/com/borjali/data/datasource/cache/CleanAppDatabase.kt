package com.borjali.data.datasource.cache

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

import com.borjali.data.datasource.cache.worker.WorkerDao
import com.borjali.data.datasource.cache.worker.WorkerEntity

@Database(
    entities = [
        WorkerEntity::class,
    ],
    version = 1, exportSchema = false
)
@TypeConverters(value = [EntityConverters::class])
abstract class CleanAppDatabase : RoomDatabase() {
    abstract fun workerDao(): WorkerDao

}
