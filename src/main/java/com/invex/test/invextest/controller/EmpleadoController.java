package com.invex.test.invextest.controller;

import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import com.invex.test.invextest.model.Empleado;
import com.invex.test.invextest.model.Response;
import com.invex.test.invextest.service.EmpleadoService;

@Slf4j
@Validated
@AllArgsConstructor
@RestController
@RequestMapping("/api")
public class EmpleadoController {
    
    @Autowired
    private final EmpleadoService empleadoService;

    @GetMapping("/allEmpleados")
    public ResponseEntity<Response> getAllEmpleados(){
        return this.empleadoService.getAllEmpleados();
    }

    @DeleteMapping("/eliminarEmpleado/{id}")
    public ResponseEntity<Response> deleteEmpleadoById(
        @PathVariable @Min(value = 1, message = "El ID proporcionado debe ser mayor o igual a 1.") Long id){
        return this.empleadoService.deleteEmpleadoById(id);
    }

    @PutMapping("/actualizarDatosEmpleado/{id}")
    public ResponseEntity<Response> actualizarDatosEmpleado(
        @PathVariable @Min(value = 1, message = "El ID proporcionado debe ser mayor o igual a 1.") Long id, 
        @Valid @RequestBody Empleado datosEmpleado){
        log.info(datosEmpleado.toString());
        return this.empleadoService.actualizarDatosEmpleado(id, datosEmpleado);
    }

    @PostMapping("/insertarEmpleados")
    public ResponseEntity<Response> insertarEmpleados(@Valid @RequestBody List<Empleado> listaEmpleados){
        log.info(listaEmpleados.toString());
        return this.empleadoService.insertarEmpleados(listaEmpleados);
    }

}
