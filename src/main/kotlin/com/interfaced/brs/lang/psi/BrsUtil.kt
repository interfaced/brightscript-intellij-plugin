package com.interfaced.brs.lang.psi

import com.intellij.openapi.project.Project
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiFile
import com.intellij.psi.PsiManager
import com.intellij.psi.search.FileTypeIndex
import com.intellij.psi.search.GlobalSearchScope
import com.intellij.util.indexing.FileBasedIndex
import com.interfaced.brs.lang.BrsFileType
import com.intellij.psi.util.PsiTreeUtil
import com.intellij.psi.util.parentOfType

class BrsUtil {
    companion object {
        private inline fun <reified T : PsiElement> getChildrenFromFiles(files: List<PsiFile>): List<Array<T>> {
            return files.mapNotNull { PsiTreeUtil.getChildrenOfType(it, T::class.java) }
        }

        fun isPropertyIdentifier(element: PsiElement): Boolean {
            val prevSibling = element.prevSibling

            if (prevSibling != null) return prevSibling.node.elementType == BrsTypes.T_DOT
            return false
        }

        fun getFunctionDeclarations(project: Project, key: String): List<PsiElement> {
            val virtualFiles = FileBasedIndex.getInstance()
                    .getContainingFiles(FileTypeIndex.NAME, BrsFileType.INSTANCE, GlobalSearchScope.allScope(project))

            val files = virtualFiles.mapNotNull { PsiManager.getInstance(project).findFile(it) }
            val functionIdentifiers = getChildrenFromFiles<BrsFunctionStmt>(files)
                    .flatMap { it.map { fn -> fn.fnDecl } }
                    .filter { it.tIdentifier.text.equals(key, true) }
            val subIdentifiers = getChildrenFromFiles<BrsSubStmt>(files)
                    .flatMap { it.map { fn -> fn.subDecl } }
                    .filter { it.tIdentifier.text.equals(key, true) }

            return listOf(functionIdentifiers, subIdentifiers).flatten()
        }

        fun getFunctionDeclarations(file: PsiFile, key: String): List<PsiElement> {
            val functionIdentifiers = PsiTreeUtil.getChildrenOfType(file, BrsFunctionStmt::class.java)
                    ?.mapNotNull { it.fnDecl }
                    ?.filter { it.tIdentifier.text.equals(key, true) }
            val subIdentifiers = PsiTreeUtil.getChildrenOfType(file, BrsSubStmt::class.java)
                    ?.mapNotNull { it.subDecl }
                    ?.filter { it.tIdentifier.text.equals(key, true) }

            return listOf(functionIdentifiers, subIdentifiers).filterNotNull().flatten()
        }

        fun getOwnedFunction(element: PsiElement): BrsFunctionStmt? {
            return element.parentOfType()
        }

        fun getOwnedSub(element: PsiElement): BrsSubStmt? {
            return element.parentOfType()
        }
    }
}
