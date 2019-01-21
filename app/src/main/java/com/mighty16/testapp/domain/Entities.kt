package com.mighty16.testapp.domain

data class FeedEntity(
    val posts: List<PostEntity>,
    val photos: List<PhotoEntity>,
    val page: Int
)


data class PhotoEntity(
    val albumId: Int,
    val id: Int,
    val title: String,
    val url: String,
    val thumbnailUrl: String
)

data class PostEntity(
    val userId: Int,
    val id: Int,
    val title: String,
    val body: String
)

