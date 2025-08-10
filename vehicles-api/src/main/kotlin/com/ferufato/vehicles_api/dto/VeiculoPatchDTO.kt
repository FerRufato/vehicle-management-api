package com.ferufato.vehicles_api.dto

import jakarta.validation.constraints.Min
import jakarta.validation.constraints.Size

data class VeiculoPatchDTO(
    @field:Size(min = 2, max = 255, message = "veiculo deve ter entre 2 e 255 caracteres")
    val veiculo: String? = null,

    val marca: String? = null,

    @field:Min(1900, message = "ano deve ser >= 1900")
    val ano: Int? = null,

    val cor: String? = null,

    @field:Size(max = 2000, message = "descricao pode ter no máximo 2000 caracteres")
    val descricao: String? = null,

    val vendido: Boolean? = null
)
