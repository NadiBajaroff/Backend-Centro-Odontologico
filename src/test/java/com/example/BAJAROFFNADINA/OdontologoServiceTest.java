package com.example.BAJAROFFNADINA;

import com.example.BAJAROFFNADINA.model.Odontologo;
import com.example.BAJAROFFNADINA.model.OdontologoDTO;
import com.example.BAJAROFFNADINA.service.OdontologoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class OdontologoServiceTest {

    @Autowired
    OdontologoService odontologoService;

    @Test
    public void saveOdontologo()
    {
        OdontologoDTO odontologo = new OdontologoDTO("Hugo", "Felt", 1254);

        odontologoService.save(odontologo);

        List<OdontologoDTO> odontologos = odontologoService.findAll();

        boolean resultado = odontologos.size() > 0;

        assertTrue(resultado);

    }

}
