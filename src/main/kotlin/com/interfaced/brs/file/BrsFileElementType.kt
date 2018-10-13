package com.interfaced.brs.file

import com.intellij.psi.tree.IFileElementType
import com.intellij.psi.tree.IStubFileElementType
import com.interfaced.brs.lang.BrsFileStub
import com.interfaced.brs.lang.BrsLanguage

class BrsFileElementType : IStubFileElementType<BrsFileStub>(BrsLanguage.INSTANCE) {
    companion object {
        var INSTANCE: IFileElementType = BrsFileElementType()
    }
}