package com.example.BAJAROFFNADINA.controller;

import com.example.BAJAROFFNADINA.exception.BadRequestException;
import com.example.BAJAROFFNADINA.model.Turno;
import com.example.BAJAROFFNADINA.model.TurnoDTO;
import com.example.BAJAROFFNADINA.service.OdontologoService;
import com.example.BAJAROFFNADINA.service.PacienteService;
import com.example.BAJAROFFNADINA.service.TurnoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Logger;


@CrossOrigin
@RestController
@RequestMapping("/turnos")
public class TurnoController {


    Logger logger = Logger.getLogger(String.valueOf(PacienteController.class));

    private final TurnoService turnoService;

    private final PacienteService pacienteService;

    private final OdontologoService odontologoService;

    @Autowired
    public TurnoController(TurnoService turnoService, PacienteService pacienteService, OdontologoService odontologoService) {
        this.turnoService = turnoService;
        this.pacienteService = pacienteService;
        this.odontologoService = odontologoService;
    }

    @PostMapping("/registro")
    public ResponseEntity saveTurno(@RequestBody TurnoDTO turno) throws BadRequestException {
        ResponseEntity respuesta;

        if(odontologoService.findById(turno.getOdontologo().getId()).isEmpty()) {
            respuesta = new ResponseEntity("Odontologo no encontrado!", HttpStatus.NOT_FOUND);
            logger.info("Error al registrar un turno, odontologo no encontrado");
            throw new BadRequestException("Odontologo no encontrado!");
        } else if (pacienteService.findById(turno.getPaciente().getId()).isEmpty()){
            respuesta = new ResponseEntity("Paciente no encontrado!", HttpStatus.NOT_FOUND);
            logger.info("Error al registrar un turno, paciente no encontrado");
            throw new BadRequestException("Paciente no encontrado!");
        }else {
            respuesta = new ResponseEntity(turnoService.save(turno), HttpStatus.OK);
        }
        return respuesta;
    }

    @GetMapping()
    public List<TurnoDTO> findAllTurnos() {
        return turnoService.findAll();
    }

    @GetMapping("/od/{matricula}")
    public List<TurnoDTO> findTurnosByMatricula(@PathVariable Integer matricula) {

        return turnoService.findTurnosByMatricula(matricula);
    }

    @GetMapping("/pac/{dni}")
    public List<TurnoDTO> findTurnosByDni(@PathVariable Integer dni) {

        return turnoService.findTurnosByDni(dni);
    }

    @GetMapping("/semana")
    public List<TurnoDTO> turnosSemanales(){
        return turnoService.turnosSemanales();
    }

    @PutMapping("/modificar")
    public ResponseEntity deleteTurno(@RequestBody TurnoDTO modificacion) throws BadRequestException{
        ResponseEntity respuesta;
        if(turnoService.findById(modificacion.getId()).isPresent()) {
            respuesta = new ResponseEntity(turnoService.update(modificacion), HttpStatus.OK);
        } else {
            respuesta = new ResponseEntity("Turno no encontrado!", HttpStatus.NOT_FOUND);
            logger.info("Error al modificar un turno, no se encontro!");
            throw new BadRequestException("Turno no encontrado!");
        }
        return respuesta;
    }

    @DeleteMapping("/borrar/{id}")
    public ResponseEntity deleteTurno(@PathVariable Long id) throws BadRequestException{
        ResponseEntity respuesta;

        if(turnoService.findById(id).isPresent()) {
            turnoService.delete(id);
            respuesta = ResponseEntity.status(HttpStatus.NO_CONTENT).body("Eliminado correctamente!");
        } else {
            respuesta = new ResponseEntity("Turno no encontrado!", HttpStatus.NOT_FOUND);
            logger.info("Error al eliminar un turno, no se encontro!");
            throw new BadRequestException("Turno no encontrado!");
        }
        return respuesta;
    }

}

