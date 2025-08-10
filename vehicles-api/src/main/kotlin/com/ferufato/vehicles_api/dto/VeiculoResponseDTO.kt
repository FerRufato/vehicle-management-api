package com.ferufato.vehicles_api.dto

import java.time.LocalDateTime

data class VeiculoResponseDTO(
    val id: Long,
    val veiculo: String,
    val marca: String,
    val ano: Int,
    val cor: String,
    val descricao: String,
    val vendido: Boolean,
    val created: LocalDateTime,
    val updated: LocalDateTime?
)
