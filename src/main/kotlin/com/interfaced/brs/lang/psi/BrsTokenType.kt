package com.interfaced.brs.lang.psi

import com.intellij.psi.tree.IElementType
import com.interfaced.brs.lang.BrsLanguage

class BrsTokenType(debugName: String) : IElementType(debugName, BrsLanguage.INSTANCE) {
    override fun toString(): String = "Brs:${super.toString()}"
}