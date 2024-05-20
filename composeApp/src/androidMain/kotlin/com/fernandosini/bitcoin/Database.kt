package com.fernandosini.bitcoin

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import repository.database.AppDatabase

fun getDatabaseBuilder(ctx: Context): RoomDatabase.Builder<AppDatabase> {
    val appContext = ctx.applicationContext
    val dbFile = appContext.getDatabasePath("crypto.db")
    return Room.databaseBuilder<AppDatabase>(
        context = appContext,
        name = dbFile.absolutePath
    )
}