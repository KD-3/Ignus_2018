package org.ignus.ignus18.ui.fragments


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import org.ignus.ignus18.R
import android.support.design.widget.TabLayout
import kotlinx.android.synthetic.main.frag_event_details.*


class EventDetails : Fragment() {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.frag_event_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tab()
    }

    private fun tab() {

        // Set the text for each tab.
        ed_tabs.addTab(ed_tabs.newTab().setText("About"))
        ed_tabs.addTab(ed_tabs.newTab().setText("Details"))
        ed_tabs.addTab(ed_tabs.newTab().setText("Organisers"))
        // Set the tabs to fill the entire layout.
        ed_tabs.tabGravity = TabLayout.GRAVITY_FILL
    }

}
