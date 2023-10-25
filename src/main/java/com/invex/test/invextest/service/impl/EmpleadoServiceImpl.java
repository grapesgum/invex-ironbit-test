package com.invex.test.invextest.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.invex.test.invextest.entity.EmpleadoEntity;
import com.invex.test.invextest.exceptions.ApiRuntimeException;
import com.invex.test.invextest.exceptions.NotFoundEmployeeException;
import com.invex.test.invextest.model.Empleado;
import com.invex.test.invextest.model.Response;
import com.invex.test.invextest.repository.EmpleadoRepository;
import com.invex.test.invextest.service.EmpleadoService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class EmpleadoServiceImpl implements EmpleadoService{
    
    private final EmpleadoRepository empleadoRepository;

    @Autowired
    private final ModelMapper modelMapper;

    @Override
    public ResponseEntity<Response> getAllEmpleados(){
        Response response = new Response();
        
        try {
            List<EmpleadoEntity> allEmpleados = this.empleadoRepository.findAll();
            List<Empleado> listaEmpleados = allEmpleados.stream()
                                                        .map((empleado) -> modelMapper.map(empleado, Empleado.class))
                                                        .collect(Collectors.toList());
            response.setData(listaEmpleados);
            response.setMessage("La lista de empleados se obtuvo correctamente.");
            
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception error) {
            throw new ApiRuntimeException();
        }
    }

    @Override
    public ResponseEntity<Response> deleteEmpleadoById(Long id){
        
        Response response = new Response();
            Optional<EmpleadoEntity> empleado = this.empleadoRepository.findById(id);
            
            if(empleado.isPresent()) {
                this.empleadoRepository.deleteById(id);
                
                response.setData(empleado.get());
                response.setMessage("El empleado fue eliminado correctamente.");
            
                return new ResponseEntity<>(response, HttpStatus.OK);
            } else {
                throw new NotFoundEmployeeException();
            }
    }

    @Override
    public ResponseEntity<Response> actualizarDatosEmpleado(Long id, Empleado datosEmpleado){

        Response response = new Response();

            Optional<EmpleadoEntity> empleado = this.empleadoRepository.findById(id);
        
            if(empleado.isPresent()){
            
                EmpleadoEntity updateEmpleado = empleado.get();

                updateEmpleado.setPrimerNombre(datosEmpleado.getPrimerNombre());
                updateEmpleado.setSegundoNombre(datosEmpleado.getSegundoNombre());
                updateEmpleado.setApellidoPaterno(datosEmpleado.getApellidoPaterno());
                updateEmpleado.setApellidoMaterno(datosEmpleado.getApellidoMaterno());
                updateEmpleado.setEdad(datosEmpleado.getEdad());
                updateEmpleado.setSexo(datosEmpleado.getSexo());
                updateEmpleado.setFechaNacimiento(datosEmpleado.getFechaNacimiento());
                updateEmpleado.setPuesto(datosEmpleado.getPuesto());
                
                this.empleadoRepository.save(updateEmpleado);
                
                response.setData(updateEmpleado);
                response.setMessage("El registro fue actualizado correctamente.");

                return new ResponseEntity<>(response, HttpStatus.OK);
            }else {
                throw new NotFoundEmployeeException();
            }
    }

    @Override
    public ResponseEntity<Response> insertarEmpleados(List<Empleado> listaEmpleados){
        Response response = new Response();
        
        try{
            List<EmpleadoEntity> listaEmpleadoEntities = new ArrayList<EmpleadoEntity>();
        
            listaEmpleados.forEach(empleado -> {
                listaEmpleadoEntities.add(modelMapper.map(empleado, EmpleadoEntity.class));
            });
        
            this.empleadoRepository.saveAll(listaEmpleadoEntities);
            response.setData(null);
            response.setMessage("Los registros se almacenaron de forma correcta");

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            throw new ApiRuntimeException();
        }
            
    }

}
