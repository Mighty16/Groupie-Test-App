package com.mighty16.testapp.domain

interface RepositoryContract {

    suspend fun getRandomPhotos(count: Int): List<PhotoEntity>
    suspend fun getPosts(page: Int): List<PostEntity>

}