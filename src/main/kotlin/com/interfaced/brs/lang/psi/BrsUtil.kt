package com.interfaced.brs.lang.psi

import com.intellij.openapi.project.Project
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiManager
import com.intellij.psi.search.FileTypeIndex
import com.intellij.psi.search.GlobalSearchScope
import com.intellij.util.indexing.FileBasedIndex
import com.interfaced.brs.lang.BrsFileType
import com.intellij.psi.util.PsiTreeUtil
import com.interfaced.brs.lang.psi.impl.BrsFunctionStmtImpl
import com.interfaced.brs.lang.psi.impl.BrsSubStmtImpl

class BrsUtil {
    companion object {
        fun getFunctionDeclarations(project: Project, key: String): List<PsiElement> {
            val virtualFiles = FileBasedIndex.getInstance()
                    .getContainingFiles(FileTypeIndex.NAME, BrsFileType.INSTANCE, GlobalSearchScope.allScope(project))

            val files = virtualFiles.mapNotNull { PsiManager.getInstance(project).findFile(it) }
            val functionIdentifiers = files
                    .mapNotNull { PsiTreeUtil.getChildrenOfType(it, BrsFunctionStmtImpl::class.java) }
                    .flatMap { it.map { fn -> fn.fnDecl } }
                    .filter { it.tIdentifier.text == key }
            val subIdentifiers = files
                    .mapNotNull { PsiTreeUtil.getChildrenOfType(it, BrsSubStmtImpl::class.java) }
                    .flatMap { it.map { fn -> fn.subDecl } }
                    .filter { it.tIdentifier.text == key }

            return listOf(functionIdentifiers, subIdentifiers).flatten()
        }
    }
}