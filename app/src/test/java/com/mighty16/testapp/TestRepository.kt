package com.mighty16.testapp

import com.mighty16.testapp.domain.PhotoEntity
import com.mighty16.testapp.domain.PostEntity
import com.mighty16.testapp.domain.RepositoryContract

open class TestRepository : RepositoryContract {

    private val testPosts = listOf(
        createTestPost(1, "Title 1", "body 1"),
        createTestPost(2, "Title 2", "body 2"),
        createTestPost(3, "Title 3", "body 3"),
        createTestPost(4, "Title 4", "body 4"),
        createTestPost(5, "Title 5", "body 5"),
        createTestPost(6, "Title 6", "body 6"),
        createTestPost(7, "Title 7", "body 7"),
        createTestPost(8, "Title 8", "body 8"),
        createTestPost(9, "Title 9", "body 9"),
        createTestPost(10, "Title 10", "body 10")
    )

    override suspend fun getRandomPhotos(count: Int): List<PhotoEntity> {
        return listOf(
            createTestPhoto(1),
            createTestPhoto(2),
            createTestPhoto(3),
            createTestPhoto(4),
            createTestPhoto(5)
        ).shuffled()
    }

    override suspend fun getPosts(page: Int): List<PostEntity> {
        return testPosts.subList(page * 5, (page * 5) + 5)
    }



    companion object {
        const val TEST_USER_ID = 1
        const val TEST_POSTS_PAGE_SIZE = 5
        const val TEST_PHOTOS_COUNT = 5

        fun createTestPhoto(id: Int): PhotoEntity {
            return PhotoEntity(
                albumId = 1,
                id = id,
                title = "photo title",
                url = "photo url",
                thumbnailUrl = "thumbnail url"
            )
        }

        fun createTestPost(id: Int, title: String, body: String): PostEntity {
            return PostEntity(
                userId = TEST_USER_ID,
                id = id,
                title = title,
                body = body,
                color = 0
            )
        }

    }


}