package com.mighty16.testapp.presentation

abstract class BasePresenter<V> {

    protected var view: V? = null
    private val onViewAttachedActions = mutableListOf<() -> Unit>()

    private var firstAttach: Boolean = true

    fun attachView(view: V) {
        this.view = view

        if (firstAttach) {
            onViewReady()
            firstAttach = false
            return
        }

        if (onViewAttachedActions.isNotEmpty()) {
            onViewAttachedActions.forEach { it.invoke() }
            onViewAttachedActions.clear()
        }
    }

    fun detachView() {
        view = null
    }

    fun doWhenViewAttached(action: () -> Unit) {
        if (view == null) {
            onViewAttachedActions.add(action)
            return
        }
        action()
    }

    abstract fun onViewReady()


    fun dispose() {

    }


}