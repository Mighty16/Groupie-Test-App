package com.mighty16.testapp

import com.mighty16.testapp.presentation.BasePresenter
import org.junit.Assert
import org.junit.Test

class BaseTest {

    @Test
    fun presenterDoWhenViewAttachedActionCount() {
        val presenter = TestPresenter()
        val view = TestView()
        Assert.assertTrue("Pending actions count on start > 0 ", presenter.pendingActionsCount == 0)
        presenter.doWhenViewAttached {  }
        presenter.doWhenViewAttached {  }
        presenter.doWhenViewAttached {  }
        Assert.assertTrue("Pending actions count is wrong", presenter.pendingActionsCount == 3)
        presenter.attachView(view)
        Assert.assertTrue("Pending actions count after attach > 0 ", presenter.pendingActionsCount == 0)
    }

    @Test
    fun presenterDoWhenViewAttachedActionCalls() {
        val presenter = TestPresenter()
        val view = TestView()
        var counter = 0
        presenter.doWhenViewAttached { counter++ }
        presenter.doWhenViewAttached { counter++ }
        presenter.attachView(view)
        Assert.assertEquals(2,counter)
    }

    class TestPresenter : BasePresenter<TestView>() {

        val pendingActionsCount: Int
            get() = onViewAttachedActions.size

        override fun onViewReady() {

        }

    }

    class TestView {}
}