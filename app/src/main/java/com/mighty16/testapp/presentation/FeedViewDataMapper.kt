package com.mighty16.testapp.presentation

import android.graphics.Color
import com.mighty16.testapp.domain.FeedEntity
import com.xwray.groupie.Section

class FeedViewDataMapper {

    private val colors = intArrayOf(Color.GREEN, Color.RED, Color.GRAY, Color.MAGENTA, Color.CYAN)

    fun map(from: FeedEntity): FeedPageViewData {
        val postItems = from.posts.map { PostItem(it, colors[it.color]) }
        val photosItem = from.photos.map { PhotoItem(it) }
        return FeedPageViewData(postItems, photosItem, false)
    }

}