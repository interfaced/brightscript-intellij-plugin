package com.interfaced.brs.lang

import com.intellij.lang.Language
import com.intellij.openapi.fileTypes.LanguageFileType

class BrsLanguage : Language("BrightScript", "text/brightscript") {
    override fun getAssociatedFileType(): LanguageFileType? = BrsFileType.INSTANCE

    companion object {
        val INSTANCE = BrsLanguage()
    }
}