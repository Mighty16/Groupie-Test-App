package com.mighty16.testapp.presentation


abstract class BasePresenter<V> {

    protected var view: V? = null
    protected val onViewAttachedActions = mutableListOf<(V) -> Unit>()


    fun attachView(view: V) {
        this.view = view

        if (onViewAttachedActions.isNotEmpty()) {
            onViewAttachedActions.forEach { it.invoke(view) }
            onViewAttachedActions.clear()
        }
    }

    fun detachView() {
        view = null
    }

    fun doWhenViewAttached(action: (V) -> Unit) {
        view?.let{
            action(it)
            return
        }
        onViewAttachedActions.add(action)
    }

    abstract fun onViewReady()

    fun dispose() {

    }


}