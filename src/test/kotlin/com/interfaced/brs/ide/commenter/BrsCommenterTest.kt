package com.interfaced.brs.ide.commenter

import com.intellij.codeInsight.generation.actions.CommentByLineCommentAction
import com.intellij.testFramework.fixtures.BasePlatformTestCase
import com.interfaced.brs.lang.BrsFileType

class BrsCommenterTest : BasePlatformTestCase() {
    fun testCommenter() {
        val commentAction = CommentByLineCommentAction()
        myFixture.configureByText(BrsFileType.INSTANCE, """
            Function add2(a as Integer, b=5 as Integer) As Integer
                <caret>Return a+b
            End Function
        """.trimIndent())

        commentAction.actionPerformedImpl(project, myFixture.editor)
        myFixture.checkResult("""
            Function add2(a as Integer, b=5 as Integer) As Integer
            '    Return a+b
            End Function
        """.trimIndent())
        commentAction.actionPerformedImpl(project, myFixture.editor)
        myFixture.checkResult("""
            Function add2(a as Integer, b=5 as Integer) As Integer
            '    Return a+b
            'End Function
        """.trimIndent())

        // multiline selection
        myFixture.configureByText(BrsFileType.INSTANCE, """
            <selection>Function add2(a as Integer, b=5 as Integer) As Integer
                Return a+b
            End Function</selection>
        """.trimIndent())
        commentAction.actionPerformedImpl(project, myFixture.editor)
        myFixture.checkResult("""
            'Function add2(a as Integer, b=5 as Integer) As Integer
            '    Return a+b
            'End Function
        """.trimIndent())
    }
}