package com.mighty16.testapp.presentation

import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.mighty16.testapp.R
import com.mighty16.testapp.domain.PhotoEntity
import com.mighty16.testapp.domain.PostEntity
import com.xwray.groupie.kotlinandroidextensions.Item
import com.xwray.groupie.kotlinandroidextensions.ViewHolder
import kotlinx.android.synthetic.main.list_photo_item.view.*
import kotlinx.android.synthetic.main.list_post_item.view.*


class PostItem(private val post: PostEntity) : Item() {

    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.containerView.postTitle.text = post.title
        viewHolder.containerView.postBody.text = post.body
    }

    override fun getLayout(): Int = R.layout.list_post_item

}

class LoadingItem : Item() {

    override fun bind(viewHolder: ViewHolder, position: Int) {}

    override fun getLayout(): Int = R.layout.list_loading_indicator

}

class PhotoItem(private val photo: PhotoEntity) : Item() {

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

}
