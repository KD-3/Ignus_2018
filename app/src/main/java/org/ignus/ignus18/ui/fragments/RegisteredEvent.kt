package org.ignus.ignus18.ui.fragments


import android.content.res.Configuration
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.kittinunf.result.Result
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.frag_registered_event.*
import org.ignus.ignus18.App

import org.ignus.ignus18.R
import org.ignus.ignus18.data.Event
import org.ignus.ignus18.data.Helper
import org.ignus.ignus18.data.RegisteredEventRequest
import org.ignus.ignus18.ui.adapters.EventListAdapter
import org.ignus.ignus18.ui.utils.SpacesItemDecorationOne
import org.ignus.ignus18.ui.utils.SpacesItemDecorationTwo
import org.jetbrains.anko.dip
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread


class RegisteredEvent : Fragment() {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.frag_registered_event, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getRegisteredEventList()
    }

    private fun getRegisteredEventList() {

        doAsync {
            val result = RegisteredEventRequest().execute()

            uiThread {
                when (result) {
                    is Result.Failure -> {
                        loading?.text = getString(R.string.failed)

                        //Testing

                        if (recyclerView != null) populateData(Helper.eventCategories.filter { it.parent_type == "1" }[0].events)

                        loading?.visibility = View.GONE
                    }

                    is Result.Success -> {
                        val jsonString = result.get()
                        if (recyclerView != null) populateData(getDataClassFromJson(jsonString))

                        loading?.visibility = View.GONE
                    }
                }
            }
        }
    }

    private fun populateData(eventList: List<Event>) {
        recyclerView.setHasFixedSize(true)
        recyclerView.adapter = EventListAdapter(eventList)

        if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            recyclerView.addItemDecoration(SpacesItemDecorationTwo(App.instance.dip(8)))
            recyclerView.layoutManager = GridLayoutManager(context, 2)
        } else {
            recyclerView.addItemDecoration(SpacesItemDecorationOne(App.instance.dip(16)))
            recyclerView.layoutManager = GridLayoutManager(context, 1)
        }
    }

    private fun getDataClassFromJson(json: String): List<Event> {
        return Gson().fromJson(json, object : TypeToken<List<Event>>() {}.type)
    }
}
