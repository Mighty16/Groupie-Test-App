package com.mighty16.testapp

import android.app.Application
import com.mighty16.testapp.data.TestRepository
import com.mighty16.testapp.domain.FeedInteractor
import com.mighty16.testapp.domain.RepositoryContract

class App : Application() {

    companion object {
        lateinit var instance: App
            private set
    }

    private lateinit var repository: RepositoryContract

    override fun onCreate() {
        super.onCreate()
        instance = this
        //repository = Repository("https://jsonplaceholder.typicode.com/")
        repository = TestRepository()
    }

    fun getFeedInteractor(): FeedInteractor {
        return FeedInteractor(repository, 12)
    }

}