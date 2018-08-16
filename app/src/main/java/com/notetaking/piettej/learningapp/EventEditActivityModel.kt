package com.notetaking.piettej.learningapp

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.notetaking.piettej.learningapp.database.RepositoryImpl
import com.notetaking.piettej.learningapp.models.EventData

class EventEditActivityModel (application: Application) : AndroidViewModel(application) {

    private val mRepository: RepositoryImpl

    init {
        mRepository = RepositoryImpl(application)
        /*
        mDb = EventRoomDatabase.getDatabase(application)!!
        allEventsList = mDb.eventDao().getAlphabetizedEvents()
        */

    }
    fun insert(event: EventData) {
        mRepository.addItem(event)
    }

    fun getEvent(id: Int):EventData?{
       return mRepository.getItem(id)
    }
}