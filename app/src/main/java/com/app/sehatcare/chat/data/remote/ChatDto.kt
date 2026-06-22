package com.app.sehatcare.chat.data.remote

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ChatRequestDto(
    @Json(name = "message") val message: String
)

@JsonClass(generateAdapter = true)
data class ChatResponseDto(
    @Json(name = "response")  val response: String,
    @Json(name = "fromCache") val fromCache: Boolean,
    @Json(name = "timestamp") val timestamp: String
)

@JsonClass(generateAdapter = true)
data class DoctorsResponseDto(
    @Json(name = "success") val success: Boolean,
    @Json(name = "total")   val total: Int,
    @Json(name = "doctors") val doctors: List<DoctorDto>
)

@JsonClass(generateAdapter = true)
data class DoctorDto(
    @Json(name = "id")              val id: String,
    @Json(name = "name")            val name: String,
    @Json(name = "specialization")  val specialization: String,
    @Json(name = "experience")      val experience: Int,
    @Json(name = "qualifications")  val qualifications: String,
    @Json(name = "timings")         val timings: String,
    @Json(name = "consultationFee") val consultationFee: Int,
    @Json(name = "branch")          val branch: String,
    @Json(name = "availableDays")   val availableDays: List<String>
)

@JsonClass(generateAdapter = true)
data class DepartmentsResponseDto(
    @Json(name = "success")     val success: Boolean,
    @Json(name = "total")       val total: Int,
    @Json(name = "departments") val departments: List<DepartmentDto>
)

@JsonClass(generateAdapter = true)
data class DepartmentDto(
    @Json(name = "id")                val id: String,
    @Json(name = "name")              val name: String,
    @Json(name = "description")       val description: String,
    @Json(name = "commonDiseases")    val commonDiseases: List<String>,
    @Json(name = "services")          val services: List<String>,
    @Json(name = "availableBranches") val availableBranches: List<String>
)