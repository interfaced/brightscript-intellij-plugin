package com.interfaced.brs.file

import com.intellij.psi.tree.IFileElementType
import com.intellij.psi.tree.IStubFileElementType
import com.interfaced.brs.lang.BSFileStub
import com.interfaced.brs.lang.BSLanguage

class BSFileElementType : IStubFileElementType<BSFileStub>(BSLanguage.INSTANCE) {
    companion object {
        var INSTANCE: IFileElementType = BSFileElementType()
    }
}