package com.app.sehatcare.chat.data.repository


import com.app.sehatcare.chat.data.remote.ChatApiService
import com.app.sehatcare.chat.data.remote.ChatRequestDto
import com.app.sehatcare.chat.data.remote.toDomain
import com.app.sehatcare.chat.domain.model.Department
import com.app.sehatcare.chat.domain.model.Doctor
import com.app.sehatcare.chat.domain.repository.ChatRepository
import com.app.sehatcare.core.network.NetworkMonitor
import com.app.sehatcare.core.network.safeApiCall
import com.app.sehatcare.core.result.Result
import com.app.sehatcare.core.result.map
import javax.inject.Inject

class ChatRepositoryImpl @Inject constructor(
    private val api: ChatApiService,
    private val networkMonitor: NetworkMonitor
) : ChatRepository {

    override suspend fun sendMessage(message: String): Result<String> =
        safeApiCall(networkMonitor) {
            api.sendMessage(ChatRequestDto(message = message)).response
        }

    override suspend fun getDoctors(branch: String?): Result<List<Doctor>> =
        safeApiCall(networkMonitor) {
            if (branch != null) {
                api.getDoctorsByBranch(branch).doctors
            } else {
                api.getDoctors().doctors
            }
        }.map { list -> list.map { it.toDomain() } }

    override suspend fun getDepartments(): Result<List<Department>> =
        safeApiCall(networkMonitor) {
            api.getDepartments().departments
        }.map { list -> list.map { it.toDomain() } }
}