package com.ferufato.vehicles_api.web

import com.ferufato.vehicles_api.dto.VeiculoCreateDTO
import com.ferufato.vehicles_api.dto.VeiculoPatchDTO
import com.ferufato.vehicles_api.dto.VeiculoResponseDTO
import com.ferufato.vehicles_api.service.VeiculoService
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/veiculos")
class VeiculoController(
    private val service: VeiculoService
) {
    /* CRUD */
    @PostMapping
    fun criar(@RequestBody @Valid dto: VeiculoCreateDTO): VeiculoResponseDTO =
        service.criar(dto)

    @GetMapping
    fun listar(
        @RequestParam(required = false) marca: String?,
        @RequestParam(required = false) ano: Int?,
        @RequestParam(required = false) cor: String?
    ): List<VeiculoResponseDTO> = service.listar(marca, ano, cor)

    @GetMapping("/{id}")
    fun buscar(@PathVariable id: Long): VeiculoResponseDTO =
        service.buscarPorId(id)

    @PutMapping("/{id}")
    fun atualizar(@PathVariable id: Long, @RequestBody @Valid dto: VeiculoCreateDTO): VeiculoResponseDTO =
        service.atualizar(id, dto)

    @PatchMapping("/{id}")
    fun patch(@PathVariable id: Long, @RequestBody @Valid dto: VeiculoPatchDTO): VeiculoResponseDTO =
        service.atualizarParcial(id, dto)

    @DeleteMapping("/{id}")
    fun deletar(@PathVariable id: Long) = service.deletar(id)

    /* RELATÓRIOS */
    @GetMapping("/nao-vendidos")
    fun naoVendidos(): Long = service.contarNaoVendidos()

    @GetMapping("/por-decada")
    fun porDecada(): Map<Int, Long> = service.distribuicaoPorDecada()

    @GetMapping("/por-marca")
    fun porMarca(): Map<String, Long> = service.distribuicaoPorMarca()

    @GetMapping("/ultimos-7-dias")
    fun ultimos7Dias(): List<VeiculoResponseDTO> = service.cadastradosUltimos7Dias()
}
