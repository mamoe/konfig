package net.mamoe.yamlkt

import kotlinx.serialization.Serializable
import kotlin.test.Test
import kotlin.test.assertEquals


/**
 * [Multiline format](https://yaml-multiline.info/
 */ // https://yaml-multiline.info/
internal class EscapeTest {
    @Serializable
    data class TestData(
        val v: String
    )

    @Test
    fun testEscape() {

        assertEquals(
            TestData("\n").toString(),
            Yaml.default.parse(
                TestData.serializer(), """
                    v: "\n"
                """.trimIndent()
            ).toString()
        )
    }

    @Test
    fun testSingleQuoteEscapeSingleQuotes() {
        assertEquals(
            TestData("Don't").toString(),
            Yaml.default.parse(
                TestData.serializer(), """
                    v: 'Don''t'
                """.trimIndent()
            ).toString()
        )
    }

    @Test
    fun testDoubleQuoteNoEscapeLineSeparator() {
        assertEquals(
            TestData("te st").toString(),
            Yaml.default.parse(
                TestData.serializer(), """
                    v: "te
                    st"
                """.trimIndent()
            ).toString()
        )
    }

    @Test
    fun testDoubleQuoteLeadingContactLines() {
        assertEquals(
            TestData("te st").toString(),
            Yaml.default.parse(
                TestData.serializer(), """
                    v: "te
                     st"
                """.trimIndent()
            ).toString()
        )
    }

    @Test
    fun testDoubleQuoteLeadingEscapeNewline() {
        assertEquals(
            TestData("test").toString(),
            Yaml.default.parse(
                TestData.serializer(), """
                    v: "te\
                       st"
                """.trimIndent()
            ).toString()
        )
    }

    @Test
    fun testDoubleQuoteBlankLine() {
        assertEquals(
            TestData("te\nst").toString(),
            Yaml.default.parse(
                TestData.serializer(), """
                    v: "te
                    
                       st"
                """.trimIndent()
            ).toString()
        )
    }

    @Test
    fun testDoubleQuoteBlankLine2() {
        assertEquals(
            TestData("te\n\n\n\n\nst").toString(),
            Yaml.default.parse(
                TestData.serializer(), """
                    v: "te
                    
                    
                    
                    
                    
                       st"
                """.trimIndent()
            ).toString()
        )
    }

    @Test
    fun testDoubleQuoteBlankLine3() {
        assertEquals(
            TestData(" st").toString(),
            Yaml.default.parse(
                TestData.serializer(), """
                    v: "
                       st"
                """.trimIndent()
            ).toString()
        )
    }

    @Test
    fun testDoubleQuoteBlankLine4() {
        assertEquals(
            TestData("\nst").toString(),
            Yaml.default.parse(
                TestData.serializer(), """
                    v: "
                    
                       st"
                """.trimIndent()
            ).toString()
        )
    }

    @Test
    fun testNoEscape() {
        assertEquals(
            TestData(
                "\\n"
            ).toString(),
            Yaml.default.parse(
                TestData.serializer(), """
                    v: \n
                """.trimIndent()
            ).toString()
        )

        assertEquals(
            TestData(
                "p\\n"
            ).toString(),
            Yaml.default.parse(
                TestData.serializer(), """
                    v: p\n
                """.trimIndent()
            ).toString()
        )

        assertEquals(
            TestData(
                "p\\\\n"
            ).toString(),
            Yaml.default.parse(
                TestData.serializer(), """
                    v: 'p\\n'
                """.trimIndent()
            ).toString()
        )

        assertEquals(
            TestData(
                "\\n"
            ).toString(),
            Yaml.default.parse(
                TestData.serializer(), """
                    v: '\n'
                """.trimIndent()
            ).toString()
        )
    }


    /**
     * From https://yaml-multiline.info/
     */
    @Test
    fun testEscapeExample() {
        assertEquals(
            TestData(
                """
                    Several lines of text, containing "double quotes". Escapes (like \n) work.
                    In addition, newlines can be escaped to prevent them from being converted to a space.
                    Newlines can also be added by leaving a blank line. Leading whitespace on lines is ignored.
                """.trimIndent()
            ),
            Yaml.default.parse(
                TestData.serializer(), """
                v: "Several lines of text,
                containing \"double quotes\". Escapes (like \\n) work.\nIn addition,
                newlines can be esc\
                aped to prevent them from being converted to a space.
                
                Newlines can also be added by leaving a blank line.
                  Leading whitespace on lines is ignored."
            """.trimIndent()
            )
        )
    }
}
