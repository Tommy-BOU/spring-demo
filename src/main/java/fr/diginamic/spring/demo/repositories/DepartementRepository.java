package fr.diginamic.spring.demo.repositories;

import fr.diginamic.spring.demo.beans.Departement;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartementRepository extends JpaRepository<Departement, Integer> {

    @EntityGraph(attributePaths = {"villes"})
    Departement findById(int id);

    @EntityGraph(attributePaths = {"villes"})
    Departement findByCodeDepartement(String code);
}
