package com.interfaced.brs.editor.folding

import com.intellij.testFramework.fixtures.BasePlatformTestCase
import com.interfaced.brs.BrsTestUtil

class BrsFoldingBuilderTest : BasePlatformTestCase() {
    fun `test function`() = doTest()
    fun `test object lit`() = doTest()
    fun `test array lit`() = doTest()
    fun `test sub`() = doTest()
    fun `test if else`() = doTest()
    fun `test for`() = doTest()
    fun `test while`() = doTest()

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