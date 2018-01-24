package org.ignus.ignus18.ui.adapters

import android.content.Intent
import android.net.Uri
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import org.ignus.ignus18.App
import org.ignus.ignus18.R
import org.ignus.ignus18.data.Event
import org.ignus.ignus18.ui.activities.EventDetails
import org.ignus.ignus18.data.Helper
import org.ignus.ignus18.ui.utils.ctx
import java.text.SimpleDateFormat
import java.util.*

class EventListAdapter(private val eventList: List<Event>) : RecyclerView.Adapter<EventListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent?.ctx).inflate(R.layout.event_list_card, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: EventListAdapter.ViewHolder, position: Int) {
        Glide.with(holder.cover).load(eventList[position].cover_url).into(holder.cover)
        holder.title.text = eventList[position].name
        holder.time.text = formatDateTime(eventList[position].date_time)
        holder.venue.text = eventList[position].location.name
        holder.teamSize.text = App.instance.resources.getString(R.string.team_size, eventList[position].min_team_size, eventList[position].max_team_size)

        holder.cover.setOnClickListener({ showFullDetails(position) })

        holder.venue.setOnClickListener({ launchGmaps(position) })

        holder.register.setOnClickListener({ registerForEvent(position) })

    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val cover: ImageView = view.findViewById(R.id.frag_ed2_cover)
        val title: TextView = view.findViewById(R.id.frag_ed2_title)
        val time: TextView = view.findViewById(R.id.frag_ed2_time)
        val venue: TextView = view.findViewById(R.id.frag_ed2_venue)
        val teamSize: TextView = view.findViewById(R.id.frag_ed2_teamSize)
        val register: Button = view.findViewById(R.id.frag_ed2_register)
    }

    override fun getItemCount(): Int = eventList.size

    private fun showFullDetails(position: Int) {
        val intent = Intent(App.instance, EventDetails::class.java)
        Helper.event = eventList[position]
        App.instance.startActivity(intent)
    }

    private fun launchGmaps(position: Int) {
        val pos = (eventList[position].location.longitude).toString() + "," + eventList[position].location.latitude.toString()
        val uri = "https://www.google.com/maps/dir/?api=1&destination=$pos&travelmode=walking"
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(uri))
        App.instance.startActivity(intent)
    }

    private fun registerForEvent(position: Int) {
        Toast.makeText(App.instance, "Call event register api " + eventList[position].unique_id, Toast.LENGTH_SHORT).show()
    }

    private fun formatDateTime(date_time: String): String {
        try {
            val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssz", Locale.ENGLISH)
            val date = inputFormat.parse(date_time)
            val outputFormat = SimpleDateFormat("EEE, dd MMM  hh:mm aa", Locale.ENGLISH)
            return outputFormat.format(date)
        } catch (e: Exception) {
            return "Coming soon..."
        }
    }

}