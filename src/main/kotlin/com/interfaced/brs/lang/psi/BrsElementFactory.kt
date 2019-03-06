package com.interfaced.brs.lang.psi

import com.intellij.openapi.project.Project
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiFileFactory
import com.intellij.psi.util.PsiTreeUtil
import com.interfaced.brs.lang.BrsFileType

class BrsElementFactory {
    companion object {
        fun createIdentifier(project: Project, name: String): PsiElement {
            val goto = createFromText<BrsGotoStmt>(project, "goto $name")

            return goto?.tIdentifier!!
        }

        private fun createFile(project: Project, text: CharSequence): BrsFile =
                PsiFileFactory.getInstance(project)
                        .createFileFromText("dummy.brs", BrsFileType.INSTANCE, text) as BrsFile

        private inline fun <reified T : PsiElement>createFromText(project: Project, text: String): T? =
                PsiTreeUtil.findChildOfType(createFile(project, text), T::class.java, /* strict */ true)
    }
}