package com.notetaking.piettej.learningapp

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.notetaking.piettej.learningapp.models.EventData
import kotlinx.android.synthetic.main.recyclerview_event_item.view.*
import org.w3c.dom.Text
import android.R.menu
import android.view.MenuItem
import android.widget.PopupMenu
import com.notetaking.piettej.learningapp.models.uiEventData


class MainViewAdapter(context: Context, listenerEvent: OnItemClickListener, listenerOptions: OnItemOptonClickListener): RecyclerView.Adapter<MainViewAdapter.ViewHolder>() {
    private var eventList: List<uiEventData> = listOf()// Cached copy of words
    private val mCtx = context;

    private val listenerEvent: OnItemClickListener = listenerEvent
    private val listenerOptions: OnItemOptonClickListener = listenerOptions

    interface OnItemClickListener {
        fun onItemClick(event: uiEventData)
    }

    interface OnItemOptonClickListener {
        fun onItemOptionClick(event: uiEventData, buttonViewOption: TextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.recyclerview_event_item, parent, false)
        return ViewHolder(v);
    }

    override fun getItemCount(): Int {
        return eventList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var currentEvent: uiEventData = eventList[position]

        var titleEvent = currentEvent.getMessage()
        var detailsEvent = currentEvent.getMessage()

        holder.mTitle.text = titleEvent
        holder.mDetails.text = detailsEvent

        holder.bind(currentEvent, listenerEvent, listenerOptions)

    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        var mTitle = itemView.textEventTitle
        var mDetails = itemView.textEventDetails
        var buttonViewOption = itemView.textOptionsButton

        fun bind(event: uiEventData, listenerEvent: OnItemClickListener, listenerOptions: OnItemOptonClickListener) {
            itemView.setOnClickListener {
                listenerEvent.onItemClick(event)
            }
            buttonViewOption.setOnClickListener {
                listenerOptions.onItemOptionClick(event, buttonViewOption)
            }
        }
    }

    fun setEvents(events: List<uiEventData>) {
        this.eventList = events
        notifyDataSetChanged()
    }

}

/*
class WordListAdapter internal constructor(context: Context) : RecyclerView.Adapter<WordListAdapter.WordViewHolder>() {

    private val mInflater: LayoutInflater
    private var mWords: List<Word>? = null // Cached copy of words

    // getItemCount() is called many times, and when it is first called,
    // mWords has not been updated (means initially, it's null, and we can't return null).
    val itemCount: Int
        get() = if (mWords != null)
            mWords!!.size
        else
            0

    internal inner class WordViewHolder private constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val wordItemView: TextView

        init {
            wordItemView = itemView.findViewById(R.id.textView)
        }
    }

    init {
        mInflater = LayoutInflater.from(context)
    }

    fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordViewHolder {
        val itemView = mInflater.inflate(R.layout.recyclerview_item, parent, false)
        return WordViewHolder(itemView)
    }

    fun onBindViewHolder(holder: WordViewHolder, position: Int) {
        if (mWords != null) {
            val current = mWords!![position]
            holder.wordItemView.setText(current.getWord())
        } else {
            // Covers the case of data not being ready yet.
            holder.wordItemView.text = "No Word"
        }
    }

    internal fun setWords(words: List<Word>) {
        mWords = words
        notifyDataSetChanged()
    }
}
        */