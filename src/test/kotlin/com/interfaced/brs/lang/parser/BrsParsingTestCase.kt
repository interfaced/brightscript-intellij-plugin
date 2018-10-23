import com.intellij.testFramework.ParsingTestCase
import com.interfaced.brs.lang.parser.BrsParserDefinition

class BrsParsingTestCase : ParsingTestCase("fixtures/parser", "brs", true, BrsParserDefinition()) {
    fun `test while`() = doTest(true)
    fun `test object lit`() = doTest(true)
    fun `test identifier`() = doTest(true)
    fun `test array lit`() = doTest(true)
    fun `test if`() = doTest(true)
    fun `test assign`() = doTest(true)
    fun `test for`() = doTest(true)
    fun `test comment`() = doTest(true)
    fun `test dim`() = doTest(true)
    fun `test function`() = doTest(true)
    fun `test goto label`() = doTest(true)
    fun `test print`() = doTest(true)
    fun `test sub`() = doTest(true)
    fun `test number`() = doTest(true)
    fun `test end`() = doTest(true)
    fun `test exit`() = doTest(true)
    fun `test library`() = doTest(true)
    fun `test return`() = doTest(true)
    fun `test stop`() = doTest(true)

    override fun getTestDataPath(): String = "src/test/testData"
    override fun skipSpaces(): Boolean = false
    override fun includeRanges(): Boolean = true

    override fun getTestName(lowercaseFirstLetter: Boolean): String {
        val camelCase = super.getTestName(lowercaseFirstLetter)
        return camelOrWordsToSnake(camelCase)
    }

    companion object {
        @JvmStatic
        fun camelOrWordsToSnake(name: String): String {
            if (' ' in name) return name.trim().replace(" ", "_")

            return name.split("(?=[A-Z])".toRegex()).joinToString("_", transform = String::toLowerCase)
        }
    }
}