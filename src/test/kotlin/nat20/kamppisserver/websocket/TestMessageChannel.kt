package nat20.kamppisserver.websocket

import org.springframework.messaging.Message
import org.springframework.messaging.support.AbstractSubscribableChannel
import kotlin.collections.List

/**
 * This class extends AbstractSubscribableChannel, which allows for multiple
 * subscribers to receive messages. The class is used to store messages instead
 * of passing them on to subscribers (as seen in the overridden function
 * sendInternal()). Messages can then be fetched by other test classes, like
 * MessageControllerTests.
 */
class TestMessageChannel : AbstractSubscribableChannel() {
    private val messages = mutableListOf<Message<*>>()

    fun getMessages(): List<Message<*>> = messages

    override fun sendInternal(message: Message<*>, timeout: Long): Boolean {
        messages.add(message)
        return true
    }
}