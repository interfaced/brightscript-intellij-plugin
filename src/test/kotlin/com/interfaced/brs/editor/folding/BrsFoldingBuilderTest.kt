package com.interfaced.brs.editor.folding

import com.intellij.testFramework.fixtures.LightCodeInsightFixtureTestCase
import com.interfaced.brs.BrsTestUtil

class BrsFoldingBuilderTest : LightCodeInsightFixtureTestCase() {
    fun `test function`() = doTest()
    fun `test object lit`() = doTest()

    override fun getTestDataPath(): String = "src/test/testData"
    override fun getBasePath(): String = "fixtures/folding"

    private fun doTest() {
        // FoldingBuilder must be instance of DumbAware
        myFixture.testFolding("$testDataPath/$basePath/$fileName")
    }

    private val fileName: String
        get() = "$testName.brs"

    private val testName: String
        get() = BrsTestUtil.camelOrWordsToSnake(getTestName(true))
}