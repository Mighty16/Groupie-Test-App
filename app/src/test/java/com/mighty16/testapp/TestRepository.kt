package com.mighty16.testapp

import com.mighty16.testapp.domain.PhotoEntity
import com.mighty16.testapp.domain.PostEntity
import com.mighty16.testapp.domain.RepositoryContract

class TestRepository : RepositoryContract {

    private val testPosts = listOf(
        createTestPost(1, "Title 1", "body 1"),
        createTestPost(2, "Title 2", "body 2"),
        createTestPost(3, "Title 3", "body 3"),
        createTestPost(4, "Title 4", "body 4"),
        createTestPost(5, "Title 5", "body 5")
    )

    override suspend fun getRandomPhotos(count: Int): List<PhotoEntity> {
        return listOf(
            createTestPhoto(1),
            createTestPhoto(2),
            createTestPhoto(3),
            createTestPhoto(4),
            createTestPhoto(5)
        )
    }

    override suspend fun getPosts(page: Int): List<PostEntity> {
        return testPosts
    }

    private fun createTestPhoto(id: Int): PhotoEntity {
        return PhotoEntity(
            albumId = 1,
            id = id,
            title = "photo title",
            url = "photo url",
            thumbnailUrl = "thumbnail url"
        )
    }

    private fun createTestPost(id: Int, title: String, body: String): PostEntity {
        return PostEntity(
            userId = TEST_USER_ID,
            id = id,
            title = title,
            body = body,
            color = 0
        )
    }

    companion object {
        const val TEST_USER_ID = 1
        const val TEST_POSTS_PAGE_SIZE = 5
        const val TEST_PHOTOS_COUNT = 5
    }


}