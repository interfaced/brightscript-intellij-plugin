package com.interfaced.brs.lang.psi.ext

import com.intellij.psi.PsiElement
import com.interfaced.brs.lang.psi.BrsReference

interface BrsReferenceElement : PsiElement {
    override fun getReference(): BrsReference
}