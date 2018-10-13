package com.interfaced.brs.lang.psi

import com.intellij.extapi.psi.PsiFileBase
import com.intellij.openapi.fileTypes.FileType
import com.intellij.psi.FileViewProvider
import com.interfaced.brs.lang.BrsFileType
import com.interfaced.brs.lang.BrsLanguage

class BrsFile(viewProvider: FileViewProvider) : PsiFileBase(viewProvider, BrsLanguage.INSTANCE) {
    override fun getFileType(): FileType = BrsFileType.INSTANCE

    override fun toString(): String = "BrightScriptFile:${this.name}"
}