package com.mighty16.testapp.data

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.mighty16.testapp.data.api.ApiService
import com.mighty16.testapp.data.api.PhotoApiModel
import com.mighty16.testapp.data.api.PostApiModel
import com.mighty16.testapp.domain.PhotoEntity
import com.mighty16.testapp.domain.PostEntity
import com.mighty16.testapp.domain.RepositoryContract
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import kotlin.random.Random

class Repository(apiUrl: String, private val postsPageSize: Int = 20) : RepositoryContract {

    private val apiService: ApiService

    private val random: Random = Random(System.currentTimeMillis())

    init {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        val httpClient = OkHttpClient.Builder()
            //.addInterceptor(loggingInterceptor)
            .build()
        val retrofit = Retrofit.Builder()
            .client(httpClient)
            .baseUrl(apiUrl)
            .addConverterFactory(MoshiConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build()

        apiService = retrofit.create(ApiService::class.java)
    }


    override suspend fun getRandomPhotos(count: Int): List<PhotoEntity> {
        val response = apiService.getPhotos().await()
        if (response.isSuccessful) {
            val photos = response.body()!!
            val startIndex = random.nextInt(photos.size / 2)
            val photosList = photos.subList(startIndex, startIndex + count)
            return mapPhotos(photosList)
        }
        throw RuntimeException(response.message())
    }

    override suspend fun getPosts(page: Int): List<PostEntity> {
        val response = apiService.getPosts().await()
        if (response.isSuccessful) {
            val offset = page * postsPageSize
            val posts = response.body()!!.subList(offset, postsPageSize)
            return mapPosts(posts)
        }
        throw RuntimeException(response.message())
    }

    private fun mapPosts(posts: List<PostApiModel>): List<PostEntity> {
        return posts.map { post ->
            PostEntity(
                userId = post.userId,
                id = post.id,
                title = post.title,
                body = post.body
            )
        }
    }

    private fun mapPhotos(photos: List<PhotoApiModel>): List<PhotoEntity> {
        return photos.map { photo ->
            PhotoEntity(
                albumId = photo.albumId,
                id = photo.id,
                title = photo.title,
                url = photo.url,
                thumbnailUrl = photo.thumbnailUrl
            )
        }
    }

}