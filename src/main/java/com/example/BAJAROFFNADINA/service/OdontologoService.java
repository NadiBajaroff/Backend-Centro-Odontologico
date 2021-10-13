package com.example.BAJAROFFNADINA.service;

import com.example.BAJAROFFNADINA.model.Odontologo;
import com.example.BAJAROFFNADINA.model.OdontologoDTO;
import com.example.BAJAROFFNADINA.repository.impl.OdontologoRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
    public class OdontologoService implements IService<OdontologoDTO> {
        private OdontologoRepository odontologoRepository;
        private ObjectMapper mapper;

        @Autowired
        public OdontologoService(OdontologoRepository odontologoRepository, ObjectMapper mapper) {
            this.odontologoRepository = odontologoRepository;
            this.mapper = mapper;
        }

        public OdontologoDTO save(OdontologoDTO odontologo) {

            odontologoRepository.save(mapper.convertValue(odontologo, Odontologo.class));
            return odontologo;
        }

        public Optional<OdontologoDTO> findById(Long id) {

            OdontologoDTO odonDTO = null;
            Optional<Odontologo> odon = odontologoRepository.findById(id);
            if(odon.isPresent()) {
                odonDTO = mapper.convertValue(odon, OdontologoDTO.class);
            }
            return Optional.ofNullable(odonDTO);
        }

        public List<OdontologoDTO> findAll() {

            List<Odontologo> odontologos = odontologoRepository.findAll();
            List<OdontologoDTO> odontologosDTO = new ArrayList<>();
            for(Odontologo odon : odontologos) {
                odontologosDTO.add(mapper.convertValue(odon, OdontologoDTO.class));
            }
            return odontologosDTO;
        }

        public OdontologoDTO update(OdontologoDTO odontologoNew) {
            Odontologo odon = odontologoRepository.findById(odontologoNew.getId()).get();
            odon.setNombre(odontologoNew.getNombre());
            odon.setApellido(odontologoNew.getApellido());
            odon.setMatricula(odontologoNew.getMatricula());
            odontologoRepository.save(odon);
            return mapper.convertValue(odon, OdontologoDTO.class);
        }

        public void delete(Long id) {
            if (odontologoRepository.findById(id).isPresent()) {
                odontologoRepository.deleteById(id);
                System.out.println("Eliminado con exito!");
            } else {
                System.out.println("Odontologo no encontrado!");
            }
        }

        public OdontologoDTO findOdontologoByMatricula(Integer matricula) {
            OdontologoDTO odonDTO = null;
            Odontologo odon = odontologoRepository.findOdontologoByMatricula(matricula);
            if(odon != null) {
                odonDTO = mapper.convertValue(odon, OdontologoDTO.class);
            }
            return odonDTO;
        }
    }

