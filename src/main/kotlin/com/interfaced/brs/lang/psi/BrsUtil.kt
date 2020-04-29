package com.interfaced.brs.lang.psi

import com.intellij.openapi.project.Project
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiFile
import com.intellij.psi.PsiManager
import com.intellij.psi.search.FileTypeIndex
import com.intellij.psi.search.GlobalSearchScope
import com.interfaced.brs.lang.BrsFileType
import com.intellij.psi.util.PsiTreeUtil

class BrsUtil {
    companion object {
        private inline fun <reified T : PsiElement> getChildrenFromFiles(files: List<PsiFile>): List<Array<T>> {
            return files.mapNotNull { PsiTreeUtil.getChildrenOfType(it, T::class.java) }
        }

        private inline fun <reified T : PsiElement> PsiElement.ancestorOfType(): T? =
                PsiTreeUtil.getParentOfType(this, T::class.java, /* strict */ false)

        private inline fun <reified T : PsiElement> PsiElement.getAllAncestorsOfType(): List<T> =
                PsiTreeUtil.collectParents(this, T::class.java, false) { it is PsiFile }

        fun isPropertyIdentifier(element: PsiElement): Boolean {
            val prevSibling = element.prevSibling

            if (prevSibling != null) return prevSibling.node.elementType == BrsTypes.T_DOT
            return false
        }

        fun getFunctionDeclarations(project: Project, key: String): List<PsiElement> {
            val virtualFiles = FileTypeIndex.getFiles(BrsFileType.INSTANCE, GlobalSearchScope.allScope(project))

            val files = virtualFiles.mapNotNull { PsiManager.getInstance(project).findFile(it) }
            val anonFunctionIdentifiers = getChildrenFromFiles<BrsAssignStmt>(files)
                    .flatMap { it.filter { stmt -> stmt.firstChild is BrsRefExpr && stmt.lastChild is BrsAnonFunctionStmtExpr} }
                    .map { it.firstChild as BrsRefExpr }
                    .filter { it.text.equals(key, true) }
                    .map { it.identifier }
            val functionIdentifiers = getChildrenFromFiles<BrsFunctionStmt>(files)
                    .flatMap { it.map { fn -> fn.fnDecl } }
                    .filter { it.tIdentifier.text.equals(key, true) }
            val subIdentifiers = getChildrenFromFiles<BrsSubStmt>(files)
                    .flatMap { it.map { fn -> fn.subDecl } }
                    .filter { it.tIdentifier.text.equals(key, true) }

            return listOf(functionIdentifiers, subIdentifiers, anonFunctionIdentifiers).flatten()
        }

        fun getFunctionDeclarations(file: PsiFile, key: String): List<PsiElement> {
            val anonFunctionIdentifiers = PsiTreeUtil.getChildrenOfType(file, BrsAssignStmt::class.java)
                    ?.filter { it.firstChild is BrsRefExpr && it.lastChild is BrsAnonFunctionStmtExpr }
                    ?.map { it.firstChild as BrsRefExpr }
                    ?.filter { it.text.equals(key, true) }
                    ?.map { it.identifier }
            val functionIdentifiers = PsiTreeUtil.getChildrenOfType(file, BrsFunctionStmt::class.java)
                    ?.mapNotNull { it.fnDecl }
                    ?.filter { it.tIdentifier.text.equals(key, true) }
            val subIdentifiers = PsiTreeUtil.getChildrenOfType(file, BrsSubStmt::class.java)
                    ?.mapNotNull { it.subDecl }
                    ?.filter { it.tIdentifier.text.equals(key, true) }

            return listOfNotNull(functionIdentifiers, subIdentifiers, anonFunctionIdentifiers).flatten()
        }

        fun getAllOwnedFunctions(element: PsiElement): List<BrsFunctionStmt> = element.getAllAncestorsOfType()
        fun getOwnedFunction(element: PsiElement): BrsFunctionStmt? = element.ancestorOfType()

        fun getAllOwnedForBlocks(element: PsiElement): List<BrsForStmt> = element.getAllAncestorsOfType()
        fun getOwnedForBlock(element: PsiElement): BrsForStmt? = element.ancestorOfType()

        fun getAllOwnedAnonymousFunctions(element: PsiElement): List<BrsAnonFunctionStmtExpr> = element.getAllAncestorsOfType()
        fun getOwnedAnonymousFunction(element: PsiElement): BrsAnonFunctionStmtExpr? = element.ancestorOfType()

        fun getAllOwnedSubs(element: PsiElement): List<BrsSubStmt> = element.getAllAncestorsOfType()
        fun getOwnedSub(element: PsiElement): BrsSubStmt? = element.ancestorOfType()
    }
}
