package nat20.kamppisserver.websocket

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import io.mockk.*
import nat20.kamppisserver.api.MessageController
import nat20.kamppisserver.TestPrincipal
import nat20.kamppisserver.domain.Match
import nat20.kamppisserver.domain.Message
import nat20.kamppisserver.domain.MessageDTO
import nat20.kamppisserver.domain.User
import nat20.kamppisserver.repository.MatchRepository
import nat20.kamppisserver.repository.MessageRepository
import nat20.kamppisserver.service.UserService
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.springframework.messaging.support.MessageBuilder
import org.springframework.context.support.StaticApplicationContext
import org.springframework.messaging.MessageChannel
import org.springframework.messaging.simp.SimpMessageSendingOperations
import org.springframework.messaging.simp.SimpMessagingTemplate
import org.springframework.messaging.simp.annotation.support.SimpAnnotationMethodMessageHandler
import org.springframework.messaging.simp.stomp.StompCommand
import org.springframework.messaging.simp.stomp.StompHeaderAccessor
import org.springframework.messaging.SubscribableChannel
import java.util.HashMap
import org.springframework.messaging.converter.MappingJackson2MessageConverter
import java.time.LocalDateTime

/**
 * Unit test class for MessageController that does NOT load the Spring
 * application context. For integration tests, see
 * ContextMessageControllerTests.

class StandaloneMessageControllerTests {

    private lateinit var userService: UserService
    private lateinit var messagingTemplate: SimpMessagingTemplate
    private lateinit var messageRepository: MessageRepository
    private lateinit var matchRepository: MatchRepository
    private lateinit var controller: MessageController

    private lateinit var testMessageChannel: TestMessageChannel
    private lateinit var annotationMethodHandler: TestAnnotationMethodHandler

    @BeforeEach
    fun setup() {
        userService = mockk()
        messagingTemplate = mockk()
        messageRepository = mockk()
        matchRepository = mockk()

        controller = MessageController(userService, messagingTemplate, messageRepository, matchRepository)

        val testUser1 = User("test1@example.com", id = 1L)
        val testUser2 = User("test2@example.com", id = 2L)
        val testMatch = Match(mutableSetOf(testUser1, testUser2), id = 1L)
        testUser1.matches = mutableSetOf(testMatch)

        every { messagingTemplate.convertAndSend(any<String>(), any<MessageDTO>()) } just Runs
        every { userService.findActiveUserByEmail("test1@example.com") } returns testUser1
        every { matchRepository.findByIdWithUsers(1L) } returns testMatch
        every { messageRepository.findByMatchIdOrderByCreatedAtAsc(1L) } returns listOf(
            Message(testUser1, testMatch, "test message", id = 1L)
        )

        testMessageChannel = TestMessageChannel()
        annotationMethodHandler = TestAnnotationMethodHandler(
            TestMessageChannel(), testMessageChannel, SimpMessagingTemplate(TestMessageChannel())
        )
        annotationMethodHandler.registerHandler(controller)
        annotationMethodHandler.setDestinationPrefixes(listOf("/app"))
        annotationMethodHandler.setMessageConverter(MappingJackson2MessageConverter())
        annotationMethodHandler.applicationContext = StaticApplicationContext()
        annotationMethodHandler.afterPropertiesSet()
    }

    @Test
    fun `TestMessageChannel should store sent messages`() {
        val channel = TestMessageChannel()
        val message = MessageBuilder.withPayload("Hello World!").build()

        channel.send(message)

        assertEquals(1, channel.getMessages().size)
        assertEquals("Hello World!", channel.getMessages()[0].payload)
    }

    @Test
    fun `should forward subscription messages to the correct channel (matchId = 1)`() {
        val headers = StompHeaderAccessor.create(StompCommand.SEND).apply {
            subscriptionId = "0"
            destination = "/app/matches/1/messages"
            sessionId = "0"
            user = TestPrincipal("test1@example.com")
            sessionAttributes = HashMap()
            setHeader("email", "test1@example.com")
        }

        val messageDTO = MessageDTO(
            id = null,
            senderEmail = "test1@example.com",
            senderId = 1L,
            matchId = 1L,
            content = "Hello World!",
            createdAt = LocalDateTime.now()
        )

        val objectMapper = jacksonObjectMapper().registerModule(JavaTimeModule())
        val messageJson = objectMapper.writeValueAsBytes(messageDTO)

        val message: org.springframework.messaging.Message<ByteArray> = MessageBuilder.withPayload(messageJson)
            .setHeaders(headers)
            .build()

        val messageSlot = slot<Any>()
        every { messagingTemplate.convertAndSend(any<String>(), capture(messageSlot)) } just Runs

        annotationMethodHandler.handleMessage(message)

        println("Captured message: ${messageSlot.captured}")
        println("Messages in testMessageChannel: ${testMessageChannel.getMessages()}")

        assertEquals(1, testMessageChannel.getMessages().size)

        val reply: org.springframework.messaging.Message<*> = testMessageChannel.getMessages()[0]

        val replyHeaders = StompHeaderAccessor.wrap(reply)
        assertEquals("0", replyHeaders.sessionId)
        assertEquals("0", replyHeaders.subscriptionId)
        assertEquals("/app/matches/1/messages", replyHeaders.destination)
    }

    /**
     * Custom implementation of SimpAnnotationMethodMessageHandler. This class allows
     * for registering handlers manually.
     *
     * @param inChannel The input message channel where messages are received.
     * @param outChannel The output message channel where responses are sent.
     * @param brokerTemplate The messaging template used to send messages to the broker.
     */
    private class TestAnnotationMethodHandler(
        inChannel: SubscribableChannel,
        outChannel: MessageChannel,
        brokerTemplate: SimpMessageSendingOperations
    ) : SimpAnnotationMethodMessageHandler(inChannel, outChannel, brokerTemplate) {

        fun registerHandler(handler: Any) {
            super.detectHandlerMethods(handler)
        }
    }

}
        */