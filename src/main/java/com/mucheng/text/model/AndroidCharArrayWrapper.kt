package com.mucheng.text.model

import android.text.GetChars
import com.mucheng.text.model.exception.IndexOutOfBoundsException
import java.nio.CharBuffer

open class AndroidCharArrayWrapper(private val array: CharArray, private var _length: Int) : GetChars {

    fun setLength(length: Int) {
        this._length = length
    }

    override val length: Int
        get() {
            return _length
        }

    override fun get(index: Int): Char {
        return array[index]
    }

    override fun subSequence(startIndex: Int, endIndex: Int): CharSequence {
        return CharBuffer.wrap(array, startIndex, endIndex - startIndex)
    }

    override fun getChars(start: Int, end: Int, dest: CharArray?, destoff: Int) {
        checkRangeIndex(start, end)
        if (dest == null) {
            throw NullPointerException("The dest CharArray can not be null.")
        }
        return System.arraycopy(array, start, dest, destoff, end - start)
    }

    /**
     * 检验目标索引是否越界
     *
     * @param targetIndex 需要检验的索引
     * @throws IndexOutOfBoundsException
     * */
    @Throws(IndexOutOfBoundsException::class)
    @Suppress("NOTHING_TO_INLINE")
    protected inline fun checkIndex(targetIndex: Int) {
        if (targetIndex < 0) {
            throw IndexOutOfBoundsException(targetIndex)
        }
        if (targetIndex > length) {
            throw IndexOutOfBoundsException(targetIndex)
        }
    }

    /**
     * 检验目标区间是否越界
     *
     * @param startIndex 需要检验的起始索引
     * @param endIndex 需要检验的结束索引
     * @throws IndexOutOfBoundsException
     * */
    @Throws(IndexOutOfBoundsException::class)
    @Suppress("NOTHING_TO_INLINE")
    protected inline fun checkRangeIndex(startIndex: Int, endIndex: Int) {
        checkIndex(startIndex)
        checkIndex(endIndex) // 结束索引不包含, 因此允许等于 length
        if (startIndex > endIndex) {
            throw IndexOutOfBoundsException(endIndex - startIndex)
        }
    }

}