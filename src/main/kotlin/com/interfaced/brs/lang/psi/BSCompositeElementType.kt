package com.interfaced.brs.lang.psi

import com.intellij.psi.tree.IElementType
import com.interfaced.brs.lang.BSLanguage

class BSCompositeElementType(expr: String) : IElementType(expr, BSLanguage.INSTANCE)