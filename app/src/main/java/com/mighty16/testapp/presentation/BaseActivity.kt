package com.mighty16.testapp.presentation

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.mighty16.testapp.R

@SuppressLint("Registered")
abstract class BaseActivity<P : BasePresenter<V>, V> : AppCompatActivity() {

    protected lateinit var presenter: P

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        presenter = lastCustomNonConfigurationInstance?.let {
            it as P
        } ?: createPresenter()


    }

    abstract fun createPresenter(): P

    override fun onStart() {
        super.onStart()
        presenter.attachView(this as V)
    }

    override fun onStop() {
        super.onStop()
        presenter.detachView()
    }

    override fun onPause() {
        super.onPause()
        presenter.detachView()
    }

    override fun onResume() {
        super.onResume()
        presenter.attachView(this as V)
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.detachView()
        presenter.dispose()
    }

    override fun onRetainCustomNonConfigurationInstance(): Any = presenter

    fun errorToast(error: Throwable) {
        Toast.makeText(this, error.message ?: getString(R.string.unknown_error), Toast.LENGTH_SHORT).show()
    }


}