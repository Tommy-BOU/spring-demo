package fr.diginamic.spring.demo.repositories;

import fr.diginamic.spring.demo.beans.Departement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartementRepository extends JpaRepository<Departement, Integer> {

    Departement findById(int id);
    Departement findByCodeDepartement(String code);
}
