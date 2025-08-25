package fr.diginamic.spring.demo.repositories;

import fr.diginamic.spring.demo.beans.Departement;
import fr.diginamic.spring.demo.beans.Ville;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VilleRepository extends JpaRepository<Ville, Integer> {
//    public Page<Ville> findAll();
    public Ville findById(int id);
    public Ville findByNom(String nom);
    public List<Ville> findAllByNomStartingWith(String nom);
    public List<Ville> findByNbHabitantsGreaterThan(int nbHabitants);
    public List<Ville> findByNbHabitantsBetweenOrderByNbHabitantsDesc(int min, int max);
    public List<Ville> findByDepartementAndNbHabitantsGreaterThanOrderByNbHabitantsDesc(Departement departement, int nbHabitants);
    public List<Ville> findByDepartementAndNbHabitantsBetweenOrderByNbHabitantsDesc(Departement departement, int min, int max);
}
