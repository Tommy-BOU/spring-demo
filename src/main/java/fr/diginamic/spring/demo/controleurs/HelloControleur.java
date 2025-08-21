package fr.diginamic.spring.demo.controleurs;

import fr.diginamic.spring.demo.services.HelloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hello")
public class HelloControleur {

    @Autowired
    private HelloService service;

    @GetMapping
    public String direHello() {
        return service.salutations();
    }
}
