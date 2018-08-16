package com.notetaking.piettej.learningapp.database

import com.notetaking.piettej.learningapp.models.EventData
import android.app.Application
import android.arch.lifecycle.*
import com.notetaking.piettej.learningapp.models.EventDao
import org.jetbrains.annotations.Nullable
import android.arch.lifecycle.LifecycleRegistry
import android.arch.lifecycle.Lifecycle
import android.os.AsyncTask
import android.support.annotation.NonNull





class RepositoryImpl internal constructor(application: Application) :Repository<EventData> {
    private var mEventDao: EventDao
    private var mAllEvents: LiveData<List<EventData>>
    private val db = EventRoomDatabase.getDatabase(application)
    init {
        mEventDao = db.eventDao()
        mAllEvents = mEventDao.getAlphabetizedEvents()
    }

    override fun getItems():LiveData<List<EventData>>{
        return mAllEvents
    }

    override fun getItem(id:Int):EventData?{
        //getAsynTask(this.db).execute(id)
        return mEventDao.getEvent(id)
    }
    /*
    class getAsynTask(db: EventRoomDatabase) : AsyncTask<Int, Void, Void>() {
        private var eventDb = db
        override fun doInBackground(vararg params: Int?): Void? {
            eventDb.eventDao().getEvent(params[0])
            return null
        }
    }
*/
    override fun addItem(item:EventData){
        addAsynTask(this.db).execute(item)
    }
    class addAsynTask(db: EventRoomDatabase) : AsyncTask<EventData, Void, Void>() {
        private var eventDb = db
        override fun doInBackground(vararg params: EventData): Void? {
            eventDb.eventDao().insert(params[0])
            return null
        }
    }

    override fun addItems(items:Iterable<EventData>){
        items.forEach{
            addItem(it)
        }
    }

    override fun removeItem(item: EventData) {
        removeAsynTask(this.db).execute(item)
    }
    class removeAsynTask(db: EventRoomDatabase) : AsyncTask<EventData, Void, Void>() {
        private var eventDb = db
        override fun doInBackground(vararg params: EventData): Void? {
            eventDb.eventDao().deleteEvent(params[0].id)
            return null
        }
    }

    override fun removeAll() {
        removeAllAsynTask(this.db).execute()
    }
    class removeAllAsynTask(db: EventRoomDatabase) : AsyncTask<EventData, Void, Void>() {
        private var eventDb = db
        override fun doInBackground(vararg params: EventData): Void? {
            eventDb.eventDao().deleteAll()
            return null
        }
    }
}
