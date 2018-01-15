package org.ignus.ignus18.ui.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v7.widget.GridLayoutManager
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_event_details.*
import org.ignus.ignus18.R
import org.ignus.ignus18.ui.adapters.EventOrganiserListAdapter
import org.ignus.ignus18.ui.fragments.EventCategories
import java.util.*

class EventDetails : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event_details)

        val eventID = intent.getStringExtra("eventID")
        Log.d("Suthar", eventID)

        supportActionBar?.title = "Event Details"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        tab()   // Set the text for each tab.
        about() //Load about for first time
        tabClickEventListener() // Tab event click listener

        Glide.with(this).load(EventCategories.resultList[Random().nextInt(2)].events[Random().nextInt(3)].cover_url).into(ed_logo)
        ed_title.text = EventCategories.resultList[Random().nextInt(2)].events[Random().nextInt(3)].name
    }

    private fun tab() {

        ed_tabs.addTab(ed_tabs.newTab().setText("About"))
        ed_tabs.addTab(ed_tabs.newTab().setText("Details"))
        ed_tabs.addTab(ed_tabs.newTab().setText("Organisers"))
        ed_tabs.tabGravity = TabLayout.GRAVITY_FILL // Set the tabs to fill the entire layout.

        ed_about.keyListener = null
    }

    private fun tabClickEventListener() {
        ed_tabs.setOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                when (tab?.position) {
                    0 -> about()
                    1 -> details()
                    2 -> organisers()
                    else -> about()
                }
            }
            override fun onTabReselected(tab: TabLayout.Tab?) {}
            override fun onTabUnselected(tab: TabLayout.Tab?) {}
        })
    }

    private fun about() {
        ed_about.visibility = View.VISIBLE
        ed_recyclerView.visibility = View.INVISIBLE

        ed_about.setText("About :"+resources.getString(R.string.lorem), TextView.BufferType.EDITABLE)
    }

    private fun details() {
        ed_about.visibility = View.VISIBLE
        ed_recyclerView.visibility = View.INVISIBLE

        ed_about.setText("Details :"+resources.getString(R.string.lorem), TextView.BufferType.EDITABLE)
    }

    private fun organisers() {
        ed_about.visibility = View.INVISIBLE
        ed_recyclerView.visibility = View.VISIBLE

        ed_recyclerView.adapter = EventOrganiserListAdapter(EventCategories.resultList[0].events[0].organiser_list)
        ed_recyclerView.layoutManager = GridLayoutManager(this, 1)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId==16908332) super.onBackPressed()
        return super.onOptionsItemSelected(item)
    }
}
