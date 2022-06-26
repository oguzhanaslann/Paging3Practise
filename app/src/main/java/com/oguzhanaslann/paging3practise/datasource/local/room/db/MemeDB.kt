package com.oguzhanaslann.paging3practise.datasource.local.room.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.oguzhanaslann.paging3practise.datasource.local.room.dao.MemeDao
import com.oguzhanaslann.paging3practise.datasource.local.room.entity.MemeEntity

@Database(
    entities = [MemeEntity::class],
    version = MemeDB.LATEST_VERSION
)
abstract class MemeDB : RoomDatabase() {

    abstract fun memeDao(): MemeDao

    companion object {
        const val LATEST_VERSION = 1
        private const val DATABASE_NAME = "word_database"

        @Volatile
        private var INSTANCE: MemeDB? = null

        fun getDatabase(context: Context): MemeDB {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MemeDB::class.java,
                    DATABASE_NAME
                ).build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }
}
