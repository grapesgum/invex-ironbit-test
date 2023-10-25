package com.invex.test.invextest.unitTests;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.invex.test.invextest.model.Empleado;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
public class EmpleadoServiceUnitTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testGetAllEmpleados200() throws Exception{

        mockMvc.perform(get("/api/allEmpleados")
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk());
    }

    @Test
    public void testDeleteEmpleadoById200() throws Exception{

        mockMvc.perform(delete("/api/eliminarEmpleado/3")
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk());

    }

    @Test
    public void testDeleteEmpleadoById400() throws Exception {

        mockMvc.perform(delete("/api/eliminarEmpleado/hola")
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest());

    }

    @Test
    public void actualizarDatosEmpleado200() throws Exception {

        ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());

        Empleado empleado = new Empleado();

        empleado.setPrimerNombre("Iris");
        empleado.setSegundoNombre(null);
        empleado.setApellidoPaterno("Sachova");
        empleado.setApellidoMaterno("Andreeva");
        empleado.setEdad(18);
        empleado.setSexo("Femenino");
        empleado.setFechaNacimiento(LocalDate.parse("22-03-2005", DateTimeFormatter.ofPattern("dd-MM-yyyy")));
        empleado.setPuesto("Product Owner");

        byte[] expected = mapper.writeValueAsBytes(empleado);

        mockMvc.perform(put("/api/actualizarDatosEmpleado/1")
        .content(expected)
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk());

    }

    @Test
    public void actualizarDatosEmpleado400() throws Exception {

        ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());

        Empleado empleado = new Empleado();

        empleado.setPrimerNombre(null);
        empleado.setSegundoNombre(null);
        empleado.setApellidoPaterno("Sachova");
        empleado.setApellidoMaterno("Andreeva");
        empleado.setEdad(18);
        empleado.setSexo("Femenino");
        empleado.setFechaNacimiento(LocalDate.parse("22-03-2005", DateTimeFormatter.ofPattern("dd-MM-yyyy")));
        empleado.setPuesto(null);

        byte[] expected = mapper.writeValueAsBytes(empleado);

        mockMvc.perform(put("/api/actualizarDatosEmpleado/3")
        .content(expected)
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest());

    }

    @Test
    public void testInsertarEmpleados200() throws Exception {

        ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());

        List<Empleado> listaEmpleados = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            Empleado empleado = new Empleado();
            empleado.setPrimerNombre("dummy");
            empleado.setSegundoNombre("dummy");
            empleado.setApellidoPaterno("dummy");
            empleado.setApellidoMaterno("dummy");
            empleado.setEdad(18);
            empleado.setSexo("dummy");
            empleado.setFechaNacimiento(LocalDate.parse("01-01-1999", DateTimeFormatter.ofPattern("dd-MM-yyyy")));
            empleado.setPuesto("dummy");
            listaEmpleados.add(empleado);
        }

        byte[] expected = mapper.writeValueAsBytes(listaEmpleados);

        mockMvc.perform(post("/api/insertarEmpleados")
        .content(expected)
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk());

    }

    @Test
    public void testInsertarEmpleados400() throws Exception {

        ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());

        Empleado empleado = new Empleado();

        byte[] expected = mapper.writeValueAsBytes(empleado);

        mockMvc.perform(post("/api/insertarEmpleados")
        .content(expected)
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest());

    }

}