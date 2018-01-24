package org.ignus.ignus18.data

import android.preference.PreferenceManager
import android.widget.Toast
import com.github.kittinunf.fuel.core.FuelError
import com.github.kittinunf.fuel.core.FuelManager
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.result.Result
import com.google.gson.Gson
import org.ignus.ignus18.App
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import java.net.URL

class EventCategoryRequest {

    private val apiUrl = "https://ignus.org/api/event/category-list/?format=json"


    fun execute(): EventCategoryResult {
        val eventCategoryJsonStr: String

        if (Helper.isConnected()) {

            eventCategoryJsonStr = URL(apiUrl).readText()
            storeJSONLocally(eventCategoryJsonStr)

        } else {

            eventCategoryJsonStr = Helper.getLocalJSON()
            doAsync { uiThread { Toast.makeText(App.instance, "Server/Internet error -> Outdated Data", Toast.LENGTH_LONG).show() } }
        }

        return EventCategoryResult(Helper.getDataClassFromJSON(eventCategoryJsonStr))
    }

    private fun storeJSONLocally(json: String) {
        val editor = PreferenceManager.getDefaultSharedPreferences(App.instance).edit()
        editor.putString("theJSON", json)
        editor.apply()
    }
}

class EventDetailRequest {

    fun execute(eventId: String): EventDetail {
        val apiUrl = "https://ignus.org/api/event/$eventId/detail/?format=json"
        val eventDetailJson: String
        if (Helper.isConnected())
            eventDetailJson = URL(apiUrl).readText()
        else {
            eventDetailJson = "{\"url\":\"/\",\"about\":\"Error\",\"details\":\"Error\",\"pdf\":\"null\"}"
            doAsync { uiThread { Toast.makeText(App.instance, "Server/Internet error -> Outdated Data", Toast.LENGTH_SHORT).show() } }
        }
        return Gson().fromJson(eventDetailJson, EventDetail::class.java)
    }
}

class RegisteredEventRequest {

    fun execute(): Result<String, FuelError> {
        val apiUrl = "https://ignus.org/api/dummy"
        val token = PreferenceManager.getDefaultSharedPreferences(App.instance).getString("token", "")


        FuelManager.instance.baseHeaders = mapOf("Authorization" to "JWT $token")
        val (request, response, result) = apiUrl.httpGet().responseString()
        return result
    }
}