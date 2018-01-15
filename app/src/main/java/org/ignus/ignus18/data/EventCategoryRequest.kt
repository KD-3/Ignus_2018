package org.ignus.ignus18.data

import android.preference.PreferenceManager
import android.util.Log
import android.widget.Toast
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.ignus.ignus18.App
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import java.io.IOException
import java.net.URL

class EventCategoryRequest {
    companion object {
        private val API_URL = "https://ignus.org/api/event/category-list/?format=json"
    }

    fun execute(): EventCategoryResult {
        val eventCategoryJsonStr: String

        if (isConnected()) {

            Log.d("Suthar", "isConnected")
            eventCategoryJsonStr = URL(API_URL).readText()
            storeJSONLocally(eventCategoryJsonStr)

        } else {

            Log.d("Suthar", "isNotConnected")
            eventCategoryJsonStr = getLocalJSON()
            doAsync { uiThread { Toast.makeText(App.instance, "Server/Internet error -> Outdated Data", Toast.LENGTH_LONG).show() } }
        }

        return EventCategoryResult(getDataClassFromJSON(eventCategoryJsonStr))
    }

    private fun getDataClassFromJSON(JsonStr: String): List<EventCategory> {
        return Gson().fromJson(JsonStr, object : TypeToken<List<EventCategory>>() {}.type)
    }

    private fun getLocalJSON(): String {
        val sp = PreferenceManager.getDefaultSharedPreferences(App.instance.applicationContext)
        return sp.getString("theJSON", "default json")
    }

    private fun storeJSONLocally(json: String) {
        val sp = PreferenceManager.getDefaultSharedPreferences(App.instance)
        val editor = sp.edit()
        editor.putString("theJSON", json)
        editor.apply()
    }

    @Throws(InterruptedException::class, IOException::class)
    private fun isConnected(): Boolean {
        val command = "ping -c 1 google.com"
        return Runtime.getRuntime().exec(command).waitFor() == 0
    }
}