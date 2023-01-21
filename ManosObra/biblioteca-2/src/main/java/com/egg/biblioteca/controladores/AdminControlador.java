package com.egg.biblioteca.controladores;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller // @Controller = Indica al framework de Spring que la clase es tipo controladora
@RequestMapping("/admin") // @RequestMapping = Configura la URL de clase controladora
public class AdminControlador {

    @GetMapping("/dashboard")  // @GetMapping = Se accede a travez de una operación GET de HTTP
    public String panelAdministrativo() { 
        return "panel.html"; //localhost:8080/admin/dashboard
    }
}