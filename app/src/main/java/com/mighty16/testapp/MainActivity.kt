package com.mighty16.testapp

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.mighty16.testapp.data.TestRepository
import com.mighty16.testapp.domain.FeedInteractor
import com.mighty16.testapp.presentation.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.include_empty_view.*


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
                val item = adapter.getItem(viewHolder.adapterPosition)
                adapter.remove(item)
            }

        }).attachToRecyclerView(feedLists)

        setSupportActionBar(toolbar)
    }

    private fun createAdapter(): FeedAdapter {
        val adapter = FeedAdapter()
        adapter.spanCount = 4
        return adapter
    }


    override fun createPresenter(): FeedPresenter {
        //val repository = Repository("https://jsonplaceholder.typicode.com/")
        val repository = TestRepository()
        val interactor = FeedInteractor(repository, 12)
        return FeedPresenter(interactor)
    }

    override fun showFeedPage(feedPage: FeedPageViewData) {
        adapter.updateData(feedPage)
        feedLists.post { feedLists.invalidateItemDecorations() }
    }

    override fun onRefresh() {
        presenter.onRefreshTriggered()
    }

    override fun showFeedPageError(error: Throwable) {
        errorToast(error)
    }

    override fun showFeedLoading(show: Boolean) {
        if (refreshLayout.isRefreshing && !show) {
            refreshLayout.isRefreshing = false
            return
        }
        if (adapter.itemCount > 0) {
            adapter.showLoading(show)
            return
        }
        emptyViewContainer.visibility = View.GONE
        loadingIndicator.visibility = if (show) View.VISIBLE else View.GONE
    }

    override fun showPhotos() {

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


}
