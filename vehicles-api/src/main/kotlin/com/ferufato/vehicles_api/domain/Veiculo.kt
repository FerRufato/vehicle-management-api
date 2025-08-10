package com.ferufato.vehicles_api.domain

import jakarta.persistence.*
import java.time.LocalDateTime


@Entity
data class Veiculo(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    var veiculo: String = "",
    var marca: String = "",
    var ano: Int = 0,
    var cor: String = "",

    @Column(columnDefinition = "TEXT")
    var descricao: String = "",

    var vendido: Boolean = false,
    val created: LocalDateTime = LocalDateTime.now(),
    var updated: LocalDateTime? = null
)
