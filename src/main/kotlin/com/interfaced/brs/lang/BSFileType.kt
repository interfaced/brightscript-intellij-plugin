package com.interfaced.brs.lang

import com.intellij.openapi.fileTypes.LanguageFileType
import com.interfaced.brs.ide.icons.BSIcons
import javax.swing.Icon

class BSFileType : LanguageFileType(BSLanguage.INSTANCE) {
    override fun getName(): String = "BrightScript"

    override fun getDescription(): String = "BrightScript file"

    override fun getDefaultExtension(): String = "brs"

    override fun getIcon(): Icon? = BSIcons.FILE

    companion object {
        val INSTANCE = BSFileType()
    }
}