package com.app.sehatcare.chat.data.remote


import com.app.sehatcare.core.network.NoAuth
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

/**
 * All Sehat Care endpoints are public (no auth token needed).
 * @NoAuth tells the boilerplate's AuthInterceptor to skip the Authorization header.
 */
interface ChatApiService {

    @NoAuth
    @POST("api/chat")
    suspend fun sendMessage(@Body request: ChatRequestDto): ChatResponseDto

    @NoAuth
    @GET("api/doctors")
    suspend fun getDoctors(): DoctorsResponseDto

    @NoAuth
    @GET("api/doctors")
    suspend fun getDoctorsByBranch(@Query("branch") branch: String): DoctorsResponseDto

    @NoAuth
    @GET("api/departments")
    suspend fun getDepartments(): DepartmentsResponseDto

    @NoAuth
    @GET("api/health")
    suspend fun healthCheck(): Map<String, String>
}
