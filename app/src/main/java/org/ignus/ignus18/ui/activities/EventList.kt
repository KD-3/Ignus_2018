package org.ignus.ignus18.ui.activities

import android.content.res.Configuration
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.support.design.widget.AppBarLayout
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.view.MenuItem
import android.widget.LinearLayout
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import kotlinx.android.synthetic.main.activity_event_list.*
import org.ignus.ignus18.App
import org.ignus.ignus18.R
import org.ignus.ignus18.data.Event
import org.ignus.ignus18.ui.adapters.EventListAdapter
import org.ignus.ignus18.ui.fragments.EventCategories
import org.ignus.ignus18.ui.utils.SpacesItemDecorationOne
import org.ignus.ignus18.ui.utils.SpacesItemDecorationTwo
import org.jetbrains.anko.dip


class EventList : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event_list)


        //Shared element transition stuff
        supportPostponeEnterTransition()
        val index = intent.getIntExtra("catPosition", 0)
        val parentType = intent.getStringExtra("parent_type")

        el_backdrop.transitionName = "transition" + index
        val list = EventCategories.resultList.filter { it.parent_type == parentType }

        Glide.with(this)
                .load(list[index].cover)
                .listener(object : RequestListener<Drawable> {
                    override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
                        supportStartPostponedEnterTransition()
                        return false
                    }

                    override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean): Boolean {
                        supportStartPostponedEnterTransition()
                        return true
                    }
                })
                .into(el_backdrop)


        //Some basic commands
        setSupportActionBar(el_toolbar)
        el_collapsingToolbar.title = list[index].name
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        EventDetails.catPosition = index
        EventDetails.parentType = parentType

        //Load recycler view with event list
        loadView(list[index].events)

    }

    private fun loadView(list: List<Event>) {
        el_eventList.adapter = EventListAdapter(list)

        if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            el_eventList.addItemDecoration(SpacesItemDecorationTwo(App.instance.dip(4)))
            el_eventList.layoutManager = GridLayoutManager(this, 2)
        } else {
            el_eventList.addItemDecoration(SpacesItemDecorationOne(App.instance.dip(8)))
            el_eventList.layoutManager = GridLayoutManager(this, 1)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            16908332 -> super.onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }
}
