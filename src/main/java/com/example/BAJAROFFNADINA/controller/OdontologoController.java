package com.example.BAJAROFFNADINA.controller;

import com.example.BAJAROFFNADINA.exception.BadRequestException;
import com.example.BAJAROFFNADINA.model.Odontologo;
import com.example.BAJAROFFNADINA.model.OdontologoDTO;
import com.example.BAJAROFFNADINA.service.OdontologoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Logger;

@CrossOrigin
@RestController
@RequestMapping("/odontologos")
public class OdontologoController {

    Logger logger = Logger.getLogger(String.valueOf(OdontologoController.class));

    private final OdontologoService odontologoService;

    @Autowired
    public OdontologoController(OdontologoService odontologoService) {
        this.odontologoService = odontologoService;
    }

    @PostMapping("/registro")
    public ResponseEntity saveOdontologo(@RequestBody OdontologoDTO odontologo) throws BadRequestException {
        ResponseEntity respuesta;
        if(odontologoService.findOdontologoByMatricula(odontologo.getMatricula()) != null) {
            respuesta = new ResponseEntity("La matricula ingresada ya existe!", HttpStatus.CONFLICT);
            logger.info("Error al registrar un odontologo, matricula ya existe");
            throw new BadRequestException("La matricula ingresada ya existe!");
        } else {
            respuesta = new ResponseEntity(odontologoService.save(odontologo), HttpStatus.OK);
        }
        return respuesta;
    }

    @GetMapping("/{matricula}")
    public OdontologoDTO findOdontologoByMatricula(@PathVariable Integer matricula) {
        return odontologoService.findOdontologoByMatricula(matricula);
    }

    @GetMapping()
    public List<OdontologoDTO> findAllOdontologos() {
        return odontologoService.findAll();
    }

    @PutMapping("/modificar")
    public ResponseEntity updateOdontologo(@RequestBody OdontologoDTO odontologo) throws BadRequestException{
        ResponseEntity respuesta;
        if(odontologoService.findById(odontologo.getId()).isPresent()) {
            respuesta = new ResponseEntity(odontologoService.update(odontologo), HttpStatus.OK);
        } else {
            respuesta = new ResponseEntity("Odontologo no encontrado!", HttpStatus.NOT_FOUND);
            logger.info("Error al modificar un odontologo, no se encontro!");
            throw new BadRequestException("Odontologo no encontrado!");
        }
        return respuesta;
    }

    @DeleteMapping("/borrar/{id}")
    public ResponseEntity deleteOdontologo(@PathVariable Long id) throws BadRequestException{
        ResponseEntity respuesta;

        if(odontologoService.findById(id).isPresent()) {
            odontologoService.delete(id);
            respuesta = ResponseEntity.status(HttpStatus.NO_CONTENT).body("Eliminado correctamente!");
        } else {
            respuesta = new ResponseEntity("Odontologo no encontrado!", HttpStatus.NOT_FOUND);
            logger.info("Error al eliminar un odontologo, no se encontro!");
            throw new BadRequestException("Odontologo no encontrado!");
        }
        return respuesta;
    }

}

