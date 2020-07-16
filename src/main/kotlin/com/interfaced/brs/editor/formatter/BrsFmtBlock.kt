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
        val indent = when (node.psi) {
            is BrsFunctionStmtImpl,
            is BrsSubStmtImpl,
            is BrsIfStmtImpl,
            is BrsElseIfStmtImpl,
            is BrsElseStmtImpl,
            is BrsForStmtImpl,
            is BrsWhileStmtImpl,
            is BrsAnonFunctionStmtExprImpl,
            is BrsAnonSubStmtExprImpl,
            is BrsObjectLiteralImpl,
            is BrsArrayImpl -> Indent.getNormalIndent()
            else -> Indent.getNoneIndent()
        }
        return ChildAttributes(indent, null)
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
                blocks.add(BrsFmtBlock(child, null, computeIndent(child, node), null))
            }

            child = child.treeNext
        }

        return blocks
    }

    override fun toString() = "${node.text} $textRange"

    companion object {
        private fun indentIf(check: Boolean): Indent {
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
                    indentIf(childPsi !is BrsEndFunctionImpl
                            && childPsi !is BrsFnDeclImpl)
                }
                is BrsSubStmtImpl -> {
                    indentIf(childPsi !is BrsEndSubImpl
                            && childPsi !is BrsSubDeclImpl)
                }
                is BrsIfStmtImpl -> {
                    indentIf(childPsi !is BrsEndIfImpl
                            && childPsi !is BrsIfInitImpl
                            && childPsi !is BrsElseIfStmtImpl
                            && childPsi !is BrsElseStmtImpl)
                }
                is BrsElseIfStmtImpl -> {
                    indentIf(childPsi !is BrsElseIfInitImpl)
                }
                is BrsElseStmtImpl -> {
                    indentIf(childType != T_ELSE)
                }
                is BrsForStmtImpl -> {
                    indentIf(childPsi !is BrsEndForImpl
                            && childPsi !is BrsForInitImpl
                            && childType != T_NEXT)
                }
                is BrsWhileStmtImpl -> {
                    indentIf(childPsi !is BrsEndWhileImpl
                            && childPsi !is BrsWhileInitImpl)
                }
                is BrsAnonFunctionStmtExprImpl -> {
                    indentIf(childPsi !is BrsEndFunctionImpl
                            && childPsi !is BrsAnonFunctionDeclImpl)
                }
                is BrsAnonSubStmtExprImpl -> {
                    indentIf(childPsi !is BrsEndSubImpl
                            && childPsi !is BrsAnonSubDeclImpl)
                }
                is BrsObjectLiteralImpl -> {
                    indentIf(childPsi is BrsObjectPropertyImpl)
                }
                is BrsArrayImpl -> {
                    indentIf(childType != T_LBRACK
                            && childType != T_RBRACK)
                }
                else -> Indent.getNoneIndent()
            }
        }
    }
}