package com.mighty16.testapp.domain

interface FeedInteractorContract {

    suspend fun getFeedPage(page: Int): FeedEntity
    suspend fun getPhotos(): List<PhotoEntity>

}