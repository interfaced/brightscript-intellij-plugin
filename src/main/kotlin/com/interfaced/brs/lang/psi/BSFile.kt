package com.interfaced.brs.lang.psi

import com.intellij.extapi.psi.PsiFileBase
import com.intellij.openapi.fileTypes.FileType
import com.intellij.psi.FileViewProvider
import com.interfaced.brs.lang.BSFileType
import com.interfaced.brs.lang.BSLanguage

class BSFile(viewProvider: FileViewProvider) : PsiFileBase(viewProvider, BSLanguage.INSTANCE) {
    override fun getFileType(): FileType = BSFileType.INSTANCE

    override fun toString(): String = "BrightScriptFile:${this.name}"
}