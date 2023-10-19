package com.example.mikohelper.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [
        Chat::class,
        Message::class
    ],
    version = 1
)
abstract class ChatDatabase: RoomDatabase() {
    abstract val chatDao: ChatDao
}