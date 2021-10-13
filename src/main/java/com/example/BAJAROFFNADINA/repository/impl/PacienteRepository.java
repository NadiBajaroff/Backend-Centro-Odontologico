package com.example.BAJAROFFNADINA.repository.impl;

import com.example.BAJAROFFNADINA.model.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PacienteRepository extends JpaRepository<Paciente, Long> {
    @Query("SELECT p FROM Paciente p WHERE p.dni = ?1")
    Paciente findPacienteByDNI(Integer dni);
}
