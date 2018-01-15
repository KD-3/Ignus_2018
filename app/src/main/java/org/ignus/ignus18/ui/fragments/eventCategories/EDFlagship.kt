package org.ignus.ignus18.ui.fragments.eventCategories


import android.os.Bundle
import android.preference.PreferenceManager
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.frag_edflagship.*
import org.ignus.ignus18.App
import org.ignus.ignus18.R
import org.ignus.ignus18.data.EventCategory
import org.ignus.ignus18.data.EventCategoryResult
import org.ignus.ignus18.ui.activities.EventList
import org.ignus.ignus18.ui.adapters.EventCategoryListAdapter


class EDFlagship : Fragment() {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.frag_edflagship, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        FlagshipEventCategoryList.adapter = EventCategoryListAdapter(EventList.resultList.filter { it.parent_type == "2" })
        FlagshipEventCategoryList.layoutManager = LinearLayoutManager(context, LinearLayout.VERTICAL, false)
    }
}