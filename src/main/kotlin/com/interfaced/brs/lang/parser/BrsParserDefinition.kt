package com.interfaced.brs.lang.parser

import com.intellij.lang.ASTNode
import com.intellij.lang.ParserDefinition
import com.intellij.lang.PsiParser
import com.intellij.lexer.Lexer
import com.intellij.openapi.project.Project
import com.intellij.psi.FileViewProvider
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiFile
import com.intellij.psi.TokenType
import com.intellij.psi.tree.IFileElementType
import com.intellij.psi.tree.TokenSet
import com.interfaced.brs.file.BrsFileElementType
import com.interfaced.brs.lang.lexer.BrsLexer
import com.interfaced.brs.lang.psi.BrsFile

import com.interfaced.brs.lang.psi.BrsTypes.Factory

class BrsParserDefinition : ParserDefinition {
    private val tsWHITESPACES = TokenSet.create(TokenType.WHITE_SPACE)

    override fun createParser(project: Project): PsiParser = BrsParser()

    override fun createFile(viewProvider: FileViewProvider): PsiFile = BrsFile(viewProvider)

    override fun getStringLiteralElements(): TokenSet = TokenSet.EMPTY

    override fun getFileNodeType(): IFileElementType = BrsFileElementType.INSTANCE

    override fun getWhitespaceTokens(): TokenSet = tsWHITESPACES

    override fun createLexer(project: Project): Lexer = BrsLexer()

    override fun createElement(node: ASTNode): PsiElement = Factory.createElement(node)

    override fun getCommentTokens(): TokenSet = TokenSet.EMPTY
}