package com.mighty16.testapp.presentation

import com.mighty16.testapp.domain.FeedInteractorContract
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FeedPresenter(private val interactor: FeedInteractorContract) : BasePresenter<FeedView>() {

    private var currentPostsPage: Int = 0

    override fun onViewReady() {
        getPostsPage(currentPostsPage)
    }

    fun onFeedEndReached() {

    }

    fun onUpdatePhotosClicked() {

    }

    fun onCreateFeedClicked() {

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
            val resultPage = withContext(Dispatchers.IO) {
                interactor.getFeedPage(page)
            }
            doWhenViewAttached {
                view?.showFeedLoading(false)
                view?.showFeedPage(resultPage)
            }
        }
    }

}