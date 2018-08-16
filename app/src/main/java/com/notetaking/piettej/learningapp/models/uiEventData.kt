package com.notetaking.piettej.learningapp.models

data class uiEventData(val datamodel : EventData?)
{
    private var message : String
    constructor(message: String = ""):this(null){
        this.message = message
    }
    init{
        if(datamodel == null){
            message = ""
        }
        else {
            message = datamodel.title + datamodel.details
        }
    }
    fun getMessage():String {return message}
    fun getId(): Int { return datamodel!!.id }
}