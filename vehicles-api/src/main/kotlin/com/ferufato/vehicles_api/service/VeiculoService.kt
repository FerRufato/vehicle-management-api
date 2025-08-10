package com.ferufato.vehicles_api.service

import com.ferufato.vehicles_api.domain.Veiculo
import com.ferufato.vehicles_api.dto.VeiculoCreateDTO
import com.ferufato.vehicles_api.dto.VeiculoPatchDTO
import com.ferufato.vehicles_api.dto.VeiculoResponseDTO
import com.ferufato.vehicles_api.repo.VeiculoRepository
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException
import java.time.LocalDateTime

@Service
class VeiculoService(
    private val repo: VeiculoRepository
) {
    private val marcasValidas = setOf(
        "Ford", "Fiat", "Chevrolet", "Volkswagen", "Toyota", "Honda", "Hyundai", "Renault", "Peugeot"
    )

    /* CREATE */
    fun criar(dto: VeiculoCreateDTO): VeiculoResponseDTO {
        val marcaNormalizada = normalizarMarca(dto.marca)
        validarMarca(marcaNormalizada)

        val entity = Veiculo(
            veiculo = dto.veiculo.trim(),
            marca = marcaNormalizada,
            ano = dto.ano,
            cor = dto.cor.trim(),
            descricao = dto.descricao?.trim().orEmpty(),
            vendido = dto.vendido,
            created = LocalDateTime.now(),
            updated = null
        )
        return repo.save(entity).toResponse()
    }

    /* LIST + FILTERS */
    fun listar(marca: String?, ano: Int?, cor: String?): List<VeiculoResponseDTO> {
        var lista = repo.findAll()
        if (!marca.isNullOrBlank()) lista = lista.filter { v -> v.marca.equals(marca, true) }
        if (ano != null)            lista = lista.filter { v -> v.ano == ano }
        if (!cor.isNullOrBlank())   lista = lista.filter { v -> v.cor.equals(cor, true) }
        return lista.map { it.toResponse() }
    }

    /* READ BY ID */
    fun buscarPorId(id: Long): VeiculoResponseDTO =
        repo.findById(id).orElseThrow {
            ResponseStatusException(HttpStatus.NOT_FOUND, "Veículo $id não encontrado")
        }.toResponse()

    /* UPDATE (PUT) */
    fun atualizar(id: Long, dto: VeiculoCreateDTO): VeiculoResponseDTO {
        val existente = repo.findById(id).orElseThrow {
            ResponseStatusException(HttpStatus.NOT_FOUND, "Veículo $id não encontrado")
        }
        val marcaNormalizada = normalizarMarca(dto.marca)
        validarMarca(marcaNormalizada)

        existente.apply {
            veiculo = dto.veiculo.trim()
            marca = marcaNormalizada
            ano = dto.ano
            cor = dto.cor.trim()
            descricao = dto.descricao?.trim().orEmpty()
            vendido = dto.vendido
            updated = LocalDateTime.now()
        }
        return repo.save(existente).toResponse()
    }

    /* UPDATE (PATCH) */
    fun atualizarParcial(id: Long, dto: VeiculoPatchDTO): VeiculoResponseDTO {
        val existente = repo.findById(id).orElseThrow {
            ResponseStatusException(HttpStatus.NOT_FOUND, "Veículo $id não encontrado")
        }
        dto.veiculo?.let { existente.veiculo = it.trim() }
        dto.marca?.let  { val n = normalizarMarca(it); validarMarca(n); existente.marca = n }
        dto.ano?.let    { existente.ano = it }
        dto.cor?.let    { existente.cor = it.trim() }
        dto.descricao?.let { existente.descricao = it.trim() }
        dto.vendido?.let   { existente.vendido = it }
        existente.updated = LocalDateTime.now()
        return repo.save(existente).toResponse()
    }

    /* DELETE */
    fun deletar(id: Long) {
        if (!repo.existsById(id)) throw ResponseStatusException(HttpStatus.NOT_FOUND, "Veículo $id não encontrado")
        repo.deleteById(id)
    }

    /* REPORTS */
    fun contarNaoVendidos(): Long =
        repo.findAll().count { !it.vendido }.toLong()

    fun distribuicaoPorDecada(): Map<Int, Long> =
        repo.findAll()
            .groupBy { (it.ano / 10) * 10 }
            .mapValues { it.value.size.toLong() }
            .toSortedMap()

    fun distribuicaoPorMarca(): Map<String, Long> =
        repo.findAll()
            .groupBy { it.marca }
            .mapValues { it.value.size.toLong() }
            .toSortedMap()

    fun cadastradosUltimos7Dias(): List<VeiculoResponseDTO> {
        val limite = LocalDateTime.now().minusDays(7)
        return repo.findAll().filter { it.created.isAfter(limite) }.map { it.toResponse() }
    }

    /* Helpers */
    private fun normalizarMarca(valor: String) =
        valor.trim().lowercase().replaceFirstChar { it.titlecase() }

    private fun validarMarca(marca: String) {
        if (marca !in marcasValidas) {
            throw ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "Marca inválida: '$marca'. Válidas: ${marcasValidas.sorted().joinToString(", ")}"
            )
        }
    }

    private fun Veiculo.toResponse() = VeiculoResponseDTO(
        id = this.id ?: 0L,
        veiculo = this.veiculo,
        marca = this.marca,
        ano = this.ano,
        cor = this.cor,
        descricao = this.descricao,
        vendido = this.vendido,
        created = this.created,
        updated = this.updated
    )
}
