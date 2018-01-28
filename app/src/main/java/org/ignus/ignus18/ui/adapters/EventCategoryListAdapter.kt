package org.ignus.ignus18.ui.adapters

import android.content.Intent
import android.content.res.Configuration
import android.support.v4.app.ActivityOptionsCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import org.ignus.ignus18.App
import org.ignus.ignus18.R
import org.ignus.ignus18.data.EventCategory
import org.ignus.ignus18.ui.activities.EventList
import org.ignus.ignus18.ui.activities.MainActivity
import org.ignus.ignus18.ui.utils.ctx


class EventCategoryListAdapter(private val eventCategories: List<EventCategory>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val cover: ImageView = view.findViewById(R.id.frag_ed_cover)
        private val title: TextView = view.findViewById(R.id.frag_ed_title)

        fun bindData(position: Int, model: EventCategory) {
            Glide.with(cover.context)
                    .load(model.cover)
                    .into(cover)

            title.text = model.name

            cover.transitionName = "transition" + position

            cover.setOnClickListener({
                val intent = Intent(cover.context, EventList::class.java)
                intent.putExtra("catPosition", position)
                intent.putExtra("parent_type", model.parent_type)

                val options = ActivityOptionsCompat.makeSceneTransitionAnimation(cover.context as MainActivity,
                        cover, "transition" + position)

                cover.context.startActivity(intent, options.toBundle())
            })
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view =
                if (viewType == 1) LayoutInflater.from(parent.ctx).inflate(R.layout.event_categories_card_single, parent, false)
                else LayoutInflater.from(parent.ctx).inflate(R.layout.event_categories_card, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int = eventCategories.size

    override fun getItemViewType(position: Int): Int {
        if (itemCount % 2 == 1 && itemCount == position + 1 && App.instance.resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) return 1
        return 0
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        (holder as MyViewHolder).bindData(position, eventCategories[position])
    }
}


/*Glide.with(holder.cover.context)
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
})*/