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
import kotlinx.android.synthetic.main.frag_edcultural.*
import org.ignus.ignus18.App
import org.ignus.ignus18.R
import org.ignus.ignus18.data.EventCategory
import org.ignus.ignus18.data.EventCategoryResult
import org.ignus.ignus18.ui.activities.EventList
import org.ignus.ignus18.ui.adapters.EventCategoryListAdapter


class EDCultural : Fragment() {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.frag_edcultural, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        CulturalEventCategoryList.adapter = EventCategoryListAdapter(EventList.resultList.filter { it.parent_type == "1" })
        CulturalEventCategoryList.layoutManager = LinearLayoutManager(context, LinearLayout.VERTICAL, false)
    }
}
