package com.mighty16.testapp.data.api

import com.squareup.moshi.Json

data class PostApiModel(
    @field:Json(name = "userId") val userId: Int,
    @field:Json(name = "id") val id: Int,
    @field:Json(name = "title") val title: String,
    @field:Json(name = "body") val body: String
)

data class PhotoApiModel(
    @field:Json(name = "albumId") val albumId: Int,
    @field:Json(name = "id") val id: Int,
    @field:Json(name = "title") val title: String,
    @field:Json(name = "url") val url: String,
    @field:Json(name = "thumbnailUrl") val thumbnailUrl: String
)