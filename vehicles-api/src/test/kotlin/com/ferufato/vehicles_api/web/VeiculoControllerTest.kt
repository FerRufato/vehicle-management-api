package com.ferufato.vehicles_api.web

import com.ferufato.vehicles_api.dto.VeiculoResponseDTO
import com.ferufato.vehicles_api.service.VeiculoService
import org.junit.jupiter.api.Test
import org.mockito.BDDMockito.given
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import java.time.LocalDateTime
import org.springframework.web.server.ResponseStatusException
import org.springframework.http.HttpStatus

@WebMvcTest(VeiculoController::class)
class VeiculoControllerTest @Autowired constructor(
    private val mockMvc: MockMvc
) {

    @MockBean
    lateinit var veiculoService: VeiculoService



    @Test
    fun `GET veiculos deve retornar lista completa`() {
        val now = LocalDateTime.now()
        val mockList = listOf(
            VeiculoResponseDTO(1, "Civic", "Honda", 2019, "preto", "sedan", false, now, null),
            VeiculoResponseDTO(2, "Corolla", "Toyota", 2020, "prata", "sedan", true, now, null)
        )

        given(veiculoService.listar(null, null, null)).willReturn(mockList)

        mockMvc.get("/veiculos")
            .andExpect {
                status { isOk() }
                content { contentTypeCompatibleWith(MediaType.APPLICATION_JSON) }
                jsonPath("$.length()") { value(2) }
                jsonPath("$[0].veiculo") { value("Civic") }   // troque para $.nome se for o seu campo
            }
    }

    @Test
    fun `GET veiculos com filtro marca=Honda deve retornar 1`() {
        val now = LocalDateTime.now()
        val mockList = listOf(
            VeiculoResponseDTO(3, "Fit", "Honda", 2022, "branco", "", false, now, null)
        )
        given(veiculoService.listar("Honda", null, null)).willReturn(mockList)

        mockMvc.get("/veiculos?marca=Honda")
            .andExpect {
                status { isOk() }
                jsonPath("$.length()") { value(1) }
                jsonPath("$[0].marca") { value("Honda") }
            }
    }

    @Test
    fun `GET veiculos id existente retorna 200`() {
        val now = LocalDateTime.now()
        val dto = VeiculoResponseDTO(10, "Golf", "Volkswagen", 2016, "azul", "teste", false, now, null)
        given(veiculoService.buscarPorId(10)).willReturn(dto)

        mockMvc.get("/veiculos/10")
            .andExpect {
                status { isOk() }
                jsonPath("$.id") { value(10) }
                jsonPath("$.veiculo") { value("Golf") }       // troque para $.nome se for o caso
            }
    }

    @Test
    fun `GET veiculos id inexistente retorna 404`() {
        given(veiculoService.buscarPorId(999))
            .willAnswer { throw ResponseStatusException(HttpStatus.NOT_FOUND, "Veículo não encontrado") }

        mockMvc.get("/veiculos/999")
            .andExpect { status { isNotFound() } }
    }
}
