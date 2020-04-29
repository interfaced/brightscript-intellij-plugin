package com.interfaced.brs.lang.psi

import com.intellij.openapi.util.TextRange
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiPolyVariantReference
import com.intellij.psi.*
import com.intellij.psi.util.PsiTreeUtil
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

    private fun getScopeEntries(scope: PsiElement): List<PsiElement> = PsiTreeUtil.getChildrenOfAnyType(scope,
            BrsAssignStmt::class.java,
            BrsAnonFunctionDecl::class.java,
            BrsForInit::class.java,
            BrsFunctionStmt::class.java,
            BrsFnDecl::class.java,
            BrsDimStmt::class.java,
            BrsSubStmt::class.java,
            BrsSubDecl::class.java
            )

    private fun resolveInLocalScope(scope: PsiElement): PsiElement? {
        return resolveInScope(scope)
                .filter {
                    it.textOffset < element.textOffset
                }
                .maxBy { it.textOffset }
    }

    private fun resolveInScope(scope: PsiElement): List<PsiElement> {
        return getScopeEntries(scope).mapNotNull { entry ->
            when (entry) {
                is BrsAssignStmt -> {
                    val identifier = PsiTreeUtil.findChildOfType(entry.firstChild, BrsRefExpr::class.java, false)?.identifier
                    val eq = identifier?.tIdentifier?.text?.equals(key, true) == true
                    if (eq) identifier else null
                }
                is BrsAnonFunctionDecl, is BrsFnDecl, is BrsSubDecl -> {
                    val parameterList = when (entry) {
                        is BrsAnonFunctionDecl -> entry.parameterList.parameterList
                        is BrsFnDecl -> entry.parameterList.parameterList
                        is BrsSubDecl -> entry.parameterList.parameterList
                        else -> null
                    }

                    parameterList?.find { it.identifier.tIdentifier.text.equals(key, true) }
                }
                is BrsForInit -> {
                    val identifier = entry.identifier
                    val eq = identifier.tIdentifier.text?.equals(key, true) == true
                    if (eq) identifier else null
                }
                is BrsFunctionStmt, is BrsSubStmt -> {
                    val decl = when (entry) {
                        is BrsFunctionStmt -> entry.fnDecl
                        is BrsSubStmt -> entry.subDecl
                        else -> null
                    }
                    if (decl?.name?.equals(key, true) == true) decl else null
                }
                is BrsDimStmt -> {
                    val identifier = entry.identifier
                    if (identifier.tIdentifier.text.equals(key, true)) identifier else null
                }
                else -> null
            }
        }
    }

    override fun multiResolve(incompleteCode: Boolean): Array<ResolveResult> {
        // pairs of parents and target elements
        val candidates = mutableListOf<PsiElement>()
        val file = element.containingFile

        PsiTreeUtil.treeWalkUp(element, file) { current, _ ->
            when (current) {
                is PsiFile, is BrsForStmt, is BrsFunctionStmt, is BrsAnonFunctionStmtExpr, is BrsSubStmt -> {
                    val scopeCandidate = resolveInLocalScope(current)
                    if (scopeCandidate != null) {
                        candidates.add(scopeCandidate)
                        false
                    } else true
                }
                else -> true
            }
        }

        if (candidates.isNotEmpty()) {
            return candidates.map { PsiElementResolveResult(it) }.toTypedArray()
        }

        // Search in project files (global)
        val project = element.project
        return BrsUtil.getProjectFiles(project)
                .flatMap { resolveInScope(it) }
                .map { PsiElementResolveResult(it) }.toTypedArray()
    }
}