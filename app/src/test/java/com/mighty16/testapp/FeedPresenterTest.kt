package com.mighty16.testapp

import com.mighty16.testapp.domain.FeedEntity
import com.mighty16.testapp.domain.FeedInteractorContract
import com.mighty16.testapp.domain.PhotoEntity
import com.mighty16.testapp.domain.PostEntity
import com.mighty16.testapp.presentation.FeedPresenter
import com.mighty16.testapp.presentation.FeedView
import com.mighty16.testapp.presentation.PostItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers
import org.mockito.Mockito
import org.mockito.Mockito.*
import java.io.IOException


class FeedPresenterTest {

    private lateinit var presenter: FeedPresenter
    private lateinit var view: FeedView
    private lateinit var interactor: MockInteractor

    @Before
    fun setup() {
        view = mock(FeedView::class.java)
        interactor = spy(MockInteractor())
        presenter = spy(FeedPresenter(interactor, Dispatchers.Default, Dispatchers.Default))
    }

    @Test
    fun loadDataOnViewReady()= runBlocking {
        presenter.attachView(view)
        presenter.onViewReady()
        verify(view).showFeedLoading(true)
        verify(interactor).getFeedPage(0)
        delay(200)
        verify(view).showFeedPage(any())
        presenter.detachView()
    }

    @Test
    fun undoClickedWithoutRemovedAttached() {
        presenter.attachView(view)
        presenter.onUndoClicked()
        verify(view, never()).showItemRestored(any(), ArgumentMatchers.anyInt())
        presenter.detachView()
    }

    @Test
    fun undoClickedWithoutRemovedNotAttached() {
        presenter.onUndoClicked()
        verify(view, never()).showItemRestored(any(), ArgumentMatchers.anyInt())
    }

    @Test
    fun refreshPhotosAttached() = runBlocking {
        presenter.attachView(view)
        presenter.onRefreshPhotosClicked()
        verify(view).showPhotoRefreshing(true)
        delay(200)
        verify(view).showPhotoRefreshing(false)
        verify(view).showPhotos(listOf())
        presenter.detachView()
    }

    @Test
    fun refreshPhotoError() = runBlocking {
        val testPresenter = FeedPresenter(BrokenInteractor(), Dispatchers.Default, Dispatchers.Default)
        testPresenter.attachView(view)
        testPresenter.onRefreshPhotosClicked()
        verify(view).showPhotoRefreshing(true)
        delay(200)
        verify(view).showPhotoRefreshing(false)
        verify(view).showPhotosRefreshingError(any())
    }


    @Test
    fun getPostsPageWithError() = runBlocking {
        val testPresenter = spy(FeedPresenter(BrokenInteractor(), Dispatchers.Default, Dispatchers.Default))
        testPresenter.attachView(view)
        testPresenter.onViewReady()
        delay(200)
        verify(view).showFeedPageError(any())
        testPresenter.detachView()
    }


    @Test
    fun onUndoAfterRemoveClickedAttached() {
        val post = PostEntity(
            userId = 1,
            id = 1,
            title = "title",
            body = "text",
            color = 1
        )
        val itemToRemoved = PostItem(post, 0)
        presenter.onFeedItemRemoved(itemToRemoved, 0)
        presenter.onUndoClicked()
        verify(view, never()).showItemRestored(itemToRemoved, 0)
    }

    @Test
    fun onUndoAfterRemoveClickedNotAttached() {
        val post = PostEntity(
            userId = 1,
            id = 1,
            title = "title",
            body = "text",
            color = 1
        )
        val itemToRemoved = PostItem(post, 0)
        presenter.attachView(view)
        presenter.onFeedItemRemoved(itemToRemoved, 0)
        presenter.onUndoClicked()
        verify(view).showItemRestored(itemToRemoved, 0)
        presenter.detachView()
    }

    @Test
    fun onFeedItemRemovedViewAttached() {
        val post = PostEntity(
            userId = 1,
            id = 1,
            title = "title",
            body = "text",
            color = 1
        )
        val itemToRemoved = PostItem(post, 0)
        presenter.attachView(view)
        presenter.onFeedItemRemoved(itemToRemoved, 0)
        verify(view).showItemRemoved(itemToRemoved, 0)
        presenter.detachView()
    }

    @Test
    fun onFeedRemovedViewDetached() {
        val post = PostEntity(
            userId = 1,
            id = 1,
            title = "title",
            body = "text",
            color = 1
        )
        val itemToRemoved = PostItem(post, 0)
        presenter.onFeedItemRemoved(itemToRemoved, 0)
        verify(view, never()).showItemRemoved(itemToRemoved, 0)
    }

    @Test
    fun onClearButtonClickedViewAttached() {
        presenter.attachView(view)
        presenter.onClearButtonClicked()
        verify(view).showEmptyList()
        presenter.detachView()
    }

    @Test
    fun onClearButtonClickedViewNotAttached() {
        presenter.onClearButtonClicked()
        verify(view, never()).showEmptyList()
    }

    @Test
    fun onRefreshTriggeredAttached() {
        presenter.attachView(view)
        presenter.onRefreshTriggered()
        verify(view).showFeedLoading(true)
        presenter.detachView()
    }


    open class MockInteractor : FeedInteractorContract {

        override suspend fun getFeedPage(page: Int): FeedEntity {
            return FeedEntity(listOf(), listOf(), page)
        }

        override suspend fun getPhotos(): List<PhotoEntity> {
            return listOf()
        }
    }

    class BrokenInteractor : FeedInteractorContract {

        override suspend fun getFeedPage(page: Int): FeedEntity {
            throw exception()
        }

        override suspend fun getPhotos(): List<PhotoEntity> {
            throw exception()
        }

        fun exception(): IOException = IOException("Error loading data")

    }

    private fun <T> any(): T {
        Mockito.any<T>()
        return uninitialized()
    }

    private fun <T> uninitialized(): T = null as T

}