package com.example.BAJAROFFNADINA;

import com.example.BAJAROFFNADINA.model.Domicilio;
import com.example.BAJAROFFNADINA.model.DomicilioDTO;
import com.example.BAJAROFFNADINA.model.Paciente;

import com.example.BAJAROFFNADINA.model.PacienteDTO;
import com.example.BAJAROFFNADINA.service.PacienteService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class PacienteServiceTest {

    @Autowired
    PacienteService pacienteService;

    @Test
    public void findAllPacientes()
    {
        PacienteDTO paciente = new PacienteDTO("Hugo", "Felt", 12548, LocalDate.now(), new Domicilio( "Guha",
                "125",
                "Ferti",
                "Chubut"));

        pacienteService.save(paciente);

        List<PacienteDTO> pacientes = pacienteService.findAll();



        boolean resultado = pacientes.size() > 0;

        assertTrue(resultado);

    }
}
