package com.interfaced.brs.lang

import com.intellij.psi.ElementDescriptionLocation
import com.intellij.psi.ElementDescriptionProvider
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiNamedElement
import com.intellij.usageView.UsageViewShortNameLocation
import com.intellij.usageView.UsageViewTypeLocation
import com.interfaced.brs.lang.psi.BrsFnDecl
import com.interfaced.brs.lang.psi.BrsIdentifier
import com.interfaced.brs.lang.psi.BrsParameter
import com.interfaced.brs.lang.psi.BrsSubDecl

class BrsDescriptionProvider : ElementDescriptionProvider {
    override fun getElementDescription(element: PsiElement, location: ElementDescriptionLocation): String? {
        return when (location) {
            UsageViewShortNameLocation.INSTANCE -> getNameDescription(element)
            UsageViewTypeLocation.INSTANCE -> getTypeDescription(element)
            else -> null
        }
    }

    fun getNameDescription(element: PsiElement): String?  {
        return when (element) {
            is PsiNamedElement -> element.name
            else -> null
        }
    }

    fun getTypeDescription(element: PsiElement): String? {
        return when (element) {
            is BrsFnDecl -> "function"
            is BrsSubDecl -> "sub"
            is BrsParameter -> "parameter"
            is BrsIdentifier -> "variable"
            else -> null
        }
    }
}