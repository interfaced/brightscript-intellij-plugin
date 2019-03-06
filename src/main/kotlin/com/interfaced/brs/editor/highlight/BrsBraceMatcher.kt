package com.interfaced.brs.editor.highlight

import com.intellij.lang.BracePair
import com.intellij.lang.PairedBraceMatcher
import com.intellij.psi.PsiFile
import com.intellij.psi.tree.IElementType
import com.interfaced.brs.lang.psi.BrsTypes.*

class BrsBraceMatcher : PairedBraceMatcher {
    override fun getCodeConstructStart(file: PsiFile?, openingBraceOffset: Int): Int = openingBraceOffset

    override fun getPairs(): Array<BracePair> = PAIRS

    override fun isPairedBracesAllowedBeforeType(lbraceType: IElementType, contextType: IElementType?): Boolean = true

    companion object {
        val PAIRS: Array<BracePair> = arrayOf(
                BracePair(T_LBRACE, T_RBRACE, true),
                BracePair(T_LBRACK, T_RBRACK, true),
                BracePair(T_LPAREN, T_RPAREN, true)
        )
    }
}