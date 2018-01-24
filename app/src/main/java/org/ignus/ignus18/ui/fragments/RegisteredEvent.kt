package org.ignus.ignus18.ui.fragments


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

import org.ignus.ignus18.R
import org.ignus.ignus18.data.Event
import org.ignus.ignus18.data.RegisterdEventRequest
import org.ignus.ignus18.ui.adapters.EventListAdapter
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

    private fun getRegisteredEventList(){

        doAsync {
            val result = RegisterdEventRequest().execute()

            uiThread {
                when (result) {
                    is Result.Failure -> {
                        loading?.text = getString(R.string.failed)
                    }

                    is Result.Success -> {
                        val jsonString = result.get()
                        populateData(getDataClassFromJson(jsonString))

                        loading.visibility = View.GONE
                    }
                }
            }
        }
    }

    private fun populateData(eventList: List<Event>){
        recyclerView.setHasFixedSize(true)
        recyclerView.adapter = EventListAdapter(eventList)
        recyclerView.layoutManager = GridLayoutManager(context, 1)
    }

    private fun getDataClassFromJson(json: String): List<Event> {
        return Gson().fromJson(json, object : TypeToken<List<Event>>() {}.type)
    }
}
