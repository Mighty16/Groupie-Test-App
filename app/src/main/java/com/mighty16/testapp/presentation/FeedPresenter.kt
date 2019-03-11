package com.mighty16.testapp.presentation

import com.mighty16.testapp.domain.FeedInteractorContract
import kotlinx.coroutines.*
import java.lang.Exception

open class FeedPresenter(
    private val interactor: FeedInteractorContract,
    private val uiDispatcher: CoroutineDispatcher = Dispatchers.Main,
    private val bgDispatcher: CoroutineDispatcher = Dispatchers.IO
) : BasePresenter<FeedView>() {

    private val mapper: FeedViewDataMapper = FeedViewDataMapper()
    private var currentPostsPage: Int = 0
    private var lastRemovedIndex: Int = -1
    private var lastRemovedItem: FeedListItem? = null
    private lateinit var currentViewData: FeedPageViewData

    override fun onViewReady() {
        getPostsPage(currentPostsPage)
    }

    fun onFeedItemRemoved(item: FeedListItem, index: Int) {
        lastRemovedItem = item
        lastRemovedIndex = index
        view?.showItemRemoved(item, index)
    }

    fun onUndoClicked() {
        lastRemovedItem?.let {
            view?.showItemRestored(it, lastRemovedIndex)
        }
    }

    fun onRefreshPhotosClicked() {
        view?.showPhotoRefreshing(true)
        GlobalScope.launch(uiDispatcher) {
            try {
                val photos = withContext(bgDispatcher) { interactor.getPhotos().map { PhotoItem(it) } }
                doWhenViewAttached {view->
                    view.showPhotoRefreshing(false)
                    view.showPhotos(photos)
                }
            } catch (ex: Exception) {
                ex.printStackTrace()
                doWhenViewAttached {view->
                    view.showPhotoRefreshing(false)
                    view.showPhotosRefreshingError(ex)
                }
            }
        }
    }

    fun onClearButtonClicked() {
        view?.showEmptyList()
    }

    fun onRefreshTriggered() {
        getPostsPage(0)
    }

    private fun getPostsPage(page: Int) {
        view?.showFeedLoading(true)
        GlobalScope.launch(uiDispatcher) {
            try {
                currentViewData = withContext(bgDispatcher) { mapper.map(interactor.getFeedPage(page)) }
                doWhenViewAttached {
                    view?.showFeedLoading(false)
                    view?.showFeedPage(currentViewData)
                }
            } catch (ex: Exception) {
                ex.printStackTrace()
                doWhenViewAttached {view->
                    view.showFeedPageError(ex)
                }
            }
        }
    }

}