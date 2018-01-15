package org.ignus.ignus18.ui.activities

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.preference.PreferenceManager
import android.support.design.widget.NavigationView
import android.support.v4.app.Fragment
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.activity_main.*
import org.ignus.ignus18.R
import org.ignus.ignus18.ui.fragments.EventCategories
import org.ignus.ignus18.ui.fragments.Home


class MainActivity : AppCompatActivity() {

    private var navItemIndex = 0
    private val fragTag = arrayOf("Home", "Event Details", "Registered Event", "Contacts", "FAQs", "Sponsors", "About Us", "Developers")    // tags used to attach the fragments
    private var currentTag = fragTag[0]
    private var tempCurTag = currentTag

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        initialize() // Toolbar
        loadFragment() // I mean home fragment
        setUpNavHeader() // Navigation bar header items
        handleNavigationMenuClickEvents(nav_view) // Navigation menu click events
    }

    private fun initialize() {
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar) // Setting up toolbar

        //Add hamburger icon to actionBar
        val actionBarDrawerToggle = object : ActionBarDrawerToggle(this, drawer_layout, toolbar, R.string.openDrawer, R.string.closeDrawer) {}
        actionBarDrawerToggle.syncState()
    }

    private fun setUpNavHeader() {  // Navigation view header

        val navigationView = findViewById<NavigationView>(R.id.nav_view)
        val navHeader = navigationView.getHeaderView(0)
        val txtName = navHeader.findViewById<TextView>(R.id.name)
        val txtEmail = navHeader.findViewById<TextView>(R.id.email)
        val avatar = navHeader.findViewById<ImageView>(R.id.img_profile)
        val imgHeaderBg = navHeader.findViewById<ImageView>(R.id.img_header_bg)

        val sp = PreferenceManager.getDefaultSharedPreferences(this)
        val headerImgPath = sp.getString("key_header_img_path", "") // Getting stored avatar url
        Log.d("Suthar", "2 " + headerImgPath)

        Glide.with(this@MainActivity)   //Setting up avatar
                .load(Uri.parse(headerImgPath))
                .apply(RequestOptions().error(R.drawable.nav_header_dummy))
                .apply(RequestOptions.circleCropTransform())
                .into(avatar)

        Glide.with(this@MainActivity)   //Setting up navigation header background
                .load(headerImgPath)
                .into(imgHeaderBg)


        avatar.setOnClickListener {
            //Change avatar i.e., new avatar from storage
            val intent = Intent()
            intent.type = "image/*"
            intent.action = Intent.ACTION_GET_CONTENT
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), 199)
        }

        navHeader.setOnClickListener {
            //Show user full details
            Toast.makeText(this@MainActivity, "Show bottom sheet with user details", Toast.LENGTH_SHORT).show()
        }


        //txtName, txtWebsite, img_profile, img_header_bg will be loaded from url and set them
    }

    private fun handleNavigationMenuClickEvents(navigationView: NavigationView) {

        navigationView.setNavigationItemSelectedListener { item ->
            tempCurTag = currentTag //Temporary variable used to identify previous fragment

            //--------------------------------------------------------------------------------//
            when (item.itemId) {
                R.id.nav_home -> {
                    navItemIndex = 0
                    currentTag = fragTag[0]
                }
                R.id.nav_event_details -> {
                    navItemIndex = 1
                    currentTag = fragTag[1]
                }
                R.id.nav_registered_event -> {
                    navItemIndex = 2
                    currentTag = fragTag[2]
                }
                R.id.nav_contacts -> {
                    navItemIndex = 3
                    currentTag = fragTag[3]
                }
                R.id.nav_faqs -> {
                    navItemIndex = 4
                    currentTag = fragTag[4]
                }
                R.id.nav_sponsors -> {
                    navItemIndex = 5
                    currentTag = fragTag[5]
                }
                R.id.nav_about_us -> {
                    navItemIndex = 6
                    currentTag = fragTag[6]
                }
                R.id.nav_developers -> {
                    navItemIndex = 7
                    currentTag = fragTag[7]
                }
                else -> {
                    navItemIndex = 0
                    currentTag = fragTag[0]
                }
            }

            //Hide drawer after 250 millis to stop hanging of drawer
            Handler().postDelayed({ drawer_layout?.closeDrawers() }, 250)

            navigationView.setCheckedItem(item.itemId)
            supportActionBar?.title = currentTag


            //Checking if same fragment is open
            //if fragment is same then just do nothing
            if (currentTag != tempCurTag) loadCurrentFragment()

            true
        }
    }

    fun loadFragment(index: Int = 0) {
        navItemIndex = index
        loadCurrentFragment()
        currentTag = fragTag[index]
        supportActionBar?.title = currentTag
        nav_view.setCheckedItem(R.id.nav_home)
    }

    private fun loadCurrentFragment() {

        // Sometimes, when fragment has huge data, screen seems hanging
        // when switching between navigation menus
        // So using runnable, the fragment is loaded with cross fade effect
        // This effect can be seen in GMail app
        // update the main content by replacing fragments
        val mPendingRunnable = Runnable {
            val fragment = getHomeFragment()
            val fragmentTransaction = supportFragmentManager.beginTransaction()
            fragmentTransaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
            fragmentTransaction.replace(R.id.frame, fragment, currentTag)
            fragmentTransaction.disallowAddToBackStack()
            fragmentTransaction.commitAllowingStateLoss()
        }

        Handler().post(mPendingRunnable)
    }

    private fun getHomeFragment(): Fragment {

        return when (navItemIndex) {
            0 -> Home()
            1 -> EventCategories()
            else -> Home()
        }
    }

    override fun onBackPressed() {

        // If Drawer is open then close it First
        if (drawer_layout?.isDrawerOpen(GravityCompat.START) == true) {
            drawer_layout?.closeDrawers()
        }

        // This code loads home fragment when back key is pressed when user is in other fragment than home
        // checking if user is on other navigation menu rather than home
        else if (navItemIndex != 0) {
            navItemIndex = 0
            currentTag = fragTag[0]
            loadCurrentFragment()
            supportActionBar?.title = currentTag
            nav_view.setCheckedItem(R.id.nav_home)
        } else finish()

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 199 && resultCode == Activity.RESULT_OK) {
            //user selects img for nav header profile pic
            //the image path will be saved for future
            val uri = data?.data
            Glide.with(this@MainActivity)
                    .load(uri)
                    .apply(RequestOptions().error(R.drawable.nav_header_dummy))
                    .apply(RequestOptions.circleCropTransform())
                    .into(findViewById(R.id.img_profile))


            val sp = PreferenceManager.getDefaultSharedPreferences(this)
            val ed = sp.edit()
            ed.putString("key_header_img_path", uri.toString())
            ed.apply()

        }
    }
}
