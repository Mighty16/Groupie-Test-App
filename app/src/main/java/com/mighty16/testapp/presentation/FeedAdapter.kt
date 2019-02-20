package com.mighty16.testapp.presentation

import com.xwray.groupie.Group
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.Section
import com.xwray.groupie.ViewHolder

class FeedAdapter(photosHeaderListener: PhotosHeaderItem.PhotosHeaderListener) : GroupAdapter<ViewHolder>() {

    private val photosHeaderItem = PhotosHeaderItem(photosHeaderListener)
    private val photosSection = Section().apply {
        setHeader(photosHeaderItem)
    }

    fun insertItem(index: Int, item: FeedListItem) {
        add((index - photosSection.itemCount)+1, item)
    }

    fun updateData(data: FeedPageViewData) {
        val newItems: MutableList<Group> = mutableListOf(photosSection)
        newItems.addAll(data.posts)
        photosSection.update(data.photos)
        update(newItems)
    }

    fun updatePhotosItem(photoItems: List<FeedListItem>) {
        photosSection.update(photoItems)
    }

    fun showPhotosRefreshing(show: Boolean) {
        photosHeaderItem.isLoading = show
        photosSection.notifyItemChanged(0)
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