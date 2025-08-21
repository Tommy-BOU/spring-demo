package fr.diginamic.demo.services;

import org.springframework.stereotype.Service;

@Service // Demande a spring de créer une instance de HelloService et de la mettre dans son conteneur pour une injection ultérieure
public class HelloService {

    public String salutations() {

        return "Je suis la classe de service et je vous dis Bonjour";
    }
}
