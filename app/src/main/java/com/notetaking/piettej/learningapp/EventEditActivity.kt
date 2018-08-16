package com.notetaking.piettej.learningapp

import android.app.Activity
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.EditText
import com.notetaking.piettej.learningapp.models.EventData
import android.support.v4.app.NotificationCompat.getExtras
import android.content.Intent
import android.text.TextUtils
import android.view.View
import kotlinx.android.synthetic.main.event_edit.*
import kotlinx.android.synthetic.main.event_edit.view.*

class EventEditActivity : AppCompatActivity() {
    private var viewModel: EventEditActivityModel? = null

    private var eventId: Int? = null
    private var event: EventData? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.event_edit)

        // Get event passed to activity
        viewModel = ViewModelProviders.of(this).get(EventEditActivityModel(application)::class.java)
        eventId = intent.getIntExtra("idContact", -1)
        if (eventId != -1) {
            //setTitle(R.string.edit_contact_title)
            event = viewModel?.getEvent(eventId!!)
        } else {
            //setTitle(R.string.add_contact_title)
            //invalidateOptionsMenu()
        }
        eventTitle.setText(event!!.title)
        eventDetails.setText(event!!.details)

        buttonSave.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View) {
                if (TextUtils.isEmpty(eventTitle.getText())) {
                    //setResult(Activity.RESULT_CANCELED, replyIntent)
                } else {
                    event?.title = eventTitle.text.toString()
                    event?.details = eventDetails.text.toString()

                    viewModel?.insert(event!!)
                }
                finish()
            }
        })
    }

    companion object {

        val EXTRA_REPLY = "com.example.android.wordlistsql.REPLY"
    }
}

