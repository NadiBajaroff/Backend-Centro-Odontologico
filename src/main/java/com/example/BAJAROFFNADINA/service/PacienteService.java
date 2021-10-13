package com.example.BAJAROFFNADINA.service;


import com.example.BAJAROFFNADINA.model.Paciente;
import com.example.BAJAROFFNADINA.model.PacienteDTO;
import com.example.BAJAROFFNADINA.repository.impl.PacienteRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class PacienteService implements IService<PacienteDTO>{
    private PacienteRepository pacienteRepository;
    private ObjectMapper mapper;

    @Autowired
    public PacienteService(PacienteRepository pacienteRepository, ObjectMapper mapper) {
        this.pacienteRepository = pacienteRepository;
        this.mapper = mapper;
    }

    public PacienteDTO save(PacienteDTO paciente) {
        paciente.setFechaIngreso(LocalDate.now());
        pacienteRepository.save(mapper.convertValue(paciente, Paciente.class));
        return paciente;
    }

    public Optional<PacienteDTO> findById(Long id) {

        PacienteDTO pacDTO = null;
        Optional<Paciente> pac = pacienteRepository.findById(id);
        if(pac.isPresent()) {
            pacDTO = mapper.convertValue(pac, PacienteDTO.class);
        }
        return Optional.ofNullable(pacDTO);
    }

    public List<PacienteDTO> findAll() {

        List<Paciente> pacientes = pacienteRepository.findAll();
        List<PacienteDTO> pacientesDTO = new ArrayList<>();
        for(Paciente pac : pacientes) {
            pacientesDTO.add(mapper.convertValue(pac, PacienteDTO.class));
        }
        return pacientesDTO;
    }

    public PacienteDTO update(PacienteDTO pacienteNew) {
        Paciente pac = pacienteRepository.findById(pacienteNew.getId()).get();
        pac.setNombre(pacienteNew.getNombre());
        pac.setApellido(pacienteNew.getApellido());
        pac.setDni(pacienteNew.getDni());
        pac.setFechaIngreso(pacienteNew.getFechaIngreso());
        pac.setDomicilio(pacienteNew.getDomicilio());
        pacienteRepository.save(pac);
        return mapper.convertValue(pac, PacienteDTO.class);
    }

    public void delete(Long id) {
        if (pacienteRepository.findById(id).isPresent()) {
            pacienteRepository.deleteById(id);
            System.out.println("Eliminado con exito!");
        } else {
            System.out.println("Paciente no encontrado!");
        }
    }

    public PacienteDTO findPacienteByDni(Integer dni) {

        PacienteDTO pacDTO = null;
        Paciente pac = pacienteRepository.findPacienteByDNI(dni);
        if(pac != null) {
            pacDTO = mapper.convertValue(pac, PacienteDTO.class);
        }
        return pacDTO;
    }

}
