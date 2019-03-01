package com.interfaced.brs.lang.psi.ext

import com.intellij.lang.ASTNode
import com.interfaced.brs.lang.psi.BrsIdentifier
import com.interfaced.brs.lang.psi.BrsReference

abstract class BrsIdentifierImplMixin(node: ASTNode) : BrsElementImpl(node), BrsIdentifier {
    override fun getReference(): BrsReference {
        return BrsReference(this, this.tIdentifier.textRangeInParent)
    }
}