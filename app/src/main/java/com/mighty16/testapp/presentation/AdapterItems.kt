package com.mighty16.testapp.presentation

import android.util.Log
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.mighty16.testapp.R
import com.mighty16.testapp.domain.PhotoEntity
import com.mighty16.testapp.domain.PostEntity
import com.xwray.groupie.kotlinandroidextensions.Item
import com.xwray.groupie.kotlinandroidextensions.ViewHolder
import kotlinx.android.synthetic.main.list_photo_item.view.*
import kotlinx.android.synthetic.main.list_post_item.view.*

abstract class FeedListItem : Item() {


}


data class PostItem(
    private val post: PostEntity,
    private val color: Int
) : FeedListItem() {

    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.containerView.postTitle.text = post.title
        viewHolder.containerView.postBody.text = post.body
        viewHolder.containerView.postTitle.setBackgroundColor(color)
    }

    override fun getLayout(): Int = R.layout.list_post_item

    override fun isSameAs(other: com.xwray.groupie.Item<*>?): Boolean {
        if (other !is PostItem) return false
        if (other.post.hashCode() != post.hashCode()) return false
        return color == other.color
    }


}

class LoadingItem : FeedListItem() {

    override fun bind(viewHolder: ViewHolder, position: Int) {}

    override fun getLayout(): Int = R.layout.list_loading_indicator

}

data class PhotoItem(private val photo: PhotoEntity) : FeedListItem() {

    override fun bind(viewHolder: ViewHolder, position: Int) {
        Glide.with(viewHolder.itemView)
            .load(photo.url)
            .apply(RequestOptions().override(200, 200))
            .into(viewHolder.itemView.photo)
    }

    override fun getLayout(): Int = R.layout.list_photo_item

    override fun getSpanSize(spanCount: Int, position: Int): Int {
        return 1
    }

    override fun isSameAs(other: com.xwray.groupie.Item<*>?): Boolean {
        if (other !is PhotoItem) return false
        return other.photo.hashCode() == photo.hashCode()
    }


}
