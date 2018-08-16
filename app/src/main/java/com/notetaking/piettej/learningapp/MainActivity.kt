package com.notetaking.piettej.learningapp

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.notetaking.piettej.learningapp.models.uiEventData
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.annotations.Nullable
import android.support.v7.widget.DividerItemDecoration
import android.view.MenuItem
import android.widget.PopupMenu
import android.widget.TextView
import android.widget.Toast
import org.w3c.dom.Text


class MainActivity : AppCompatActivity(), MainViewAdapter.OnItemClickListener, MainViewAdapter.OnItemOptonClickListener {
    private var mEventViewModel: MainActivityModel? = null

    private var recyclerView: RecyclerView? = null
    private var adapter: MainViewAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = eventView
        recyclerView!!.addItemDecoration(DividerItemDecoration(applicationContext,
                DividerItemDecoration.VERTICAL))
        adapter = MainViewAdapter(this, this, this)
        recyclerView!!.layoutManager = LinearLayoutManager(this)
        recyclerView!!.setAdapter(adapter)

// Get a new or existing ViewModel from the ViewModelProvider.
        mEventViewModel = ViewModelProviders.of(this).get(MainActivityModel(application)::class.java)

        // Add an observer on the LiveData returned by getAlphabetizedWords.
        // The onChanged() method fires when the observed data changes and the activity is
        // in the foreground.
        mEventViewModel!!.getAllEvents().observe(this, Observer { events ->
            adapter!!.setEvents(events!!)
        })
        mEventViewModel!!.getToastMessages().observe(this, Observer { message ->
            if(message != null) {
                Toast.makeText(
                        getApplicationContext(),
                        message,
                        Toast.LENGTH_SHORT).show()
                mEventViewModel!!.getToastMessages().postValue(null)
            }
        })
/*
        fab.setOnClickListener {
            editEvent(null)
        }
*/
    }

    fun editEvent(event: uiEventData?){
        var thisEvent : uiEventData? = event
        if(thisEvent == null){
            thisEvent = uiEventData("")
        }
        var intent = Intent(getApplication(), EventEditActivity::class.java)
        intent.putExtra("idContact", thisEvent.getId())
        startActivity(intent)
    }

    override fun onItemClick(event: uiEventData) {
        Toast.makeText(
                getApplicationContext(),
                event.getMessage() + " clicked!",
                Toast.LENGTH_SHORT).show();
    }

    override fun onItemOptionClick(event: uiEventData, buttonViewOption: TextView) {
        //creating a popup menu
        val popup = PopupMenu(applicationContext, buttonViewOption)
        //inflating menu from xml resource
        popup.inflate(R.menu.main_button_options)
        //adding click listener
        popup.setOnMenuItemClickListener(object : PopupMenu.OnMenuItemClickListener {

            override fun onMenuItemClick(item: MenuItem): Boolean {
                when (item.getItemId()) {
                    R.id.Edit -> {
                        editEvent(event)
                        return true
                    }
                    R.id.Delete -> {
                        mEventViewModel!!.deleteEvent(event)
                        return true
                    }
                    else -> return false
                }
            }
        })
        //displaying the popup
        popup.show()
    }
}


