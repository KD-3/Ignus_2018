package org.ignus.ignus18.data

import android.preference.PreferenceManager
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.ignus.ignus18.App

class Helper {

    companion object {
        var event: Event? = null
        var eventCategories: List<EventCategory> = getDataClassFromJSON(getLocalJSON())


        fun isConnected(): Boolean {
            val command = "ping -c 1 google.com"
            return Runtime.getRuntime().exec(command).waitFor() == 0
        }

        fun getLocalJSON(): String {
            val sp = PreferenceManager.getDefaultSharedPreferences(App.instance.applicationContext)
            return sp.getString("theJSON", "[]")
        }

        fun getDataClassFromJSON(JsonStr: String): List<EventCategory> {
            return Gson().fromJson(JsonStr, object : TypeToken<List<EventCategory>>() {}.type)
        }

    }
}