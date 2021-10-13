package com.example.BAJAROFFNADINA.controller;

import com.example.BAJAROFFNADINA.exception.BadRequestException;
import com.example.BAJAROFFNADINA.model.Paciente;
import com.example.BAJAROFFNADINA.model.PacienteDTO;
import com.example.BAJAROFFNADINA.service.PacienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Logger;

@CrossOrigin
@RestController
@RequestMapping("/pacientes")
public class PacienteController {

    Logger logger = Logger.getLogger(String.valueOf(PacienteController.class));

    private final PacienteService pacienteService;

    @Autowired
    public PacienteController(PacienteService pacienteService) {
        this.pacienteService = pacienteService;
    }

    @PostMapping("/registro")
    public ResponseEntity savePaciente(@RequestBody PacienteDTO paciente) throws BadRequestException {
        ResponseEntity respuesta;
        if(pacienteService.findPacienteByDni(paciente.getDni()) != null) {
            respuesta = new ResponseEntity("El dni ingresado ya existe!", HttpStatus.CONFLICT);
            logger.info("Error al registrar un paciente, dni ya existe");
            throw new BadRequestException("El dni ingresado ya existe!");
        } else {
            respuesta = new ResponseEntity(pacienteService.save(paciente), HttpStatus.OK);
        }
        return respuesta;
    }

    @GetMapping("/{dni}")
    public PacienteDTO findPacienteByDni(@PathVariable Integer dni) {

        return pacienteService.findPacienteByDni(dni);
    }

    @GetMapping()
    public List<PacienteDTO> findAllPacientes() {
        return pacienteService.findAll();
    }

    @PutMapping("/modificar")
    public ResponseEntity updatePaciente(@RequestBody PacienteDTO modificacion) throws BadRequestException{
        ResponseEntity respuesta;

        if(pacienteService.findById(modificacion.getId()).isPresent()) {
            respuesta = new ResponseEntity(pacienteService.update(modificacion), HttpStatus.OK);
        } else {
            respuesta = new ResponseEntity("Paciente no encontrado!", HttpStatus.NOT_FOUND);
            logger.info("Error al modificar un paciente, no se encontro!");
            throw new BadRequestException("Paciente no encontrado!");
        }
        return respuesta;
    }

    @DeleteMapping("/borrar/{id}")
    public ResponseEntity deletePaciente(@PathVariable Long id) throws BadRequestException{
        ResponseEntity respuesta;

        if(pacienteService.findById(id).isPresent()) {
            pacienteService.delete(id);
            respuesta = ResponseEntity.status(HttpStatus.NO_CONTENT).body("Eliminado correctamente!");
        } else {
            respuesta = new ResponseEntity("Paciente no encontrado!", HttpStatus.NOT_FOUND);
            logger.info("Error al eliminar un paciente, no se encontro!");
            throw new BadRequestException("Paciente no encontrado!");
        }
        return respuesta;
    }

}

