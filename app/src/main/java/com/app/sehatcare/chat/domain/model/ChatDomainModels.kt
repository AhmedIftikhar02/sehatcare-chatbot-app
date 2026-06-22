package com.app.sehatcare.chat.domain.model


/**
 * Who sent the message — drives bubble alignment and color.
 */
enum class MessageSender { USER, BOT }

/**
 * A single chat message in the domain layer.
 * isTyping = true  → show the animated typing indicator (only for BOT)
 * isError  = true  → show error styling
 */
data class ChatMessage(
    val id: Long = System.currentTimeMillis(),
    val sender: MessageSender,
    val text: String,
    val isTyping: Boolean = false,
    val isError: Boolean = false,
    val fromCache: Boolean = false
)

data class Doctor(
    val id: String,
    val name: String,
    val specialization: String,
    val experience: Int,
    val qualifications: String,
    val timings: String,
    val consultationFee: Int,
    val branch: String,
    val availableDays: List<String>
) {
    val formattedFee: String get() = "Rs. ${"%,d".format(consultationFee)}"
}

data class Department(
    val id: String,
    val name: String,
    val description: String,
    val commonDiseases: List<String>,
    val services: List<String>,
    val availableBranches: List<String>
)
