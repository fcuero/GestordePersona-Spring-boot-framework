package com.darkcode.spring.app.gertordepersona;

public class Persona {
    private String nombre;
    private int edad;
    private String correo;
    private String estadoCivil;

    // Constructor vacío
    public Persona() {}

    // Constructor con parámetros
    public Persona(String nombre, int edad, String correo, String estadoCivil) {
        this.nombre = nombre;
        this.edad = edad;
        this.correo = correo;
        this.estadoCivil = estadoCivil;
    }

    // Getters y setters
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getEstadoCivil() {
        return estadoCivil;
    }

    public void setEstadoCivil(String estadoCivil) {
        this.estadoCivil = estadoCivil;
    }
}
