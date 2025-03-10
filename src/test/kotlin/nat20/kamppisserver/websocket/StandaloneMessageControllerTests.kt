package nat20.kamppisserver.websocket

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.assertEquals
import org.springframework.messaging.support.MessageBuilder

class MessageControllerTests {

    @Test
    fun `should store sent messages`() {
        val channel = TestMessageChannel()
        val message = MessageBuilder.withPayload("Hello, Kotlin!").build()

        channel.send(message)

        assertEquals(1, channel.getMessages().size)
        assertEquals("Hello, Kotlin!", channel.getMessages()[0].payload)
    }
}