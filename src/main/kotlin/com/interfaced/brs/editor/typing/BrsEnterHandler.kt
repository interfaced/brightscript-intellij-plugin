package com.interfaced.brs.editor.typing

import com.intellij.codeInsight.editorActions.enter.EnterHandlerDelegate
import com.intellij.codeInsight.editorActions.enter.EnterHandlerDelegateAdapter
import com.intellij.openapi.actionSystem.DataContext
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.editor.actionSystem.EditorActionHandler
import com.intellij.openapi.util.Ref
import com.intellij.openapi.util.text.StringUtil
import com.intellij.psi.PsiDocumentManager
import com.intellij.psi.PsiFile
import com.intellij.psi.codeStyle.CodeStyleManager
import com.intellij.util.IncorrectOperationException
import com.interfaced.brs.lang.BrsLanguage
import com.interfaced.brs.lang.psi.BrsTypes


class BrsEnterHandler : EnterHandlerDelegateAdapter() {
    override fun preprocessEnter(file: PsiFile, editor: Editor, caretOffsetRef: Ref<Int>, caretAdvanceRef: Ref<Int>, dataContext: DataContext, originalHandler: EditorActionHandler?): EnterHandlerDelegate.Result {
        if (file.language != BrsLanguage.INSTANCE) return EnterHandlerDelegate.Result.Continue
        val offset = caretOffsetRef.get()
        val element = file.findElementAt(offset) ?: return EnterHandlerDelegate.Result.Continue

        var prev = element.prevSibling ?: return EnterHandlerDelegate.Result.Continue

        while (prev.node.elementType == BrsTypes.T_LINE_TERMINATOR)
            prev = prev.prevSibling ?: return EnterHandlerDelegate.Result.Continue

        // Search stmt keyword on the same line
        val lineNumber = StringUtil.offsetToLineNumber(file.text, prev.textOffset)
        var currentLineNumber = lineNumber

        val stmtTypes = listOf(BrsTypes.T_FUNCTION, BrsTypes.T_IF, BrsTypes.T_SUB, BrsTypes.T_FOR, BrsTypes.T_WHILE)

        while (currentLineNumber == lineNumber && prev.node.elementType !in stmtTypes) {
            prev = prev.prevSibling ?: return EnterHandlerDelegate.Result.Continue
            currentLineNumber = StringUtil.offsetToLineNumber(file.text, prev.textOffset)
        }

        return when (prev.node.elementType) {
            BrsTypes.T_FUNCTION -> completeStatementAt(offset, "function", editor, file)
            BrsTypes.T_IF -> completeStatementAt(offset, "if", editor, file)
            BrsTypes.T_SUB -> completeStatementAt(offset, "sub", editor, file)
            BrsTypes.T_FOR -> completeStatementAt(offset, "for", editor, file)
            BrsTypes.T_WHILE -> completeStatementAt(offset, "while", editor, file)
            else -> EnterHandlerDelegate.Result.Continue
        }
    }

    private fun completeStatementAt(offset: Int, text: String, editor: Editor, file: PsiFile): EnterHandlerDelegate.Result {
        editor.document.insertString(offset, "\nend $text")
        PsiDocumentManager.getInstance(file.project).commitDocument(editor.document)

        try {
            CodeStyleManager.getInstance(file.project)!!.adjustLineIndent(file, editor.caretModel.offset + 1)
        } catch (e: IncorrectOperationException) {
            // TODO: process exception
        }

        return EnterHandlerDelegate.Result.DefaultForceIndent
    }
}