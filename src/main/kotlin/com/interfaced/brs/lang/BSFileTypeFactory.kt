package com.interfaced.brs.lang

import com.intellij.openapi.fileTypes.FileTypeConsumer
import com.intellij.openapi.fileTypes.FileTypeFactory

class BSFileTypeFactory : FileTypeFactory() {
    override fun createFileTypes(fileTypeConsumer: FileTypeConsumer) {
        fileTypeConsumer.consume(BSFileType.INSTANCE)
    }
}