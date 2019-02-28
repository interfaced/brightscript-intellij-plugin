package com.interfaced.brs.lang.psi.ext

import com.intellij.lang.ASTNode
import com.intellij.psi.*
import com.interfaced.brs.lang.psi.BrsElementFactory
import com.interfaced.brs.lang.psi.BrsTypes.T_IDENTIFIER

interface BrsNamedElement : BrsElement, PsiNamedElement, NavigatablePsiElement

interface BrsNameIdentifierOwner : BrsNamedElement, PsiNameIdentifierOwner

abstract class BrsNamedElementImpl(node: ASTNode) : BrsElementImpl(node), BrsNameIdentifierOwner {
    override fun getNameIdentifier(): PsiElement? = findChildByType(T_IDENTIFIER)

    override fun getName(): String? = nameIdentifier?.text

    override fun setName(name: String): PsiElement? {
        nameIdentifier?.replace(BrsElementFactory.createIdentifier(project, name))
        return this
    }

    override fun getTextOffset(): Int = nameIdentifier?.textOffset ?: super.getTextOffset()
}