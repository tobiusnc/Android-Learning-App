package com.notetaking.piettej.learningapp.models

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*
import android.support.annotation.NonNull
import java.io.Serializable


@Entity(tableName = "event_table")
data class EventData(
        @NonNull var title:String,
        var details:String
):Serializable
{
    @PrimaryKey(autoGenerate = true) var id:Int = 0
}

@Dao
interface EventDao {
    @Query("SELECT * from event_table ORDER BY title ASC")
    fun getAlphabetizedEvents(): LiveData<List<EventData>>

    @Query("SELECT * from event_table WHERE id = :id")
    fun getEvent(id: Int?): EventData

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(event: EventData)

    @Query("DELETE FROM event_table")
    fun deleteAll()

    @Query("DELETE FROM event_table WHERE id = :id")
    fun     deleteEvent(id: Int)
}