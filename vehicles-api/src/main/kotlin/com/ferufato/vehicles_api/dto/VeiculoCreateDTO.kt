package com.ferufato.vehicles_api.dto

import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

data class VeiculoCreateDTO(
    @field:NotBlank(message = "veiculo é obrigatório")
    @field:Size(min = 2, max = 255, message = "veiculo deve ter entre 2 e 255 caracteres")
    val veiculo: String,

    @field:NotBlank(message = "marca é obrigatória")
    val marca: String,

    @field:Min(value = 1900, message = "ano deve ser maior ou igual a 1900")
    val ano: Int,

    @field:NotBlank(message = "cor é obrigatória")
    val cor: String,

    @field:Size(max = 2000, message = "descricao pode ter no máximo 2000 caracteres")
    val descricao: String? = null,

    val vendido: Boolean = false
)
