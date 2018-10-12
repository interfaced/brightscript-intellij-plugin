package com.interfaced.brs.lang.psi

import com.intellij.psi.tree.IElementType
import com.interfaced.brs.lang.BSLanguage

class BSTokenType(debugName: String) : IElementType(debugName, BSLanguage.INSTANCE) {
    override fun toString(): String = "Brs:${super.toString()}"
}