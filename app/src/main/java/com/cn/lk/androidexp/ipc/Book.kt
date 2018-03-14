package com.cn.lk.androidexp.ipc

import android.os.Parcel
import android.os.Parcelable

/**
 * http://blog.csdn.net/luoyanglizi/article/details/51980630
 */
class Book() : Parcelable {
    var bookId = 0
    var bookName = ""

    constructor(parcel: Parcel) : this() {
        bookId = parcel.readInt()
        bookName = parcel.readString()
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeInt(this.bookId)
        dest.writeString(this.bookName)
    }

    override fun describeContents() = 0

    companion object CREATOR : Parcelable.Creator<Book> {
        override fun createFromParcel(parcel: Parcel) : Book? = Book(parcel)

        override fun newArray(size: Int): Array<Book?>  = arrayOfNulls(size)
    }

}