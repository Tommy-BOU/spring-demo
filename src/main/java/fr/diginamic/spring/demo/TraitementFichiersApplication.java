package fr.diginamic.spring.demo;

import fr.diginamic.spring.demo.dtos.DepartementDto;
import fr.diginamic.spring.demo.dtos.DepartementMapper;
import fr.diginamic.spring.demo.services.DepartementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@SpringBootApplication
@Primary
@Profile("file")
public class TraitementFichiersApplication implements CommandLineRunner {

    @Autowired
    DepartementService departementService;

    @Autowired
    DepartementMapper departementMapper;

    public static void main(String[] args) {
        System.setProperty("spring.profiles.active", "file");
        SpringApplication application = new SpringApplication(TraitementFichiersApplication.class);
        application.setWebApplicationType(WebApplicationType.NONE);
        application.run(args);
    }

    @Override
    public void run(String... args) throws Exception {
        RestTemplate restTemplate = new RestTemplate();
        DepartementDto[] responseArray = restTemplate.getForObject("https://geo.api.gouv.fr/departements", DepartementDto[].class);
        List<DepartementDto> departements = departementService.extractDepartements();

        for (DepartementDto dep : departements) {
            for (DepartementDto response : responseArray) {
                if (dep.getCodeDepartement().equals(response.getCodeDepartement())) {
                    dep.setNom(response.getNom());
                    departementService.modifierDepartement(dep.getId(), departementMapper.toEntity(dep));
                }
            }
        }
    }
}
