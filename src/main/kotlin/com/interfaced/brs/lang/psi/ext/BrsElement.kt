package com.interfaced.brs.lang.psi.ext

import com.intellij.extapi.psi.ASTWrapperPsiElement
import com.intellij.lang.ASTNode
import com.intellij.psi.PsiElement

interface BrsElement : PsiElement

abstract class BrsElementImpl(node: ASTNode) : ASTWrapperPsiElement(node), BrsElement