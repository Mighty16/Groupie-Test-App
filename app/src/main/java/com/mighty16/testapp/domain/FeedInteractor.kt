package com.mighty16.testapp.domain


class FeedInteractor(
    private val repository: RepositoryContract,
    private val photosCount: Int
) :
    FeedInteractorContract {

    override suspend fun getFeedPage(page: Int): FeedEntity {
        val posts = repository.getPosts(page)
        val photos = if (page == 0) {
            repository.getRandomPhotos(photosCount)
        } else listOf()
        return FeedEntity(posts, photos, page)
    }

    override suspend fun getPhotos(): List<PhotoEntity> {
        return repository.getRandomPhotos(photosCount)
    }
}