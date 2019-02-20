package com.mighty16.testapp.presentation

import com.mighty16.testapp.domain.FeedInteractorContract
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception

class FeedPresenter(private val interactor: FeedInteractorContract) : BasePresenter<FeedView>() {

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
        GlobalScope.launch(Dispatchers.Main) {
            try {
                val photos = withContext(Dispatchers.IO) { interactor.getPhotos().map { PhotoItem(it) } }
                doWhenViewAttached {
                    view?.showPhotoRefreshing(false)
                    view?.showPhotos(photos)
                }
            } catch (ex: Exception) {
                ex.printStackTrace()
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
        GlobalScope.launch(Dispatchers.Main) {
            try {
                currentViewData = withContext(Dispatchers.IO) { mapper.map(interactor.getFeedPage(page)) }
                doWhenViewAttached {
                    view?.showFeedLoading(false)
                    view?.showFeedPage(currentViewData)
                }
            } catch (ex: Exception) {
                ex.printStackTrace()
                doWhenViewAttached {
                    view?.showFeedPageError(ex)
                }
            }
        }
    }

}