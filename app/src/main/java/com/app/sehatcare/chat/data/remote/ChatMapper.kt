package com.app.sehatcare.chat.data.remote

import com.app.sehatcare.chat.domain.model.Department
import com.app.sehatcare.chat.domain.model.Doctor


fun DoctorDto.toDomain() = Doctor(
    id             = id,
    name           = name,
    specialization = specialization,
    experience     = experience,
    qualifications = qualifications,
    timings        = timings,
    consultationFee = consultationFee,
    branch         = branch,
    availableDays  = availableDays
)

fun DepartmentDto.toDomain() = Department(
    id               = id,
    name             = name,
    description      = description,
    commonDiseases   = commonDiseases,
    services         = services,
    availableBranches = availableBranches
)