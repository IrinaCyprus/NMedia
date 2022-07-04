package ru.netology.nmedia.viewModel

import android.os.Parcel
import android.os.Parcelable

class SingleLiveEvent() : Parcelable {
    constructor(parcel: Parcel) : this() {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {

    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<SingleLiveEvent> {
        override fun createFromParcel(parcel: Parcel): SingleLiveEvent {
            return SingleLiveEvent(parcel)
        }

        override fun newArray(size: Int): Array<SingleLiveEvent?> {
            return arrayOfNulls(size)
        }
    }
}