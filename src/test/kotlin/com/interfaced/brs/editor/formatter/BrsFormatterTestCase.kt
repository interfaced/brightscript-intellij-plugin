package com.interfaced.brs.editor.formatter

import com.intellij.psi.formatter.FormatterTestCase
import com.interfaced.brs.BrsTestUtil

class BrsFormatterTestCase : FormatterTestCase() {
    fun `test indent`() = doTest()

    override fun getTestName(lowercaseFirstLetter: Boolean): String {
        val camelCase = super.getTestName(lowercaseFirstLetter)
        return BrsTestUtil.camelOrWordsToSnake(camelCase)
    }

    override fun getTestDataPath() = "src/test/testData"
    override fun getBasePath(): String = "fixtures/formatter"
    override fun getFileExtension(): String = "brs"
}