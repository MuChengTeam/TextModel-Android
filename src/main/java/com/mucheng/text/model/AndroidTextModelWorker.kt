package com.mucheng.text.model

import com.mucheng.text.model.base.AbstractTextModel
import com.mucheng.text.model.standard.CharTable
import java.io.InputStream
import java.io.InputStreamReader
import java.io.OutputStream
import java.io.OutputStreamWriter
import java.io.Reader
import java.io.Writer

object AndroidTextModelWorker {

    fun createFromStream(
        stream: InputStream,
        onCreatingProgress: (() -> Boolean)? = null
    ): AndroidTextModel {
        return createFromReader(InputStreamReader(stream), onCreatingProgress)
    }

    fun createFromReader(
        reader: Reader,
        onCreatingProgress: (() -> Boolean)? = null
    ): AndroidTextModel {
        val textModel = AndroidTextModel(threadSafe = false)
        val buffer = CharArray(8192 * 2)
        val charArrayWrapper = AndroidCharArrayWrapper(buffer, 0)
        var len: Int
        reader.use {
            while (reader.read(buffer).also { len = it } != -1) {
                if (onCreatingProgress != null && !onCreatingProgress()) {
                    break
                }
                charArrayWrapper.setLength(len)
                textModel.append(charArrayWrapper)
            }
        }
        textModel.setThreadSafe(true)
        return textModel
    }

    fun outputToStream(
        textModel: AbstractTextModel,
        stream: OutputStream,
        onOutputProgress: (() -> Boolean)? = null
    ) {
        return outputToWriter(textModel, OutputStreamWriter(stream), onOutputProgress)
    }

    @Suppress("OPT_IN_USAGE")
    fun outputToWriter(
        textModel: AbstractTextModel,
        writer: Writer,
        onOutputProgress: (() -> Boolean)?
    ) {
        val iterator = textModel.textRowIterator()
        val newLine = CharTable.CONSTANT_NEW_LINE
        writer.use {
            while (iterator.hasNext()) {
                if (onOutputProgress != null && !onOutputProgress()) {
                    break
                }
                val textRow = iterator.next()
                val unsafeValue = textRow.unsafeValue()
                writer.write(unsafeValue, 0, unsafeValue.size)
                if (iterator.hasNext()) {
                    writer.write(newLine)
                }
                writer.flush()
            }
        }
    }

    fun copyFromTextModel(
        textModel: AbstractTextModel,
        onCopingProgress: (() -> Boolean)? = null
    ): AndroidTextModel {
        val copiedTextModel = AndroidTextModel(textModel.lastLine, threadSafe = false)
        val iterator = textModel.textRowIterator()
        val newLine = CharTable.CONSTANT_NEW_LINE
        while (iterator.hasNext()) {
            if (onCopingProgress != null && !onCopingProgress()) {
                break
            }
            val copiedTextRow = iterator.next().copy()
            copiedTextModel.append(copiedTextRow)
            if (iterator.hasNext()) {
                copiedTextModel.append(newLine)
            }
        }
        copiedTextModel.setThreadSafe(true)
        return copiedTextModel
    }

}