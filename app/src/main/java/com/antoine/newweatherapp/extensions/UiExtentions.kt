package com.antoine.newweatherapp.extensions

import androidx.recyclerview.widget.RecyclerView
import com.antoine.newweatherapp.utils.EndlessRecyclerOnScrollListener

fun RecyclerView.onLoadMore(block: () -> Unit) {
    this.clearOnScrollListeners()
    this.addOnScrollListener(object: EndlessRecyclerOnScrollListener(){
        override fun onLoadMore() {
            block.invoke()
        }

    })
}