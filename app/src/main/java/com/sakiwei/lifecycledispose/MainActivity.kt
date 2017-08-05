package com.sakiwei.lifecycledispose

import android.arch.lifecycle.LifecycleActivity
import android.os.Bundle
import android.util.Log
import io.reactivex.Observable
import java.util.concurrent.TimeUnit

class MainActivity : LifecycleActivity() {

    val TAG = "MainActivity"
    // create a LifecycleCompositeDisposable which will be disposed at onPause()
    val disposableBag by lazy { createOnPauseCompositeDisposable() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // keep firing a number on each second,
        // until LifecycleActivity.onStop() is triggered
        Observable.interval(1000, TimeUnit.MILLISECONDS)
                .doOnDispose {
                    Log.d(TAG, "Disposed!")
                }
                .subscribe {
                    Log.d(TAG, it.toString())
                }
                // remember to add the disposable to the CompositeDisposable
                .addTo(disposableBag)
    }
}
