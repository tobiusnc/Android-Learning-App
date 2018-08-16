package com.notetaking.piettej.learningapp.database

import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.Database
import android.content.Context
import com.notetaking.piettej.learningapp.models.EventDao
import com.notetaking.piettej.learningapp.models.EventData
import android.os.AsyncTask.execute
import android.arch.persistence.db.SupportSQLiteDatabase
import android.support.annotation.NonNull
import android.os.AsyncTask

@Database(entities = arrayOf(EventData::class), version = 2, exportSchema = false)
abstract class EventRoomDatabase : RoomDatabase() {

    companion object {

        private var INSTANCE: EventRoomDatabase? = null
        fun getDatabase(context: Context): EventRoomDatabase {
            if (INSTANCE == null) {
                synchronized(EventRoomDatabase::class.java) {
                    if (INSTANCE == null) {
                        INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                EventRoomDatabase::class.java, "event_database")
                                .fallbackToDestructiveMigration()
                                .allowMainThreadQueries()
                                .addCallback(sRoomDatabaseCallback)
                                .build()

                    }
                }
            }
            return INSTANCE as EventRoomDatabase
        }
        private val sRoomDatabaseCallback = object : RoomDatabase.Callback() {

            override fun onOpen(db: SupportSQLiteDatabase) {
                super.onOpen(db)
                PopulateDbAsync(INSTANCE!!).execute()
            }
        }
    }

    abstract fun eventDao(): EventDao

    private class PopulateDbAsync internal constructor(db: EventRoomDatabase) : AsyncTask<Void, Void, Void>() {

        private val mDao: EventDao

        init {
            mDao = db.eventDao()
        }

        override fun doInBackground(vararg params: Void): Void? {
            mDao.deleteAll()
            var event = EventData("title1", "Details the one")
            mDao.insert(event)
            Thread.sleep(5000)
            event = EventData("title2", "Details the two")
            mDao.insert(event)
            Thread.sleep(5000)
            event = EventData("title3", "Details the three")
            mDao.insert(event)
            return null
        }
    }

}