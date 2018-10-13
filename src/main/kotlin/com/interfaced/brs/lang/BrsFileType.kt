package com.interfaced.brs.lang

import com.intellij.openapi.fileTypes.LanguageFileType
import com.interfaced.brs.ide.icons.BrsIcons
import javax.swing.Icon

class BrsFileType : LanguageFileType(BrsLanguage.INSTANCE) {
    override fun getName(): String = "BrightScript"

    override fun getDescription(): String = "BrightScript file"

    override fun getDefaultExtension(): String = "brs"

    override fun getIcon(): Icon? = BrsIcons.FILE

    companion object {
        val INSTANCE = BrsFileType()
    }
}