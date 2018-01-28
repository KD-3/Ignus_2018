package org.ignus.ignus18.ui.fragments


import android.os.Bundle
import android.os.Handler
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.frag_event_categories.*
import org.ignus.ignus18.R
import org.ignus.ignus18.domain.commands.RequestEventCategoryCommand
import org.ignus.ignus18.ui.fragments.eventCategories.EDCultural
import org.ignus.ignus18.ui.fragments.eventCategories.EDFlagship
import org.ignus.ignus18.ui.fragments.eventCategories.EDTechnical
import org.ignus.ignus18.data.Helper
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread


class EventCategories : Fragment() {

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

    override fun onResume() {
        super.onResume()

        doAsync {
            Log.d("Suthar", "Before")
            val temp = RequestEventCategoryCommand().execute().eventCategories
            Helper.eventCategories = temp
            Log.d("Suthar", "After")
            uiThread {
                loadCurrentFragment(cultural)
                loading?.visibility = View.GONE
                //setUpViewPager()
                navigation?.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
            }
        }
    }

    private fun loadCurrentFragment(fragment: Fragment) {

        val mPendingRunnable = Runnable {
            val fragmentTransaction = fragmentManager?.beginTransaction()
            fragmentTransaction?.replace(R.id.eventCategoryFrame, fragment)
            fragmentTransaction?.disallowAddToBackStack()
            fragmentTransaction?.commitAllowingStateLoss()
        }

        Handler().post(mPendingRunnable)
    }

    /*private fun setUpViewPager() {
        val fm = fragmentManager ?: return
        ec_pager.adapter = SectionsPagerAdapter(fm)
        ec_pager.offscreenPageLimit = 2
        ec_tabs.setupWithViewPager(ec_pager)
    }

    private inner class SectionsPagerAdapter internal constructor(fm: FragmentManager) : FragmentPagerAdapter(fm) {

        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).
        override fun getItem(position: Int): Fragment{
            return when(position){
                0 -> cultural
                1 -> technical
                2 -> flagship
                else -> cultural
            }
        }

        override fun getCount(): Int = 3

        override fun getPageTitle(position: Int): CharSequence {
            return when (position) {
                0 -> "Cultural"
                1 -> "Technical"
                2 -> "Flagship"
                else -> "Error"
            }
        }
    }*/

}
