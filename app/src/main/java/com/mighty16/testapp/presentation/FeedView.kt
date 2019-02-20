package com.mighty16.testapp.presentation


interface FeedView {

    fun showFeedPage(feedPage: FeedPageViewData)
    fun showFeedPageError(error: Throwable)
    fun showFeedLoading(show: Boolean)
    fun showPhotos()
    fun showEmptyList()

}