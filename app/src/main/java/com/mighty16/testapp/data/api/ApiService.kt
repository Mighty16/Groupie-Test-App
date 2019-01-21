package com.mighty16.testapp.data.api

import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {

    @GET("/posts")
    fun getPosts(): Deferred<Response<List<PostApiModel>>>

    @GET("/photos")
    fun getPhotos(): Deferred<Response<List<PhotoApiModel>>>

}