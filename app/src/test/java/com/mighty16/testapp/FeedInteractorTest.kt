package com.mighty16.testapp

import com.mighty16.testapp.domain.FeedInteractor
import com.mighty16.testapp.domain.RepositoryContract
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertTrue
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.*


class FeedInteractorTest {

    private lateinit var interactor: FeedInteractor
    private lateinit var repository: RepositoryContract


    @Before
    fun setUp() {
        repository = spy(TestRepository())
        interactor = FeedInteractor(repository, TestRepository.TEST_PHOTOS_COUNT)
    }

    @Test
    fun testFeedFirstPage() {
        GlobalScope.launch {
            val result = interactor.getFeedPage(0)
            verify(repository).getRandomPhotos(TestRepository.TEST_POSTS_PAGE_SIZE)
            assertEquals(0, result.page)
            assertTrue("Posts list is empty", result.posts.isNotEmpty())
            assertEquals(result.posts.size, TestRepository.TEST_POSTS_PAGE_SIZE)
            assertTrue("Photos list is empty", result.photos.isNotEmpty())
        }
    }

    @Test
    fun testFeedPage() {
        GlobalScope.launch {
            val result = interactor.getFeedPage(1)
            assertEquals(1, result.page)
            assertTrue("Posts list is empty", result.posts.isNotEmpty())
            assertEquals(result.posts.size, TestRepository.TEST_POSTS_PAGE_SIZE)
            assertTrue("Photos list mist be empty", result.photos.isEmpty())
        }
    }

    @Test
    fun testGetPhotos() {
        GlobalScope.launch {
            val photos = interactor.getPhotos()
            assertTrue("Photos list is empty", photos.isNotEmpty())
            assertEquals(photos.size, TestRepository.TEST_PHOTOS_COUNT)
        }
    }


}