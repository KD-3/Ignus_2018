package org.ignus.ignus18.ui.adapters

import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import org.ignus.ignus18.R
import org.ignus.ignus18.data.EventCategory
import org.ignus.ignus18.ui.activities.EventList
import org.ignus.ignus18.ui.utils.ctx
import android.support.v4.app.ActivityOptionsCompat
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import org.ignus.ignus18.ui.activities.MainActivity


class EventCategoryListAdapter(private val eventCategories: List<EventCategory>) : RecyclerView.Adapter<EventCategoryListAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val cover: ImageView = view.findViewById(R.id.frag_ed_cover)
        val title: TextView = view.findViewById(R.id.frag_ed_title)
    }

    private val cc = arrayOf("gfgf", "hfhf")
    val c = "djc"

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.ctx).inflate(R.layout.event_categories_card, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = eventCategories.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        Glide.with(holder.cover.context)
                .load(eventCategories[position].cover)
                .into(holder.cover)

        holder.title.text = eventCategories[position].name

        holder.cover.transitionName = "transition" + position

        holder.cover.setOnClickListener({
            val intent = Intent(holder.cover.context, EventList::class.java)
            intent.putExtra("catPosition", position)
            intent.putExtra("parent_type", eventCategories[position].parent_type)

            val options = ActivityOptionsCompat.makeSceneTransitionAnimation(holder.cover.context as MainActivity,
                    holder.cover, "transition" + position)

            holder.cover.context.startActivity(intent, options.toBundle())
        })
    }
}