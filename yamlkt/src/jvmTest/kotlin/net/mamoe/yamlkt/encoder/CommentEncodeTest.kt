package net.mamoe.yamlkt.encoder

import kotlinx.serialization.Serializable
import net.mamoe.yamlkt.Comment
import org.junit.Test


internal class CommentEncodeTest {

    @Test
    fun testComments() {
        @Serializable
        data class CommentTest(
            @Comment("testA")
            val value1: String,
            @Comment("testA\nff")
            val value2: String
        )

        allBlock.testDescriptorBased(CommentTest.serializer(), CommentTest("vv", "ss"), true)
    }

    @Test
    fun testCommentsWithHierarchy() {
        @Serializable
        data class CommentTest(
            @Comment("testA")
            val value1: String,
            @Comment("testA\nff")
            val value2: String
        )

        @Serializable
        data class OuterClass(
            val inner: CommentTest,
        )

        allBlock.testDescriptorBased(OuterClass.serializer(), OuterClass(CommentTest("vv", "ss")), true)
    }
}