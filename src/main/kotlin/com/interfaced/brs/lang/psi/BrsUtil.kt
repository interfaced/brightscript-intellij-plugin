package com.interfaced.brs.lang.psi

import com.intellij.openapi.project.Project
import com.intellij.psi.PsiFile
import com.intellij.psi.PsiManager
import com.intellij.psi.search.FileTypeIndex
import com.intellij.psi.search.GlobalSearchScope
import com.interfaced.brs.lang.BrsFileType

class BrsUtil {
    companion object {
        fun getProjectFiles(project: Project): List<PsiFile> {
            val virtualFiles = FileTypeIndex.getFiles(BrsFileType.INSTANCE, GlobalSearchScope.allScope(project))
            return virtualFiles.mapNotNull { PsiManager.getInstance(project).findFile(it) }
        }
    }
}
