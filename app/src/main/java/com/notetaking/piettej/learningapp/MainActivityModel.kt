package com.notetaking.piettej.learningapp

import android.app.Activity
import android.app.Application
import android.arch.lifecycle.*
import android.content.Intent
import android.support.v4.content.ContextCompat
import com.notetaking.piettej.learningapp.database.EventRoomDatabase
import com.notetaking.piettej.learningapp.database.RepositoryImpl
import com.notetaking.piettej.learningapp.models.EventData
import com.notetaking.piettej.learningapp.models.uiEventData


class MainActivityModel(application: Application) : AndroidViewModel(application) {
    var allEventsList: LiveData<List<uiEventData>>
    private val mRepository: RepositoryImpl
    var toastMessageList = MutableLiveData<String>()

    init {
        mRepository = RepositoryImpl(application)
        allEventsList = Transformations.map(mRepository.getItems() ){
            newData -> createEventButtonViewModel(newData) }
        /*
        mDb = EventRoomDatabase.getDatabase(application)!!
        allEventsList = mDb.eventDao().getAlphabetizedEvents()
        */
    }
    fun getAllEvents(): LiveData<List<uiEventData>> {
        return allEventsList
    }

    fun createEventButtonViewModel(dataModel : List<EventData>) : List<uiEventData>{
        var uiEvents : ArrayList<uiEventData> = arrayListOf()
        dataModel.forEach{
            uiEvents.add(uiEventData(it))
        }
        return uiEvents
    }

    fun getToastMessages(): MutableLiveData<String>{
        return toastMessageList;
    }

    fun deleteEvent(event: uiEventData){
        mRepository.removeItem(event.datamodel!!)
        toastMessageList.postValue(event.getMessage() + "delete!")
    }
/*
    fun getEvent(eventID: Int){
        mRepository.getItem(eventID)
    }
*/
    fun editEvent(event: uiEventData?){
        toastMessageList.postValue(event?.getMessage() + "delete!")
    }
}

