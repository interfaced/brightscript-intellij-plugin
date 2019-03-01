package com.interfaced.brs.lang.psi

import com.intellij.openapi.util.TextRange
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiPolyVariantReference
import com.intellij.psi.*
import com.interfaced.brs.lang.psi.ext.BrsElement

class BrsReference(element: BrsElement, textRange: TextRange) : PsiReferenceBase<PsiElement>(element, textRange), PsiPolyVariantReference {
    private val key = element.text

    fun resolveFirst(): PsiElement? {
        return multiResolve(false).firstOrNull()?.element
    }

    override fun resolve(): PsiElement? {
        val resolveResults = multiResolve(false)

        if (resolveResults.size == 1) {
            return resolveResults.first().element
        }

        return null
    }

    override fun multiResolve(incompleteCode: Boolean): Array<ResolveResult> {
        if (BrsUtil.isPropertyIdentifier(element)) return emptyArray()

        // Function/Sub parameter
        val fnParameter = BrsUtil.getOwnedFunction(element)
                ?.fnDecl?.parameterList?.parameterList?.find { it.identifier.tIdentifier.text.equals(key, true) }
        val subParameter = BrsUtil.getOwnedSub(element)
                ?.subDecl?.parameterList?.parameterList?.find { it.identifier.tIdentifier.text.equals(key, true) }

        if (fnParameter != null) return arrayOf(PsiElementResolveResult(fnParameter))
        if (subParameter != null) return arrayOf(PsiElementResolveResult(subParameter))

        // Search declarations in containing file first
        val declarationsInFile = BrsUtil.getFunctionDeclarations(element.containingFile, key)
        if (declarationsInFile.isNotEmpty()) {
            return declarationsInFile.map { PsiElementResolveResult(it) }.toTypedArray()
        }

        // Search in project files (global)
        val project = element.project
        return BrsUtil.getFunctionDeclarations(project, key).map { PsiElementResolveResult(it) }.toTypedArray()
    }
}