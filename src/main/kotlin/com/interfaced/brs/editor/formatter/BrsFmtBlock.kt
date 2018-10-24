package com.interfaced.brs.editor.formatter

import com.intellij.formatting.*
import com.intellij.lang.ASTNode
import com.intellij.openapi.util.TextRange
import com.intellij.psi.TokenType.WHITE_SPACE
import com.intellij.psi.formatter.FormatterUtil
import com.interfaced.brs.lang.psi.BrsTypes.*
import com.interfaced.brs.lang.psi.impl.*

class BrsFmtBlock(private val node: ASTNode,
                  private val alignment: Alignment?,
                  private val indent: Indent?,
                  private val wrap: Wrap?) : ASTBlock {
    override fun getAlignment(): Alignment? = alignment

    override fun isIncomplete(): Boolean = myIsIncomplete
    private val myIsIncomplete: Boolean by lazy { FormatterUtil.isIncomplete(node) }

    override fun getNode(): ASTNode? = node

    override fun getTextRange(): TextRange = node.textRange

    override fun getSubBlocks(): List<Block> = mySubBlocks
    private val mySubBlocks: List<Block> by lazy { buildChildren() }

    override fun getChildAttributes(newChildIndex: Int): ChildAttributes {
        return ChildAttributes(Indent.getNoneIndent(), null)
    }

    override fun getWrap(): Wrap? = wrap

    override fun isLeaf(): Boolean = node.firstChildNode == null

    override fun getSpacing(p0: Block?, p1: Block): Spacing? = null

    override fun getIndent(): Indent? = indent

    private fun buildChildren(): MutableList<Block> {
        val blocks = ArrayList<Block>()
        var child = node.firstChildNode
        while (child != null) {
            val type = child.elementType
            if (type != WHITE_SPACE && type != T_LINE_TERMINATOR) {
                blocks.add(BrsFmtBlock(child, Alignment.createAlignment(), computeIndent(child, node), null))
            }

            child = child.treeNext
        }

        return blocks
    }

    override fun toString() = "${node.text} $textRange"

    companion object {
        private fun doIndentIf(check: Boolean): Indent {
            return if (check) {
                Indent.getNormalIndent()
            } else {
                Indent.getNoneIndent()
            }
        }

        fun computeIndent(child: ASTNode, node: ASTNode): Indent? {
            val parentPsi = node.psi
            val childType = child.elementType
            val childPsi = child.psi

            return when (parentPsi) {
                is BrsFunctionStmtImpl -> {
                    doIndentIf(childPsi !is BrsEndFunctionImpl
                            && childPsi !is BrsFnDeclImpl)
                }
                is BrsSubStmtImpl -> {
                    doIndentIf(childPsi !is BrsEndSubImpl
                            && childPsi !is BrsSubDeclImpl)
                }
                is BrsIfStmtImpl -> {
                    doIndentIf(childPsi !is BrsEndIfImpl
                            && childPsi !is BrsIfInitImpl
                            && childPsi !is BrsElseIfStmtImpl
                            && childPsi !is BrsElseStmtImpl)
                }
                is BrsElseIfStmtImpl -> {
                    doIndentIf(childPsi !is BrsElseIfInitImpl)
                }
                is BrsElseStmtImpl -> {
                    doIndentIf(childType != T_ELSE)
                }
                is BrsForStmtImpl -> {
                    doIndentIf(childPsi !is BrsEndForImpl
                            && childPsi !is BrsForInitImpl
                            && childType != T_NEXT)
                }
                is BrsWhileStmtImpl -> {
                    doIndentIf(childPsi !is BrsEndWhileImpl
                            && childPsi !is BrsWhileInitImpl)
                }
                is BrsAnonFunctionStmtExprImpl -> {
                    doIndentIf(childPsi !is BrsEndFunctionImpl
                            && childPsi !is BrsAnonFunctionDeclImpl)
                }
                is BrsObjectLiteralImpl -> {
                    doIndentIf(childType != T_LBRACE
                            && childType != T_RBRACE)
                }
                is BrsArrayImpl -> {
                    doIndentIf(childType != T_LBRACK
                            && childType != T_RBRACK)
                }
                else -> Indent.getNoneIndent()
            }
        }
    }
}