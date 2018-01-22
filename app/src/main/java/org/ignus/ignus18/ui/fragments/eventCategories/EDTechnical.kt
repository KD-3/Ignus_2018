package org.ignus.ignus18.ui.fragments.eventCategories


import android.content.res.Configuration
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.frag_edtechnical.*
import org.ignus.ignus18.R
import org.ignus.ignus18.ui.adapters.EventCategoryListAdapter
import org.ignus.ignus18.ui.fragments.EventCategories
import org.ignus.ignus18.ui.utils.SpacesItemDecoration
import org.ignus.ignus18.ui.utils.SpacesItemDecorationLand


class EDTechnical : Fragment() {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.frag_edtechnical, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        TechnicalEventCategoryList.adapter = EventCategoryListAdapter(EventCategories.resultList.filter { it.parent_type=="3" })

        if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            TechnicalEventCategoryList.layoutManager = GridLayoutManager(context, 4)
            TechnicalEventCategoryList.addItemDecoration(SpacesItemDecorationLand(16))
        } else {
            TechnicalEventCategoryList.layoutManager = GridLayoutManager(context, 2)
            TechnicalEventCategoryList.addItemDecoration(SpacesItemDecoration(16))
        }
    }

}