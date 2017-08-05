package com.sakiwei.lifecycledispose

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleActivity
import io.reactivex.disposables.Disposable

/**
 * disposable += observable.subscribe()
 */
operator fun LifecycleCompositeDisposable.plusAssign(disposable: Disposable) {
    add(disposable)
}

/**
 * Add the disposable to a LifecycleCompositeDisposable.
 * @param lifecycleCompositeDisposable LifecycleDisposableBag to add this disposable to
 * @return this instance
 */
fun Disposable.addTo(lifecycleCompositeDisposable: LifecycleCompositeDisposable): Disposable
        = apply { lifecycleCompositeDisposable.add(this) }


/**
 * Create a LifecycleCompositeDisposable triggered by lifecycle ON_DESTROY event
 */
fun LifecycleActivity.createOnDestroyCompositeDisposable()
        = LifecycleCompositeDisposable(Lifecycle.Event.ON_DESTROY, lifecycle)

/**
 * Create a LifecycleCompositeDisposable triggered by lifecycle ON_STOP event
 */
fun LifecycleActivity.createOnStopCompositeDisposable()
        = LifecycleCompositeDisposable(Lifecycle.Event.ON_STOP, lifecycle)

/**
 * Create a LifecycleCompositeDisposable triggered by lifecycle ON_PAUSE event
 */
fun LifecycleActivity.createOnPauseCompositeDisposable()
        = LifecycleCompositeDisposable(Lifecycle.Event.ON_PAUSE, lifecycle)