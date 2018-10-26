package com.interfaced.brs.editor.formatter

import com.intellij.formatting.*
import com.intellij.psi.PsiElement
import com.intellij.psi.codeStyle.CodeStyleSettings

class BrsFormattingModelBuilder : FormattingModelBuilder {
    override fun createModel(element: PsiElement, settings: CodeStyleSettings): FormattingModel {
        return FormattingModelProvider
                .createFormattingModelForPsiFile(
                        element.containingFile,
                        BrsFmtBlock(element.node, null, Indent.getNoneIndent(), null),
                        settings
                )
    }
}