package org.ignus.ignus18.testing

import android.support.v7.widget.GridLayoutManager
import org.ignus.ignus18.App


class LayoutManager : GridLayoutManager(App.instance, 2) {

    val x = findLastVisibleItemPosition()


    override fun setSpanCount(spanCount: Int) {
        super.setSpanCount(spanCount)
    }
}