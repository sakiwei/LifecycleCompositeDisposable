package com.sakiwei.lifecycledispose

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.OnLifecycleEvent
import io.reactivex.disposables.Disposable

class LifecycleDisposeObserver(lifecycle: Lifecycle) : LifecycleObserver, Disposable {

    private var disposed = false

    init {
        lifecycle.addObserver(this)
    }

    private val disposableMap: MutableMap<Lifecycle.Event, List<Disposable>> = mutableMapOf()

    private fun disposableList(event: Lifecycle.Event): MutableList<Disposable> {
        return disposableMap[event]?.toMutableList() ?: mutableListOf<Disposable>()
    }

    private fun clearLDisposableList(event: Lifecycle.Event) {
        val list = disposableList(event)
        list.forEach {
            if (!it.isDisposed)
                it.dispose()
        }
        disposableMap[event] = mutableListOf()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun onCreate() {
        clearLDisposableList(Lifecycle.Event.ON_CREATE)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun onStart() {
        clearLDisposableList(Lifecycle.Event.ON_START)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun onResume() {
        clearLDisposableList(Lifecycle.Event.ON_RESUME)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun onPause() {
        clearLDisposableList(Lifecycle.Event.ON_PAUSE)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun onStop() {
        clearLDisposableList(Lifecycle.Event.ON_STOP)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroy() {
        clearLDisposableList(Lifecycle.Event.ON_DESTROY)
    }

    /**
     * Add a disposable to the disposable, it will be disposed when receiving a lifecycle event
     *
     * @param disposable Disposable that will be disposed when receiving a lifecycle event
     * @param event Lifecycle.Event the event triggers the disposable to be disposed
     */
    fun add(disposable: Disposable, event: Lifecycle.Event) {
        if (disposed)
            return

        val list = disposableList(event)
        if (!list.contains(disposable)) {
            list.add(disposable)
            disposableMap[event] = list
        }
    }

    override fun isDisposed(): Boolean = disposed

    override fun dispose() {
        if (disposed)
            return

        disposableMap.keys.forEach { clearLDisposableList(it) }

        disposed = true
    }
}