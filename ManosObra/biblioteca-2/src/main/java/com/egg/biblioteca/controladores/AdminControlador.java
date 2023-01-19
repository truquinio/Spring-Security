package com.egg.biblioteca.controladores;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller // @Controller = Indica al framework de Spring que la clase es tipo controladora
@RequestMapping("/admin") // @RequestMapping("/admin") = Configura la URL de clase controladora
public class AdminControlador {

    @GetMapping("/dashboard")
    public String panelAdministrativo() {
        return "panel.html";
    }
}