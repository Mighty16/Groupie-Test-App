package com.mighty16.testapp

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.mighty16.testapp.data.Repository
import com.mighty16.testapp.domain.FeedEntity
import com.mighty16.testapp.domain.FeedInteractor
import com.mighty16.testapp.presentation.*
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.Section
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity<FeedPresenter, FeedView>(), FeedView, SwipeRefreshLayout.OnRefreshListener {


    private val adapter = createAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val layoutManager = GridLayoutManager(this, adapter.spanCount).apply {
            spanSizeLookup = adapter.spanSizeLookup
        }
        feedLists.layoutManager = layoutManager
        feedLists.adapter = adapter
        toolbar.title = getString(R.string.toolbar_title)

        refreshLayout.setOnRefreshListener(this)

        setSupportActionBar(toolbar)
    }

    private fun createAdapter(): GroupAdapter<ViewHolder> {
        val adapter = GroupAdapter<ViewHolder>()
        adapter.spanCount = 4
        return adapter
    }


    override fun createPresenter(): FeedPresenter {
        val repository = Repository("https://jsonplaceholder.typicode.com/")
        val interactor = FeedInteractor(repository, 12)
        return FeedPresenter(interactor)
    }

    override fun showFeedPage(feedPage: FeedEntity) {
        val postItems = feedPage.posts.map { PostItem(it) }
        val photoItems = feedPage.photos.map { PhotoItem(it) }

        val photoGridCard = Section()
        photoGridCard.addAll(photoItems)

        adapter.add(photoGridCard)
        adapter.addAll(postItems)
    }

    override fun onRefresh() {
        presenter.onRefreshTriggered()
    }

    override fun showFeedPageError(error: Throwable) {
        errorToast(error)
    }

    override fun showFeedLoading(show: Boolean) {
        if (refreshLayout.isRefreshing && !show) {
            //adapter.clear()
            refreshLayout.isRefreshing = false
            return
        }
        if (adapter.itemCount == 0) {
            if (show) {
                loadingIndicator.visibility = View.VISIBLE
            } else {
                loadingIndicator.visibility = View.GONE
            }
            return
        }

        val lastIndex = adapter.itemCount - 1
        val lastItem = adapter.getItem(lastIndex)
        val lastIsLoading = lastItem is LoadingItem

        if (show) {
            if (!lastIsLoading) {
                adapter.add(LoadingItem())
            }
        } else {
            if (lastIsLoading) {
                adapter.removeGroup(lastIndex)
            }
        }
    }

    override fun showPhotos() {

    }

    override fun showEmptyList() {
        adapter.clear()

    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_list, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == R.id.clearList) {
            presenter.onClearButtonClicked()
        }
        return super.onOptionsItemSelected(item)
    }


}
