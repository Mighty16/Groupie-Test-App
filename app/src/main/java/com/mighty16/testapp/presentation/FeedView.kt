package com.mighty16.testapp.presentation


interface FeedView {

    fun showFeedPage(feedPage: FeedPageViewData)
    fun showFeedPageError(error: Throwable)
    fun showFeedLoading(show: Boolean)
    fun showPhotos(photos: List<PhotoItem>)
    fun showEmptyList()
    fun showPhotoRefreshing(show: Boolean)
    fun showItemRemoved(removedItem: FeedListItem, index: Int)
    fun showItemRestored(restoredItem: FeedListItem, index: Int)

}