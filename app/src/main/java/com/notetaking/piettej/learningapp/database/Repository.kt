package com.notetaking.piettej.learningapp.database

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData

interface Repository<T> {
    fun getItems():LiveData<List<T>>
    fun getItem(id:Int):T?
    fun addItem(item:T)
    fun addItems(items:Iterable<T>)
    fun removeItem(item: T)
    fun removeAll()
}