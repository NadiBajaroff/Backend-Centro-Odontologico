package com.example.BAJAROFFNADINA.repository.impl;

import com.example.BAJAROFFNADINA.model.Odontologo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface OdontologoRepository extends JpaRepository<Odontologo, Long> {
    @Query("SELECT o FROM Odontologo o WHERE o.matricula = ?1")
    Odontologo findOdontologoByMatricula(Integer matricula);
}
