package com.example.BAJAROFFNADINA.service;


import com.example.BAJAROFFNADINA.model.Paciente;
import com.example.BAJAROFFNADINA.model.PacienteDTO;
import com.example.BAJAROFFNADINA.model.Turno;
import com.example.BAJAROFFNADINA.model.TurnoDTO;
import com.example.BAJAROFFNADINA.repository.impl.TurnoRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TurnoService implements IService<TurnoDTO>{
    private TurnoRepository turnoRepository;
    private ObjectMapper mapper;

    @Autowired
    public TurnoService(TurnoRepository turnoRepository, ObjectMapper mapper) {
        this.turnoRepository = turnoRepository;
        this.mapper = mapper;
    }


    public TurnoDTO save(TurnoDTO turno) {

        turnoRepository.save(mapper.convertValue(turno, Turno.class));
        return turno;
    }

    public Optional<TurnoDTO> findById(Long id) {

        TurnoDTO turnoDTO = null;
        Optional<Turno> turno = turnoRepository.findById(id);
        if(turno.isPresent()) {
            turnoDTO = mapper.convertValue(turno, TurnoDTO.class);
        }
        return Optional.ofNullable(turnoDTO);
    }

    public List<TurnoDTO> findAll() {

        List<Turno> turnos = turnoRepository.findAll();
        List<TurnoDTO> turnosDTO = new ArrayList<>();
        for(Turno tur : turnos) {
            turnosDTO.add(mapper.convertValue(tur, TurnoDTO.class));
        }
        return turnosDTO;
    }

    public List<TurnoDTO> turnosSemanales(){
        LocalDateTime hoy = LocalDateTime.now();
        LocalDateTime proximaSemana = hoy.plusDays(7);
        List<Turno> turnos = turnoRepository.turnosSemanales(hoy, proximaSemana);
        List<TurnoDTO> turnosDTO = new ArrayList<>();
        for(Turno tur : turnos) {
            turnosDTO.add(mapper.convertValue(tur, TurnoDTO.class));
        }
        return turnosDTO;
    }

    public TurnoDTO update(TurnoDTO turnoNew) {
        Turno tur = turnoRepository.findById(turnoNew.getId()).get();
        tur.setFechaHora(turnoNew.getFechaHora());
        tur.setOdontologo(turnoNew.getOdontologo());
        turnoNew.setPaciente(turnoNew.getPaciente());
        turnoRepository.save(tur);
        return mapper.convertValue(tur, TurnoDTO.class);
    }

    public void delete(Long id) {
        if (turnoRepository.findById(id).isPresent()) {
            turnoRepository.deleteById(id);
            System.out.println("Eliminado con exito!");
        } else {
            System.out.println("Turno no encontrado!");
        }
    }

    public List<TurnoDTO> findTurnosByMatricula(Integer matricula) {

        List<Turno> turnos = turnoRepository.findTurnosByMatricula(matricula);
        List<TurnoDTO> turnosDTO = new ArrayList<>();
        for(Turno turno : turnos) {
            turnosDTO.add(mapper.convertValue(turno, TurnoDTO.class));
        }
        return turnosDTO;
    }

    public List<TurnoDTO> findTurnosByDni(Integer dni) {

        List<Turno> turnos = turnoRepository.findTurnosByDni(dni);
        List<TurnoDTO> turnosDTO = new ArrayList<>();
        for(Turno turno : turnos) {
            turnosDTO.add(mapper.convertValue(turno, TurnoDTO.class));
        }
        return turnosDTO;
    }

}
