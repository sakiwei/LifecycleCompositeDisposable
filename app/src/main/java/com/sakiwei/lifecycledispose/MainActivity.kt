package com.sakiwei.lifecycledispose

import android.arch.lifecycle.LifecycleActivity
import android.os.Bundle
import com.orhanobut.logger.Logger
import io.reactivex.Observable
import java.util.concurrent.TimeUnit

typealias L = Logger

class MainActivity : LifecycleActivity() {

    // create a LifecycleCompositeDisposable which will be disposed at onStop()
    val disposableBag by lazy { createOnPauseCompositeDisposable() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // keep firing a number on each second,
        // until LifecycleActivity.onStop() is triggered
        Observable.interval(1000, TimeUnit.MILLISECONDS)
                .doOnDispose {
                    L.d("doOnDispose")
                }
                .subscribe {
                    L.d(it)
                }
                // remember to add the disposable to the CompositeDisposable
                .addTo(disposableBag)
    }
}
