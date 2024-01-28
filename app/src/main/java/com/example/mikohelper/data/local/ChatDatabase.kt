package com.example.mikohelper.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [
        Chat::class,
        Message::class
    ],
    version = 2
)
abstract class ChatDatabase: RoomDatabase() {
    abstract val chatDao: ChatDao
}