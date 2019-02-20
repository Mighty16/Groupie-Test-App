package com.mighty16.testapp.data

import com.mighty16.testapp.domain.PhotoEntity
import com.mighty16.testapp.domain.PostEntity
import com.mighty16.testapp.domain.RepositoryContract
import kotlinx.coroutines.delay
import kotlin.random.Random

class TestRepository : RepositoryContract {

    private val rand = Random(System.currentTimeMillis())


    override suspend fun getRandomPhotos(count: Int): List<PhotoEntity> {
        delay(500)
        return listOf(
            createPhoto(1),
            createPhoto(2),
            createPhoto(3),
            createPhoto(4),
            createPhoto(5),
            createPhoto(6),
            createPhoto(7),
            createPhoto(8),
            createPhoto(9),
            createPhoto(10),
            createPhoto(11),
            createPhoto(12)
        ).shuffled()
    }

    override suspend fun getPosts(page: Int): List<PostEntity> {
        delay(600)
        return  listOf(
            createPost(1),
            createPost(2),
            createPost(3),
            createPost(4),
            createPost(5),
            createPost(6),
            createPost(7),
            createPost(8),
            createPost(9),
            createPost(10)
        ).shuffled()
    }

    private fun createPhoto(id: Int): PhotoEntity {
        return PhotoEntity(
            albumId = 1,
            id = id,
            title = "Photo $id",
            url = "https://via.placeholder.com/150",
            thumbnailUrl = "https://via.placeholder.com/64"
        )
    }

    private fun createPost(id: Int): PostEntity {
        return PostEntity(
            userId = 1,
            id = id,
            title = "Post $id",
            body = "Post $id body text. Lorem ipsum dolor sit amet, consectetur adipiscing elit, " +
                    "sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. " +
                    "Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi",
            color = rand.nextInt(PostEntity.COLORS_COUNT)
        )
    }

}