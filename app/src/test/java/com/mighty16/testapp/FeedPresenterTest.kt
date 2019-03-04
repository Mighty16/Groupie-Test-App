package com.mighty16.testapp

import com.mighty16.testapp.domain.FeedEntity
import com.mighty16.testapp.domain.FeedInteractorContract
import com.mighty16.testapp.domain.PhotoEntity
import com.mighty16.testapp.presentation.FeedPresenter
import com.mighty16.testapp.presentation.FeedView
import kotlinx.coroutines.Dispatchers
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.*


class FeedPresenterTest {

    private lateinit var presenter: FeedPresenter
    private lateinit var view: FeedView

    @Before
    fun setup() {
        view = mock(FeedView::class.java)
        presenter = spy(FeedPresenter(MockInteractor(), Dispatchers.Default))
    }

    @Test
    fun loadDataOnViewReady() {
        presenter.attachView(view)
        verify(presenter,times(1)).onViewReady()
    }

//    @Test
//    fun getFirstPostPage(){
//        presenter.
//    }


    class MockInteractor : FeedInteractorContract {

        override suspend fun getFeedPage(page: Int): FeedEntity {
            return FeedEntity(listOf(), listOf(), page)
        }

        override suspend fun getPhotos(): List<PhotoEntity> {
            return listOf()
        }

    }

}