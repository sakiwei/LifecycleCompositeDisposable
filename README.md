### LifecycleCompositeDisposable

Make RxJava's [CompositeDisposable](http://reactivex.io/RxJava/2.x/javadoc/io/reactivex/disposables/CompositeDisposable.html) disposes according to [Android Lifecycle component](https://developer.android.com/topic/libraries/architecture/lifecycle.html)

--- 

### Why

When we work with RxJava, we always need to add all the disposables to a [CompositeDisposable](http://reactivex.io/RxJava/2.x/javadoc/io/reactivex/disposables/CompositeDisposable.html) and you need an extra step to dispose it in `onPause()`, `onStop()` or `onDestroy()` of the activity and fragment.

When [Android Lifecycle](https://developer.android.com/topic/libraries/architecture/lifecycle.html) component is released by Android team, I have an idea that why don't we bind the lifecycle events with the CompositeDisposable. That's why I make a demonstration on how we could make any CompositeDisposable or even Disposable dispose according to Android Lifecycle events.

### Usage

Let say we want to dispose the CompositeDisposable when the activity is being paused:

```
class MainActivity : LifecycleActivity() {

    // create a LifecycleCompositeDisposable which will be disposed at onPause()
    val disposableBag = createOnPauseCompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // keep firing a number on each second,
        // until LifecycleActivity.onPause() is triggered
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
```

### Alernatives
[RxLifecycle](https://github.com/trello/RxLifecycle) does a very good job on binding activity and fragment lifecycle too.
