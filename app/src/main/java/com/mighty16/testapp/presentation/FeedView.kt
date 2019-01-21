package com.mighty16.testapp.presentation

import com.mighty16.testapp.domain.FeedEntity

interface FeedView {

    fun showFeedPage(feedPage: FeedEntity)
    fun showFeedPageError(error: Throwable)
    fun showFeedLoading(show: Boolean)
    fun showPhotos()
    fun showEmptyList()

}