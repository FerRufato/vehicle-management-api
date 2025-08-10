package com.ferufato.vehicles_api.repo



import com.ferufato.vehicles_api.domain.Veiculo
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface VeiculoRepository : JpaRepository<Veiculo, Long>




