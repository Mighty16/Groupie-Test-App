package com.mighty16.testapp.presentation


data class FeedPageViewData(
    val posts: List<FeedListItem>,
    val photos: List<FeedListItem>,
    val lastPage: Boolean
) {
}