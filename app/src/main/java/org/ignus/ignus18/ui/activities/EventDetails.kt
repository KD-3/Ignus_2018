package org.ignus.ignus18.ui.activities

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.text.Html
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_event_details.*
import kotlinx.android.synthetic.main.event_details_tab_1.*
import kotlinx.android.synthetic.main.event_details_tab_2.*
import kotlinx.android.synthetic.main.event_details_tab_3.*
import org.ignus.ignus18.R
import org.ignus.ignus18.data.Event
import org.ignus.ignus18.data.EventDetail
import org.ignus.ignus18.domain.commands.RequestEventDetailCommand
import org.ignus.ignus18.ui.adapters.EventOrganiserListAdapter
import org.ignus.ignus18.data.Helper
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread


class EventDetails : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event_details)


        val event = Helper.event
        if (event!=null){
            setUpViewPager()
            setUpActionBar(event)
            getEventData(event.unique_id, event)
        }
    }

    private fun setUpViewPager() {
        ed_pager.adapter = SectionsPagerAdapter(supportFragmentManager)
        ed_pager.offscreenPageLimit = 2
        ed_tabs.setupWithViewPager(ed_pager)
    }

    private fun setUpActionBar(event :Event){
        setSupportActionBar(ed_toolbar)
        supportActionBar?.title = event.name
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        Glide.with(this).load(event.cover_url).into(ed_logo)
    }

    private fun getEventData(eventId: String, event :Event) {

        doAsync {
            val data = RequestEventDetailCommand().execute(eventId)
            uiThread {
                populateData(event, data)
            }
        }
    }

    private fun populateData(event :Event, data:EventDetail) {

        more(data)

        val about = data.about
        val detail = data.details

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            ed_tab1_textView?.text = Html.fromHtml(about, Html.FROM_HTML_MODE_COMPACT)
            ed_tab2_textView?.text = Html.fromHtml(detail, Html.FROM_HTML_MODE_COMPACT)
        } else {
            ed_tab1_textView?.text = Html.fromHtml(about)
            ed_tab2_textView?.text = Html.fromHtml(detail)
        }

        ed_tab3_recyclerView?.adapter = EventOrganiserListAdapter(event.organiser_list)
        ed_tab3_recyclerView?.setHasFixedSize(true)
        ed_tab3_recyclerView?.layoutManager = GridLayoutManager(this, 1)
    }

    private fun more(data:EventDetail) {

        ed_moreDetails.setOnClickListener({
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://ignus.org${data.url}")))
        })

        if (data.pdf != null && data.pdf != "null") {
            ed_pdf.visibility = View.VISIBLE
            ed_pdf.setOnClickListener({
                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(data.pdf)))
            })
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == 16908332) super.onBackPressed()
        return super.onOptionsItemSelected(item)
    }

    private inner class SectionsPagerAdapter internal constructor(fm: FragmentManager) : FragmentPagerAdapter(fm) {

        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).
        override fun getItem(position: Int): Fragment = PlaceholderFragment.newInstance(position + 1)

        override fun getCount(): Int = 3

        override fun getPageTitle(position: Int): CharSequence {
            return when (position) {
                0 -> "About"
                1 -> "Details"
                2 -> "Organisers"
                else -> "Error"
            }
        }
    }

    class PlaceholderFragment : Fragment() {

        override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
            val sectionNumber = arguments!!.getInt(ARG_SECTION_NUMBER)

            return when (sectionNumber) {
                1, 8 -> inflater.inflate(R.layout.event_details_tab_1, container, false)
                2 -> inflater.inflate(R.layout.event_details_tab_2, container, false)
                3 -> inflater.inflate(R.layout.event_details_tab_3, container, false)
                else -> null
            }
        }

        companion object {

            private const val ARG_SECTION_NUMBER = "section_number"

            fun newInstance(sectionNumber: Int): PlaceholderFragment {
                val fragment = PlaceholderFragment()
                val args = Bundle()
                args.putInt(ARG_SECTION_NUMBER, sectionNumber)
                fragment.arguments = args
                return fragment
            }
        }

    }
}
