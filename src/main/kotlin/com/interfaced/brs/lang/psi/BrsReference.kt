package com.interfaced.brs.lang.psi

import com.intellij.openapi.util.TextRange
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiPolyVariantReference
import com.intellij.psi.*
import com.intellij.psi.util.PsiTreeUtil
import com.intellij.util.containers.addIfNotNull
import com.interfaced.brs.lang.psi.ext.BrsElement

class BrsReference(element: BrsElement, textRange: TextRange) : PsiReferenceBase<PsiElement>(element, textRange), PsiPolyVariantReference {
    private val key = element.text

    fun resolveFirst(): PsiElement? {
        return multiResolve(false).firstOrNull()?.element
    }

    fun exprText(): String {
        return if (element.parent is BrsRefExpr) {
            element.parent.text
        } else {
            key
        }
    }

    override fun resolve(): PsiElement? {
        val resolveResults = multiResolve(false)

        if (resolveResults.size == 1) {
            return resolveResults.first().element
        }

        return null
    }

    override fun multiResolve(incompleteCode: Boolean): Array<ResolveResult> {
        // pairs of parents and target elements
        val candidates = mutableListOf<List<PsiElement>>()
        val file = element.containingFile

        // For counter
        val ownedForBlock = BrsUtil.getAllOwnedForBlocks(element)
                .filter {
                    it.forInit.identifier.tIdentifier.text?.equals(exprText(), true) ?: false
                }.maxBy { PsiTreeUtil.getDepth(it, file) }
        if (ownedForBlock != null) {
            candidates.add(listOf(ownedForBlock, ownedForBlock.forInit.identifier))
        }

        // Function parameter
        val ownedFunction = BrsUtil.getAllOwnedFunctions(element)
                .filter { fn ->
                    val parameterList = fn.fnDecl.parameterList.parameterList
                    parameterList.find { it.identifier.tIdentifier.text.equals(exprText(), true) } != null
                }.maxBy { PsiTreeUtil.getDepth(it, file) }
        if (ownedFunction != null) {
            val parameterList = ownedFunction.fnDecl.parameterList.parameterList
            candidates.add(listOf(ownedFunction, parameterList.find { it.identifier.tIdentifier.text.equals(exprText(), true) }!!))
        }

        // AnonFunction parameter
        val ownedAnonFunction = BrsUtil.getAllOwnedAnonymousFunctions(element)
                .filter { fn ->
                    val parameterList = fn.anonFunctionDecl.parameterList.parameterList
                    parameterList.find { it.identifier.tIdentifier.text.equals(exprText(), true) } != null
                }.maxBy { PsiTreeUtil.getDepth(it, file) }
        if (ownedAnonFunction != null) {
            val parameterList = ownedAnonFunction.anonFunctionDecl.parameterList.parameterList
            candidates.add(listOf(ownedAnonFunction, parameterList.find { it.identifier.tIdentifier.text.equals(exprText(), true) }!!))
        }

        //Sub parameter
        val ownedSub = BrsUtil.getAllOwnedSubs(element)
                .filter { fn ->
                    val parameterList = fn.subDecl.parameterList.parameterList
                    parameterList.find { it.identifier.tIdentifier.text.equals(exprText(), true) } != null
                }.maxBy { PsiTreeUtil.getDepth(it, file) }
        if (ownedSub != null) {
            val parameterList = ownedSub.subDecl.parameterList.parameterList
            candidates.add(listOf(ownedSub, parameterList.find { it.identifier.tIdentifier.text.equals(exprText(), true) }!!))
        }

        if (candidates.isNotEmpty()) {
            return if (candidates.size == 1) {
                arrayOf(PsiElementResolveResult(candidates.first().last()))
            } else {
                val deepestCandidate = candidates.maxBy { PsiTreeUtil.getDepth(it.first(), file) }!!.last()
                arrayOf(PsiElementResolveResult(deepestCandidate))
            }
        }

        // Search declarations in containing file first
        val declarationsInFile = BrsUtil.getFunctionDeclarations(element.containingFile, exprText())
        if (declarationsInFile.isNotEmpty()) {
            return declarationsInFile.map { PsiElementResolveResult(it) }.toTypedArray()
        }

        // Search in project files (global)
        val project = element.project
        return BrsUtil.getFunctionDeclarations(project, exprText()).map { PsiElementResolveResult(it) }.toTypedArray()
    }
}