package org.ignus.ignus18.ui.activities

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.preference.PreferenceManager
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.MenuItem
import android.widget.LinearLayout
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.activity_event_list.*
import org.ignus.ignus18.App
import org.ignus.ignus18.R
import org.ignus.ignus18.data.Event
import org.ignus.ignus18.data.EventCategory
import org.ignus.ignus18.data.EventCategoryResult
import org.ignus.ignus18.ui.adapters.EventListAdapter


class EventList : AppCompatActivity() {

    companion object {
        val resultList = EventCategoryResult(getDataClassFromJSON(getLocalJSON())).list
        private fun getLocalJSON(): String {
            val sp = PreferenceManager.getDefaultSharedPreferences(App.instance.applicationContext)
            return sp.getString("theJSON", "default json")
        }
        private fun getDataClassFromJSON(JsonStr: String): List<EventCategory> {
            return Gson().fromJson(JsonStr, object : TypeToken<List<EventCategory>>() {}.type)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event_list)

        supportPostponeEnterTransition()
        val index = intent.getIntExtra("catPosition", 0)
        val parentType = intent.getStringExtra("parent_type")

        el_backdrop.transitionName = "transition" + index
        val list = resultList.filter { it.parent_type == parentType }

        Glide.with(this)
                .load(list[index].cover)
                .listener(object : RequestListener<Drawable> {
                    override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
                        supportStartPostponedEnterTransition()
                        return false
                    }

                    override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean): Boolean {
                        return true
                    }
                })
                .into(el_backdrop)

        setSupportActionBar(el_toolbar)
        el_collapsingToolbar.title = list[index].name
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        loadView(list[index].events)

    }

    private fun loadView(list: List<Event>) {

        if (list.isNotEmpty()) {
            el_eventList.adapter = EventListAdapter(list)
            el_eventList.layoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL, false)
        } else Toast.makeText(this, "No event(s) added yet :-(", Toast.LENGTH_SHORT).show()
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            16908332 -> super.onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }
}