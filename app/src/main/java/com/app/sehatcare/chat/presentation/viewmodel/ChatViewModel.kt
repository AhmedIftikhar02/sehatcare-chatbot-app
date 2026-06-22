package com.app.sehatcare.chat.presentation.viewmodel

import com.app.sehatcare.base.BaseViewModel
import com.app.sehatcare.chat.domain.model.ChatMessage
import com.app.sehatcare.chat.domain.model.MessageSender
import com.app.sehatcare.chat.domain.repository.ChatRepository
import com.app.sehatcare.core.result.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

/** Unique ID for the always-present typing indicator bubble. */
private const val TYPING_INDICATOR_ID = -1L

/** How fast each character is "typed" in ms — lower = faster stream feel. */
private const val STREAM_CHAR_DELAY_MS = 12L

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val repository: ChatRepository
) : BaseViewModel() {

    // ── State ──────────────────────────────────────────────────
    private val _messages = MutableStateFlow(
        listOf(
            ChatMessage(
                id     = 0L,
                sender = MessageSender.BOT,
                text   = "Assalam-o-Alaikum! 👋 Welcome to **Sehat Care Hospital**.\n\nI'm your virtual assistant. Ask me anything about:\n• Appointments & consultation fees\n• Our doctors & specializations\n• Departments & services\n• Hospital timings & branches",
            )
        )
    )
    val messages: StateFlow<List<ChatMessage>> = _messages.asStateFlow()

    private val _isSending = MutableStateFlow(false)
    val isSending: StateFlow<Boolean> = _isSending.asStateFlow()

    // ── Send message ───────────────────────────────────────────
    fun sendMessage(text: String) {
        val trimmed = text.trim()
        if (trimmed.isBlank() || _isSending.value) return

        launchSafe {
            _isSending.value = true

            // 1. Add user bubble
            _messages.update { it + ChatMessage(sender = MessageSender.USER, text = trimmed) }

            // 2. Show typing indicator
            _messages.update {
                it + ChatMessage(
                    id        = TYPING_INDICATOR_ID,
                    sender    = MessageSender.BOT,
                    text      = "",
                    isTyping  = true
                )
            }

            // 3. Call API
            when (val result = repository.sendMessage(trimmed)) {
                is Result.Success -> {
                    // Remove typing indicator
                    _messages.update { list -> list.filter { it.id != TYPING_INDICATOR_ID } }

                    // 4. Stream the response character by character
                    val fullText = result.data
                    val streamId = System.currentTimeMillis()

                    // Insert empty bot message
                    _messages.update {
                        it + ChatMessage(id = streamId, sender = MessageSender.BOT, text = "")
                    }

                    // Build up text incrementally
                    val sb = StringBuilder()
                    for (char in fullText) {
                        sb.append(char)
                        _messages.update { list ->
                            list.map { msg ->
                                if (msg.id == streamId) msg.copy(text = sb.toString()) else msg
                            }
                        }
                        delay(STREAM_CHAR_DELAY_MS)
                    }
                }

                is Result.Error -> {
                    _messages.update { list -> list.filter { it.id != TYPING_INDICATOR_ID } }
                    _messages.update {
                        it + ChatMessage(
                            sender  = MessageSender.BOT,
                            text    = "Sorry, I couldn't reach the server. Please check your connection and try again.",
                            isError = true
                        )
                    }
                }
            }

            _isSending.value = false
        }
    }

    fun clearChat() {
        _messages.value = listOf(
            ChatMessage(
                id     = 0L,
                sender = MessageSender.BOT,
                text   = "Chat cleared. How can I help you?",
            )
        )
    }
}