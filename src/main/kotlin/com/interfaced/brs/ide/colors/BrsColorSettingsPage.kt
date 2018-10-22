package com.interfaced.brs.ide.colors

import com.intellij.openapi.editor.colors.TextAttributesKey
import com.intellij.openapi.fileTypes.SyntaxHighlighter
import com.intellij.openapi.options.colors.AttributesDescriptor
import com.intellij.openapi.options.colors.ColorDescriptor
import com.intellij.openapi.options.colors.ColorSettingsPage
import com.interfaced.brs.editor.highlight.BrsHighlighter
import com.interfaced.brs.ide.icons.BrsIcons.Companion.FILE
import javax.swing.Icon

class BrsColorSettingsPage : ColorSettingsPage {
    override fun getHighlighter(): SyntaxHighlighter = BrsHighlighter()

    override fun getAdditionalHighlightingTagToDescriptorMap(): MutableMap<String, TextAttributesKey>? = null

    override fun getIcon(): Icon? = FILE

    override fun getAttributeDescriptors(): Array<AttributesDescriptor> = DESCRIPTORS

    override fun getColorDescriptors(): Array<ColorDescriptor> = ColorDescriptor.EMPTY_ARRAY

    override fun getDisplayName(): String = "BrightScript"

    override fun getDemoText(): String =
            """
Sub Main()
    Run("pkg:/test.brs")
    BreakIfRunError(LINE_NUM)
    Print Run("test2.brs", "arg 1", "arg 2")
    if Run(["pkg:/file1.brs","pkg:/file2.brs"])<>4 then stop
    BreakIfRunError(LINE_NUM)
    stop
End Sub

function BreakIfRunError(ln) as Void
    el=GetLastRunCompileError()
    obj = {
        prop: "foo",
        "StrProp": 42
        again: &hFAA
    }
    if el=invalid then
        el$=GetLastRunRuntimeError()
        if el=&hFC or el=&hE2 then return
        'FC==ERR_NORMAL_END, E2=ERR_VALUE_RETURN
        print "Runtime Error (line ";ln;"): ";el
        stop
    else
        print "compile error (line ";ln;")"
        a = function(f) : f += 1 : endfunction
        for each e in el
            for each i in e
                print @location, #width*2, i;": ";e[i]
            end for
        end for
        stop
   end if
End function""".trimIndent()

    companion object {
        private val DESCRIPTORS = arrayOf(
                AttributesDescriptor("Keyword", BrsHighlighter.KEYWORD),
                AttributesDescriptor("Comma", BrsHighlighter.COMMA),
                AttributesDescriptor("Colon", BrsHighlighter.COLON),
                AttributesDescriptor("Identifier", BrsHighlighter.IDENTIFIER),
                AttributesDescriptor("Comment", BrsHighlighter.COMMENT),
                AttributesDescriptor("Constant", BrsHighlighter.CONSTANT),
                AttributesDescriptor("String", BrsHighlighter.STRING),
                AttributesDescriptor("Number", BrsHighlighter.NUMBER),
                AttributesDescriptor("Meta", BrsHighlighter.META),
                AttributesDescriptor("Function identifier", BrsHighlighter.DECLARATION),
                AttributesDescriptor("Property", BrsHighlighter.PROPERTY),
                AttributesDescriptor("Type", BrsHighlighter.TYPE)
        )
    }
}