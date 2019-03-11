package com.mighty16.testapp

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.android.material.snackbar.Snackbar
import com.mighty16.testapp.presentation.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.include_empty_view.*

class MainActivity : BaseActivity<FeedPresenter, FeedView>(),
    FeedView, SwipeRefreshLayout.OnRefreshListener, PhotosHeaderItem.PhotosHeaderListener {

    private val adapter = createAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val layoutManager = GridLayoutManager(this, adapter.spanCount).apply {
            spanSizeLookup = adapter.spanSizeLookup
        }

        feedLists.layoutManager = layoutManager
        feedLists.adapter = adapter

        val animator = DefaultItemAnimator()
        animator.supportsChangeAnimations = true
        feedLists.itemAnimator = animator

        toolbar.title = getString(R.string.toolbar_title)

        emptyViewButton.setOnClickListener { presenter.onRefreshTriggered() }

        refreshLayout.setOnRefreshListener(this)

        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val index = viewHolder.adapterPosition
                val item = adapter.getItem(index) as FeedListItem
                presenter.onFeedItemRemoved(item, index)
            }

            override fun getSwipeDirs(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder): Int {
                val item = adapter.getItem(viewHolder.adapterPosition) as FeedListItem
                return item.swipeDirs
            }

        }).attachToRecyclerView(feedLists)

        setSupportActionBar(toolbar)
    }

    private fun createAdapter(): FeedAdapter {
        val adapter = FeedAdapter(this)
        adapter.spanCount = 4
        return adapter
    }


    override fun createPresenter(): FeedPresenter {
        return FeedPresenter(App.instance.getFeedInteractor())
    }

    override fun showFeedPage(feedPage: FeedPageViewData) {
        adapter.updateData(feedPage)
    }

    override fun onRefresh() {
        presenter.onRefreshTriggered()
    }

    override fun showFeedPageError(error: Throwable) {
        errorToast(error)
    }

    override fun showFeedLoading(show: Boolean) {
        emptyViewContainer.visibility = View.GONE
        if (refreshLayout.isRefreshing && !show) {
            loadingIndicator.visibility = View.GONE
            refreshLayout.isRefreshing = false
            return
        }
        if (adapter.itemCount > 0) {
            adapter.showLoading(show)
            return
        }
        loadingIndicator.visibility = if (show) View.VISIBLE else View.GONE
    }

    override fun showEmptyList() {
        adapter.clear()
        emptyViewContainer.visibility = View.VISIBLE
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

    override fun onRefreshButtonClicked(item: PhotosHeaderItem) {
        presenter.onRefreshPhotosClicked()
    }

    override fun showPhotos(photos: List<PhotoItem>) {
        adapter.updatePhotosItem(photos)
    }

    override fun showPhotoRefreshing(show: Boolean) {
        adapter.showPhotosRefreshing(show)
    }

    override fun showItemRemoved(removedItem: FeedListItem, index: Int) {
        adapter.remove(removedItem)
        val text = getString(R.string.removed_item_notification_text).format((removedItem as PostItem).title)
        val snackBar = Snackbar.make(rootView, text, Snackbar.LENGTH_LONG)
        snackBar.setAction(R.string.removed_item_undo_text) { presenter.onUndoClicked() }
        snackBar.show()
    }

    override fun showItemRestored(restoredItem: FeedListItem, index: Int) {
        adapter.insertItem(index, restoredItem)
    }

    override fun showPhotosRefreshingError(error: Throwable) {
        errorToast(error)
    }
}
