package org.ignus.ignus18.ui.fragments.eventCategories


import android.content.res.Configuration
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.frag_edflagship.*
import org.ignus.ignus18.App
import org.ignus.ignus18.R
import org.ignus.ignus18.data.Helper
import org.ignus.ignus18.ui.adapters.EventCategoryListAdapter
import org.ignus.ignus18.ui.utils.SpacesItemDecorationFour
import org.ignus.ignus18.ui.utils.SpacesItemDecorationTwo
import org.jetbrains.anko.dip


class EDFlagship : Fragment() {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.frag_edflagship, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val itemCount = Helper.eventCategories.filter { it.parent_type == "2" }.size
        FlagshipEventCategoryList.adapter = EventCategoryListAdapter(Helper.eventCategories.filter { it.parent_type == "2" })

        if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            FlagshipEventCategoryList.layoutManager = GridLayoutManager(context, 4)
            FlagshipEventCategoryList.addItemDecoration(SpacesItemDecorationFour(App.instance.dip(4)))
        } else {
            val layoutManager = GridLayoutManager(context, 2)
            layoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                override fun getSpanSize(position: Int): Int = if (itemCount % 2 == 1 && itemCount == position + 1) 2 else 1
            }
            FlagshipEventCategoryList.layoutManager = layoutManager
            FlagshipEventCategoryList.addItemDecoration(SpacesItemDecorationTwo(App.instance.dip(4)))
        }
    }
}