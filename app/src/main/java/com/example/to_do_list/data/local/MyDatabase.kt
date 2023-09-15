package com.example.to_do_list.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [Task::class], version = 1)
@TypeConverters(LocalDateTimeConverter::class)
abstract class MyDatabase :RoomDatabase() {
    abstract fun getTaskDao():TaskDao
}