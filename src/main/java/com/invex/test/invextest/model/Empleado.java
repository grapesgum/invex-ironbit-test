package com.invex.test.invextest.model;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Empleado{

    Long id;
    
    @NotBlank(message = "El primer nombre no puede ser nulo y debe contener al menos un caracter.")
    private String primerNombre;

    private String segundoNombre;
    
    @NotBlank(message = "El apellido paterno no puede ser nulo y debe contener al menos un caracter.")
    private String apellidoPaterno;
    
    @NotBlank(message = "El apellido materno no puede ser nulo y debe contener al menos un caracter.")
    private String apellidoMaterno;
    
    @Min(value = 18, message = "La edad mínima del empleado debe ser 18 años.")
    private Integer edad;
    
    @NotBlank(message = "El sexo no puede ser nulo y debe contener al menos un caracter.")
    private String sexo;
    
    @JsonFormat(pattern = "dd-MM-yyyy")
    @NotNull(message = "La fecha de nacimiento no puede ser nula o estar en blanco.")
    private LocalDate fechaNacimiento;
    
    @NotBlank(message = "El puesto no puede ser nulo y debe contener al menos un caracter.")
    private String puesto;

}
