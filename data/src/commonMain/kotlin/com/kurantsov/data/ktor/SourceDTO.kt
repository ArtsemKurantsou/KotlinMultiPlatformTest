package com.kurantsov.data.ktor

import kotlinx.serialization.Serializable

@Serializable
data class SourceDTO(
    val id: String?,
    val name: String
)