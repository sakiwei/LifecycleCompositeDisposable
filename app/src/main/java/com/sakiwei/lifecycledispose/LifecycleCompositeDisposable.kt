package com.sakiwei.lifecycledispose

import android.arch.lifecycle.Lifecycle
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

/**
 * LifecycleCompositeDisposable is the same as CompositeDisposable
 * but dispose according to the lifecycle.
 */
class LifecycleCompositeDisposable(event: Lifecycle.Event, lifecycle: Lifecycle) : Disposable {

    private val compositeDisposable = CompositeDisposable()
    val observer: LifecycleDisposeObserver = LifecycleDisposeObserver(lifecycle)

    init {
        observer.add(compositeDisposable, event)
    }

    override fun dispose() {
        compositeDisposable.dispose()
    }

    override fun isDisposed(): Boolean = compositeDisposable.isDisposed

    fun add(disposable: Disposable): Boolean = compositeDisposable.add(disposable)

    fun addAll(vararg disposables: Disposable): Boolean = compositeDisposable.addAll(*disposables)

    fun remove(disposable: Disposable): Boolean = compositeDisposable.remove(disposable)

    fun delete(disposable: Disposable): Boolean = compositeDisposable.delete(disposable)

    fun clear() {
        compositeDisposable.clear()
    }

    fun size(): Int = compositeDisposable.size()
}