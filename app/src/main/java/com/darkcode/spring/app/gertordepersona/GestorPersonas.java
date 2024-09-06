package com.darkcode.spring.app.gertordepersona;


//import com.darkcode.spring.app.gertordepersona.Persona;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


@Controller
@RequestMapping("/personas")
public class GestorPersonas {

    private static List<Persona> personas = new ArrayList<>();

    @GetMapping("/")
    public String home() {
      return "redirect:/personas/listar"; // Redirige a la lista de personas
    }   


    @GetMapping("/listar")
    
    public String listarPersonas(Model model) {
        model.addAttribute("personas", personas);
        return "listarPersona"; // Nombre de la plantilla Thymeleaf para listar personas
    }

    
    @GetMapping("/agregar")
    public String mostrarFormularioAgregar(Model model) {
      model.addAttribute("persona", new Persona()); // Agrega un objeto vacío de Persona al modelo
      return "agregarPersona"; // Nombre de la plantilla Thymeleaf para agregar persona
    }


    @PostMapping("/agregar")
    public String agregarPersona(
            @RequestParam("nombre") String nombre,
            @RequestParam("edad") int edad,
            @RequestParam("correo") String correo,
            @RequestParam("estadoCivil") String estadoCivil,
            Model model) {
        
        // Manejo de mensaje basado en estado civil
        if (estadoCivil.equalsIgnoreCase("casado")) {
            System.out.println("¡Felicidades por tu matrimonio!");
        } else if (estadoCivil.equalsIgnoreCase("soltero")) {
            System.out.println("¡Sigue disfrutando de tu soltería!");
        } else {
            System.out.println("Estado civil no reconocido. Se considerará como soltero.");
            estadoCivil = "soltero";
        }

        // Crear y agregar persona a la lista
        Persona persona = new Persona(nombre, edad, correo, estadoCivil);
        personas.add(persona);

        return "redirect:/personas/listar"; // Redirigir a la lista de personas después de agregar
    }

    @GetMapping("/editar/{nombre}")
    public String mostrarFormularioEditar(@PathVariable String nombre, Model model) {
        // Buscar la persona en la lista
        Persona persona = personas.stream()
            .filter(p -> p.getNombre().equals(nombre))
            .findFirst()
            .orElse(null);

        if (persona == null) {
            return "redirect:/personas/listar"; // Redirigir si la persona no se encuentra
        }

        model.addAttribute("persona", persona);
        return "editarPersona"; // Nombre de la plantilla Thymeleaf para editar persona
    }

    @PostMapping("/editar")
    public String actualizarPersona(
            @RequestParam("nombreAntiguo") String nombreAntiguo,
            @RequestParam("nombre") String nuevoNombre,
            @RequestParam("edad") int edad,
            @RequestParam("correo") String correo,
            @RequestParam("estadoCivil") String estadoCivil) {

        System.out.println("Nombre Antiguo: " + nombreAntiguo);
        System.out.println("Nuevo Nombre: " + nuevoNombre);
        System.out.println("Edad: " + edad);
        System.out.println("Correo: " + correo);
        System.out.println("Estado Civil: " + estadoCivil);

        // Encontrar la persona con el nombre antiguo
        Persona personaExistente = personas.stream()
            .filter(p -> p.getNombre().equals(nombreAntiguo))
            .findFirst()
            .orElse(null);

        if (personaExistente != null) {
            // Actualizar los detalles de la persona
            if (!nombreAntiguo.equals(nuevoNombre)) {
                // Eliminar la persona antigua si el nombre cambia
                personas.remove(personaExistente);
                personaExistente.setNombre(nuevoNombre);
            }
            personaExistente.setEdad(edad);
            personaExistente.setCorreo(correo);
            personaExistente.setEstadoCivil(estadoCivil);

            // Agregar de nuevo si el nombre ha cambiado
            if (!nombreAntiguo.equals(nuevoNombre)) {
                personas.add(personaExistente);
            }
        }

        return "redirect:/personas/listar"; // Redirigir a la lista de personas después de actualizar
    }
    
    @GetMapping("/eliminar/{nombre}")
    public String eliminarPersona(@PathVariable("nombre") String nombre, RedirectAttributes redirectAttributes) {
        Iterator<Persona> iterator = personas.iterator();
        boolean encontrado = false;

        while (iterator.hasNext()) {
            Persona persona = iterator.next();
            if (persona.getNombre().equalsIgnoreCase(nombre)) { // Utiliza equalsIgnoreCase para mayor flexibilidad
                iterator.remove();
                encontrado = true;
                break;
            }
        }

        if (encontrado) {
            redirectAttributes.addFlashAttribute("message", "Persona eliminada exitosamente.");
        } else {
            redirectAttributes.addFlashAttribute("error", "Persona no encontrada.");
         }

        return "redirect:/personas/listar";
    }




   

}
