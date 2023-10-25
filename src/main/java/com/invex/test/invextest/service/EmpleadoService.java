package com.invex.test.invextest.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.invex.test.invextest.model.Empleado;
import com.invex.test.invextest.model.Response;

public interface EmpleadoService {
    
    public ResponseEntity<Response> getAllEmpleados();

    public ResponseEntity<Response> deleteEmpleadoById(Long id);

    public ResponseEntity<Response> actualizarDatosEmpleado(Long id, Empleado datosEmpleado);

    public ResponseEntity<Response> insertarEmpleados(List<Empleado> listaEmpleados);
}
