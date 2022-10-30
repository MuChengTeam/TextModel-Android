package com.mucheng.text.model

import android.text.GetChars
import com.mucheng.text.model.standard.TextRow

class AndroidTextRow(capacity: Int) : TextRow(capacity), GetChars {

    constructor() : this(DEFAULT_CAPACITY)

    constructor(charSequence: CharSequence) : this(charSequence.length) {
        append(charSequence)
    }

    @Suppress("OPT_IN_USAGE")
    override fun getChars(srcBegin: Int, srcEnd: Int, dst: CharArray?, dstBegin: Int) {
        checkRangeIndex(srcBegin, srcEnd)
        if (dst == null) {
            throw NullPointerException("The dest CharArray can not be null.")
        }
        val value = unsafeValue()
        System.arraycopy(value, srcBegin, dst, dstBegin, srcEnd - srcBegin)
    }


}