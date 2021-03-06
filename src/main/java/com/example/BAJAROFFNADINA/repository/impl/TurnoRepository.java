package com.example.BAJAROFFNADINA.repository.impl;


import com.example.BAJAROFFNADINA.model.Turno;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface TurnoRepository extends JpaRepository<Turno, Long> {

    @Query("SELECT t FROM Turno t WHERE t.odontologo.matricula = ?1")
    List<Turno> findTurnosByMatricula(Integer matricula);

    @Query("SELECT t FROM Turno t WHERE t.paciente.dni = ?1")
    List<Turno> findTurnosByDni(Integer dni);

    @Query("SELECT t FROM Turno t WHERE t.fechaHora BETWEEN ?1 AND ?2")
    List<Turno> turnosSemanales(LocalDateTime fechaHora1, LocalDateTime fechaHora2);


}
