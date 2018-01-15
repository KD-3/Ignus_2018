package org.ignus.ignus18.ui.fragments


import android.app.ProgressDialog
import android.content.DialogInterface
import android.os.Bundle
import android.os.Handler
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.frag_event_categories.*
import org.ignus.ignus18.R
import org.ignus.ignus18.data.EventCategory
import org.ignus.ignus18.domain.commands.RequestEventCategoryCommand
import org.ignus.ignus18.ui.activities.MainActivity
import org.ignus.ignus18.ui.fragments.eventCategories.EDCultural
import org.ignus.ignus18.ui.fragments.eventCategories.EDFlagship
import org.ignus.ignus18.ui.fragments.eventCategories.EDTechnical
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread


class EventCategories : Fragment() {

    companion object {
        lateinit var resultList: List<EventCategory>
    }

    private val cultural by lazy { EDCultural() }
    private val technical by lazy { EDTechnical() }
    private val flagship by lazy { EDFlagship() }

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> loadCurrentFragment(cultural)
            R.id.navigation_dashboard -> loadCurrentFragment(technical)
            R.id.navigation_notifications -> loadCurrentFragment(flagship)
        }
        true
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.frag_event_categories, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

        val dialog = ProgressDialog(context)
        dialog.setMessage("Please wait...")
        dialog.setCancelable(false)

        val task = doAsync {
            resultList = RequestEventCategoryCommand().execute().eventCategories
            uiThread { dialog.dismiss(); loadCurrentFragment(cultural) }
        }

        dialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel", { _, _ ->
            task.cancel(true)
            dialog.dismiss()
            (activity as MainActivity).loadFragment()
        })
        dialog.show()
    }

    private fun loadCurrentFragment(fragment: Fragment) {

        val mPendingRunnable = Runnable {
            val fragmentTransaction = fragmentManager?.beginTransaction()
            //fragmentTransaction?.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
            fragmentTransaction?.replace(R.id.eventCategoryFrame, fragment)
            fragmentTransaction?.disallowAddToBackStack()
            fragmentTransaction?.commitAllowingStateLoss()
        }

        Handler().post(mPendingRunnable)
    }
}
