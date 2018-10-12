package com.interfaced.brs.lang

import com.intellij.lang.Language
import com.intellij.openapi.fileTypes.LanguageFileType

class BSLanguage : Language("BrightScript", "application/x-bright-script") {
    override fun getAssociatedFileType(): LanguageFileType? = BSFileType.INSTANCE

    companion object {
        val INSTANCE = BSLanguage()
    }
}