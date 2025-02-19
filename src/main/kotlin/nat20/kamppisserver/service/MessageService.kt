package nat20.kamppisserver.service
//
//import nat20.kamppisserver.domain.Chat
//import nat20.kamppisserver.domain.Match
//import nat20.kamppisserver.domain.Message
//import nat20.kamppisserver.repository.ChatRepository
//import nat20.kamppisserver.repository.MatchRepository
//import nat20.kamppisserver.repository.MessageRepository
//import nat20.kamppisserver.repository.UserRepository
//import org.springframework.stereotype.Service
//import java.util.*
//
///**
// * Service class for Message.
// */
//@Service
//class MessageService(private val messageRepository: MessageRepository,
//                     private val matchService: MatchService,
//                     private val userRepository: UserRepository,
//                     private val matchRepository: MatchRepository,
//                     private val chatRepository: ChatRepository
//) {
//
//    /**
//     * A method for starting a chat for a new Match.
//     * @param matchId ID for the match in question.
//     * @return Message type object.
//     */
//    fun startChat(matchId: Long): Message {
//        val (user1Id, user2Id) = matchService.getUserIdsForMatch(matchId) ?: throw IllegalArgumentException("Match not found.")
//
//        val sender = userRepository.findById(user1Id).orElseThrow { IllegalArgumentException("Sender not found.") }
//        val receiver = userRepository.findById(user2Id).orElseThrow { IllegalArgumentException("Receiver not found.") }
//
//        val message = Message(
//            sender = sender,
//            receiver = receiver,
//            match = matchRepository.findById(matchId).orElseThrow { IllegalArgumentException("Match not found.") },
//            content = "Chat started!" // Placeholder for first message
//        )
//
//        val senderReceiver = message.id?.let {
//            Chat(
//                messageId = it,
//                senderId = user1Id,
//                receiverId = user2Id
//            )
//        }
//
//        if (senderReceiver != null) {
//            chatRepository.save(senderReceiver)
//        }
//
//        val receiverSender = message.id?.let {
//            Chat(
//                messageId = it,
//                senderId = user2Id,
//                receiverId = user1Id
//            )
//        }
//
//        if (receiverSender != null) {
//            chatRepository.save(receiverSender)
//        }
//
//        return messageRepository.save(message)
//    }
//
//}