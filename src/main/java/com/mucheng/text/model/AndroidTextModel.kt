package com.mucheng.text.model

import com.mucheng.text.model.mark.UnsafeApi
import com.mucheng.text.model.standard.TextModel
import com.mucheng.text.model.standard.TextRow

@Suppress("unused", "LeakingThis")
open class AndroidTextModel(capacity: Int, threadSafe: Boolean) : TextModel(capacity, threadSafe) {

    constructor(capacity: Int) : this(capacity, true)

    constructor(threadSafe: Boolean) : this(DEFAULT_CAPACITY, threadSafe)

    constructor() : this(DEFAULT_CAPACITY, true)

    constructor(charSequence: CharSequence) : this(charSequence.length) {
        append(charSequence)
    }

    constructor(charSequence: CharSequence, threadSafe: Boolean) : this(
        charSequence.length,
        threadSafe
    ) {
        append(charSequence)
    }

    override fun createTextRow(): TextRow {
        return AndroidTextRow()
    }

    override fun getTextRow(line: Int): AndroidTextRow {
        return super.getTextRow(line) as AndroidTextRow
    }

    @UnsafeApi
    override fun getTextRowUnsafe(line: Int): AndroidTextRow {
        return super.getTextRowUnsafe(line) as AndroidTextRow
    }

}