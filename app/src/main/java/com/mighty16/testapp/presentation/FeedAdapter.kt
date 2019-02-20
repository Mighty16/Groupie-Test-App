package com.mighty16.testapp.presentation

import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.Section
import com.xwray.groupie.ViewHolder

class FeedAdapter : GroupAdapter<ViewHolder>() {

    private val photosSection = Section()

    fun updateData(data: FeedPageViewData) {
        if (itemCount == 0) {
            if (data.photos.isNotEmpty()) {
                photosSection.addAll(data.photos)
                add(photosSection)
                addAll(data.posts)
            }
            return
        }

        photosSection.update(data.photos)
        //update( data.posts)
    }

    fun updatePhotosItem(photoItems: List<FeedListItem>) {
        val section = Section()
        section.addAll(photoItems)
        if (getAdapterPosition(section) == -1) {
            add(section)
        } else {
            update(photoItems)
        }
    }

    fun showLoading(show: Boolean) {
        val lastIndex = itemCount - 1
        val lastItem = getItem(lastIndex)
        val lastIsLoading = lastItem is LoadingItem

        if (show) {
            if (!lastIsLoading) {
                add(LoadingItem())
            }
        } else {
            if (lastIsLoading) {
                removeGroup(lastIndex)
            }
        }
    }

}