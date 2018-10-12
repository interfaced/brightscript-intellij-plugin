package com.interfaced.brs.lang.lexer

import com.intellij.lexer.FlexAdapter
import com.intellij.lexer.MergingLexerAdapter
import com.intellij.psi.tree.TokenSet

class BSLexer : MergingLexerAdapter(FlexAdapter(_BSLexer()), TokenSet.EMPTY) {
}