package ru.netology.nmedia.util

import android.content.ContentValues.TAG
import android.os.Parcel
import android.os.Parcelable
import android.util.Log
import androidx.annotation.MainThread
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import java.util.concurrent.atomic.AtomicBoolean

class SingleLiveEvent<T> : MutableLiveData<T>() {
    private val pending = AtomicBoolean(false)

    @MainThread
    override fun observe(owner: LifecycleOwner, observer: Observer<in T>) {
        if (hasActiveObservers()) {
            Log.w(TAG, "sfjdgjk")
        }
        super.observe(owner, Observer { t ->
            if (pending.compareAndSet(true, false)) {
                observer.onChanged(t)
            }
        })
    }

    @MainThread
    override fun setValue(t: T?) {
        pending.set(true)
        super.setValue(value)
    }

}
//    override fun writeToParcel(parcel: Parcel, flags: Int) {
//    }
//
//    override fun describeContents(): Int {
//        return 0
//    }
//
//    companion object CREATOR : Parcelable.Creator<SingleLiveEvent> {
//        override fun createFromParcel(parcel: Parcel): SingleLiveEvent {
//            return SingleLiveEvent(parcel)
//        }
//
//        override fun newArray(size: Int): Array<SingleLiveEvent?> {
//            return arrayOfNulls(size)
//        }
//    }
