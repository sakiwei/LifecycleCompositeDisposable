package com.sakiwei.lifecycledispose

import android.arch.lifecycle.LifecycleFragment
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.reactivex.Observable
import java.util.concurrent.TimeUnit


class MainFragment: LifecycleFragment() {

    val TAG = "MainFragment"
    // create a LifecycleCompositeDisposable which will be disposed at onPause()
    val disposableBag = createOnPauseCompositeDisposable()

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // keep firing a number on each second,
        // until LifecycleFragment.onPause() is triggered
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