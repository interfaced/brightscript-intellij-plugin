package com.interfaced.brs.editor.highlight

import com.intellij.codeInsight.daemon.impl.HighlightInfo
import com.intellij.codeInsight.daemon.impl.HighlightInfoType
import com.intellij.codeInsight.daemon.impl.HighlightVisitor
import com.intellij.codeInsight.daemon.impl.analysis.HighlightInfoHolder
import com.intellij.openapi.editor.colors.TextAttributesKey
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiFile
import com.interfaced.brs.lang.psi.*
import com.interfaced.brs.lang.psi.BrsTypes.T_IDENTIFIER

class BrsHighlighterVisitor : BrsVisitor(), HighlightVisitor {
    private var infoHolder: HighlightInfoHolder? = null

    override fun visitFunctionStmt(element: BrsFunctionStmt) {
        highlightFunctionIdentifier(element)
        super.visitFunctionStmt(element)
    }

    override fun visitSubStmt(element: BrsSubStmt) {
        highlightFunctionIdentifier(element)
        super.visitSubStmt(element)
    }

    override fun visitPropertyIdentifier(element: BrsPropertyIdentifier) {
        highlight(element, BrsHighlighter.PROPERTY)
        super.visitPropertyIdentifier(element)
    }

    private fun highlightFunctionIdentifier(element: PsiElement) {
        val identifier = when (element) {
            is BrsSubStmt -> element.subDecl.node.findChildByType(T_IDENTIFIER)?.psi
            is BrsFunctionStmt -> element.fnDecl.node.findChildByType(T_IDENTIFIER)?.psi
            else -> null
        }

        if (identifier != null) {
            highlight(identifier, BrsHighlighter.DECLARATION)
        }
    }

    private fun highlight(element: PsiElement, attrKey: TextAttributesKey) {
        val builder = HighlightInfo.newHighlightInfo(HighlightInfoType.INFORMATION)
        builder.textAttributes(attrKey)
        builder.range(element)

        infoHolder?.add(builder.create())
    }

    override fun analyze(file: PsiFile, updateWholeFile: Boolean, holder: HighlightInfoHolder, action: Runnable): Boolean {
        infoHolder = holder
        action.run()

        return true
    }

    override fun clone(): HighlightVisitor = BrsHighlighterVisitor()

    override fun suitableForFile(file: PsiFile): Boolean = file is BrsFile

    override fun order(): Int = 0

    override fun visit(element: PsiElement) = element.accept(this)
}