package org.ignus.ignus18

import android.app.Application
import org.ignus.ignus18.ui.utils.DelegatesExt

class App : Application() {
    companion object {
        var instance: App by DelegatesExt.notNullSingleValue()
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }

}