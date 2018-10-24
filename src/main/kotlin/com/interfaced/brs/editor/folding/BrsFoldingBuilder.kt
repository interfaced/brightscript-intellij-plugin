package com.interfaced.brs.editor.folding

import com.intellij.lang.ASTNode
import com.intellij.lang.folding.FoldingBuilderEx
import com.intellij.lang.folding.FoldingDescriptor
import com.intellij.openapi.editor.Document
import com.intellij.openapi.util.TextRange
import com.intellij.psi.PsiElement
import com.intellij.psi.util.PsiTreeUtil
import com.interfaced.brs.lang.psi.*
import com.interfaced.brs.lang.psi.impl.BrsArrayImpl
import com.interfaced.brs.lang.psi.impl.BrsObjectLiteralImpl

class BrsFoldingBuilder : FoldingBuilderEx() {
    override fun getPlaceholderText(node: ASTNode): String? {
        val psi = node.psi
        return when (psi) {
            is BrsObjectLiteralImpl -> "{...}"
            is BrsArrayImpl -> "[...]"
            else -> "..."
        }
    }

    override fun buildFoldRegions(root: PsiElement, doc: Document, quick: Boolean): Array<FoldingDescriptor> {
        if (root !is BrsFile) return emptyArray()

        val descriptors: MutableList<FoldingDescriptor> = ArrayList()
        val visitor = FoldingVisitor(descriptors)
        PsiTreeUtil.processElements(root) { it.accept(visitor); true }

        return descriptors.toTypedArray()
    }

    override fun isCollapsedByDefault(node: ASTNode): Boolean = false

    private class FoldingVisitor(
            private val descriptors: MutableList<FoldingDescriptor>) : BrsVisitor() {

        override fun visitFunctionStmt(o: BrsFunctionStmt) {
            val startOffset = o.fnDecl.textRange.endOffset
            val endOffset = o.endFunction.textRange.startOffset

            fold(o, TextRange(startOffset, endOffset))
        }

        override fun visitSubStmt(o: BrsSubStmt) {
            val startOffset = o.subDecl.textRange.endOffset
            val endOffset = o.endSub.textRange.startOffset

            fold(o, TextRange(startOffset, endOffset))
        }

        override fun visitIfStmt(o: BrsIfStmt) {
            val startOffset = o.ifInit.textRange.endOffset
            val endOffset = o.endIf?.textRange?.startOffset ?: o.textRange.endOffset

            fold(o, TextRange(startOffset, endOffset))
        }

        override fun visitForStmt(o: BrsForStmt) {
            val startOffset = o.forInit.textRange.endOffset
            val endOffset = o.endFor?.textRange?.startOffset ?: o.textRange.endOffset

            fold(o, TextRange(startOffset, endOffset))
        }

        override fun visitWhileStmt(o: BrsWhileStmt) {
            val startOffset = o.whileInit.textRange.endOffset
            val endOffset = o.endWhile.textRange.startOffset

            fold(o, TextRange(startOffset, endOffset))
        }

        override fun visitAnonFunctionStmtExpr(o: BrsAnonFunctionStmtExpr) {
            val startOffset = o.anonFunctionDecl.textRange.endOffset
            val endOffset = o.endFunction.textRange.startOffset

            fold(o, TextRange(startOffset, endOffset))
        }

        override fun visitObjectLiteral(o: BrsObjectLiteral) {
            val keys = o.propertyIdentifierList

            if (keys.isNotEmpty()) {
                fold(o, o.textRange)
            }
        }

        override fun visitArray(o: BrsArray) {
            val items = o.exprList

            if (items.isNotEmpty()) {
                fold(o, o.textRange)
            }
        }

        private fun fold(element: PsiElement, range: TextRange) {
            descriptors += FoldingDescriptor(element.node, range)
        }
    }
}