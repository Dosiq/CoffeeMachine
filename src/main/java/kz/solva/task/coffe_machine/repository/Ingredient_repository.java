package kz.solva.task.coffe_machine.repository;

import kz.solva.task.coffe_machine.domain.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Ingredient_repository extends JpaRepository<Ingredient, Long> {
    boolean existsByName(String name);

    Ingredient findByName(String name);
}
