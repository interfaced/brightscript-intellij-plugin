package com.interfaced.brs.lang.psi

import com.intellij.openapi.util.TextRange
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiPolyVariantReference
import com.intellij.psi.*
import com.interfaced.brs.lang.psi.ext.BrsElement

class BrsReference(element: BrsElement, textRange: TextRange) : PsiReferenceBase<PsiElement>(element, textRange), PsiPolyVariantReference {
    private val key = element.text

    override fun resolve(): PsiElement? {
        val resolveResults = multiResolve(false)

        if (resolveResults.size == 1) {
            return resolveResults.first().element
        }

        return null
    }

    override fun multiResolve(incompleteCode: Boolean): Array<ResolveResult> {
        val project = element.project

        return BrsUtil.getFunctionDeclarations(project, key)
                .map { ident -> PsiElementResolveResult(ident) }
                .toTypedArray()
    }
}