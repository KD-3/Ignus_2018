package org.ignus.ignus18.ui.activities

import android.app.ProgressDialog
import android.content.DialogInterface
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v7.widget.GridLayoutManager
import android.text.Html
import android.view.MenuItem
import android.view.View
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_event_details.*
import org.ignus.ignus18.R
import org.ignus.ignus18.data.EventDetail
import org.ignus.ignus18.domain.commands.RequestEventDetailCommand
import org.ignus.ignus18.ui.adapters.EventOrganiserListAdapter
import org.ignus.ignus18.ui.fragments.EventCategories
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import android.content.Intent
import android.net.Uri


class EventDetails : AppCompatActivity() {

    companion object {
        var catPosition = 0
        var parentType = "0"
    }
    private val dialog by lazy { ProgressDialog(this) }

    private var data: EventDetail = EventDetail("https://ignus.org", "error", "error", "null")


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event_details)

        val index = intent.getIntExtra("index", 0)


        val event = EventCategories.resultList.filter { it.parent_type == parentType }[catPosition].events[index]


        setSupportActionBar(ed_toolbar)
        supportActionBar?.title = event.name
        supportActionBar?.setDisplayHomeAsUpEnabled(true)


        Glide.with(this).load(event.cover_url).into(ed_logo)


        tab()
        getEventData(event.unique_id)

    }

    private fun getEventData(eventId: String) {


        dialog.setMessage("Please wait...")
        dialog.setCancelable(false)

        val task = doAsync {
            data = RequestEventDetailCommand().execute(eventId)
            uiThread {
                more()
                about()
                dialog.dismiss()
            }
        }

        dialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel", { _, _ ->
            task.cancel(true)
            dialog.dismiss()
        })
        dialog.show()
    }

    private fun tab() {

        ed_tabs.addTab(ed_tabs.newTab().setText("About"))
        ed_tabs.addTab(ed_tabs.newTab().setText("Details"))
        ed_tabs.addTab(ed_tabs.newTab().setText("Organisers"))
        ed_tabs.tabGravity = TabLayout.GRAVITY_FILL // Set the tabs to fill the entire layout.

        ed_about.keyListener = null

        tabClickEventListener()
    }

    private fun more() {

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

    private fun tabClickEventListener() {
        ed_tabs.setOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                when (tab?.position) {
                    0 -> about()
                    1 -> about('D')
                    2 -> organisers()
                    else -> about()
                }
            }
            override fun onTabReselected(tab: TabLayout.Tab?) {}
            override fun onTabUnselected(tab: TabLayout.Tab?) {}
        })
    }

    private fun about(type: Char = 'A') {
        ed_about.visibility = View.VISIBLE
        ed_recyclerView.visibility = View.GONE


        val bodyData = if (type == 'A') data.about else data.details

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) ed_about.text = Html.fromHtml(bodyData, Html.FROM_HTML_MODE_COMPACT)
        else ed_about.text = Html.fromHtml(bodyData)

    }

    private fun organisers() {
        ed_about.visibility = View.GONE
        ed_recyclerView.visibility = View.VISIBLE

        ed_recyclerView.adapter = EventOrganiserListAdapter(EventCategories.resultList[0].events[0].organiser_list)
        ed_recyclerView.layoutManager = GridLayoutManager(this, 1)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == 16908332) super.onBackPressed()
        return super.onOptionsItemSelected(item)
    }
}
