package com.interfaced.brs.lang.completion

import com.intellij.codeInsight.completion.*
import com.intellij.codeInsight.lookup.LookupElementBuilder
import com.intellij.patterns.PlatformPatterns.psiElement
import com.intellij.patterns.StandardPatterns
import com.intellij.util.ProcessingContext
import com.interfaced.brs.lang.psi.BrsTypes.T_AS
import com.interfaced.brs.lang.psi.BrsTypes.T_LINE_TERMINATOR

class BrsCompletionContributor : CompletionContributor() {
    private object KeywordCompletionProvider : CompletionProvider<CompletionParameters>() {
        private val keywords = listOf(
                "And",
                "Dim",
                "Each",
                "Else",
                "ElseIf",
                "End",
                "EndFunction",
                "EndIf",
                "EndSub",
                "EndWhile",
                "Exit",
                "ExitWhile",
                "False",
                "For",
                "Function",
                "Goto",
                "If",
                "Next",
                "Not",
                "Or",
                "Print",
                "Rem",
                "Return",
                "Step",
                "Stop",
                "Sub",
                "Then",
                "To",
                "True",
                "While"
        )
        override fun addCompletions(parameters: CompletionParameters, context: ProcessingContext, resultSet: CompletionResultSet) {
            resultSet.addAllElements(listOf(keywords, keywords.map { it.toLowerCase() })
                    .flatMap { it.map { str -> LookupElementBuilder.create(str) } })
        }
    }

    private object TypesCompletionProvider : CompletionProvider<CompletionParameters>() {
        private val keywords = listOf(
                "Void",
                "Boolean",
                "Integer",
                "LongInteger",
                "Float",
                "Double",
                "String",
                "Object",
                "Function",
                "Interface",
                "Invalid"
        )
        override fun addCompletions(parameters: CompletionParameters, context: ProcessingContext, resultSet: CompletionResultSet) {
            resultSet.addAllElements(listOf(keywords, keywords.map { it.toLowerCase() })
                    .flatMap { it.map { str -> LookupElementBuilder.create(str) } })
        }
    }

    init {
        extend(CompletionType.BASIC, StandardPatterns.or(psiElement().afterLeaf(psiElement(T_LINE_TERMINATOR)), psiElement().isNull), KeywordCompletionProvider)
        extend(CompletionType.BASIC, psiElement().afterLeaf(psiElement(T_AS)), TypesCompletionProvider)
    }
}