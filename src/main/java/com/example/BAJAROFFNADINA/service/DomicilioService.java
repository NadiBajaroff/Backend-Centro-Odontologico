package com.example.BAJAROFFNADINA.service;

import com.example.BAJAROFFNADINA.model.Domicilio;
import com.example.BAJAROFFNADINA.model.DomicilioDTO;
import com.example.BAJAROFFNADINA.repository.impl.DomicilioRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class DomicilioService implements IService<DomicilioDTO>{
    private DomicilioRepository domicilioRepository;
    private ObjectMapper mapper;

    @Autowired
    public DomicilioService(DomicilioRepository domicilioRepository, ObjectMapper mapper) {
        this.domicilioRepository = domicilioRepository;
        this.mapper = mapper;
    }

    public DomicilioDTO save(DomicilioDTO domicilio) {
        domicilioRepository.save(mapper.convertValue(domicilio, Domicilio.class));
        return domicilio;
    }

    public Optional<DomicilioDTO> findById(Long id) {
        DomicilioDTO domiDTO = null;
        Optional<Domicilio> dom = domicilioRepository.findById(id);
        if(dom.isPresent()) {
            domiDTO = mapper.convertValue(dom, DomicilioDTO.class);
        }
        return Optional.ofNullable(domiDTO);
    }

    public List<DomicilioDTO> findAll() {
        List<Domicilio> domicilios = domicilioRepository.findAll();
        List<DomicilioDTO> domiciliosDTO = new ArrayList<>();
        for(Domicilio dom : domicilios) {
            domiciliosDTO.add(mapper.convertValue(dom, DomicilioDTO.class));
        }
        return domiciliosDTO;
    }

    public DomicilioDTO update(DomicilioDTO domicilioNew) {
        Domicilio dom = domicilioRepository.findById(domicilioNew.getId()).get();
        dom.setCalle(domicilioNew.getCalle());
        dom.setNumero(domicilioNew.getNumero());
        dom.setLocalidad(domicilioNew.getLocalidad());
        dom.setProvincia(domicilioNew.getProvincia());
        domicilioRepository.save(dom);
        return mapper.convertValue(dom, DomicilioDTO.class);
    }

    public void delete(Long id) {
        if(domicilioRepository.findById(id).isPresent()){
            domicilioRepository.deleteById(id);
            System.out.println("Eliminado con exito!");
        } else {
            System.out.println("Domicilio no encontrado!");
        }
    }
}
