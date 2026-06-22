package com.app.sehatcare.chat.domain.repository

import com.app.sehatcare.chat.domain.model.Department
import com.app.sehatcare.chat.domain.model.Doctor
import com.app.sehatcare.core.result.Result

interface ChatRepository {
    suspend fun sendMessage(message: String): Result<String>
    suspend fun getDoctors(branch: String? = null): Result<List<Doctor>>
    suspend fun getDepartments(): Result<List<Department>>
}