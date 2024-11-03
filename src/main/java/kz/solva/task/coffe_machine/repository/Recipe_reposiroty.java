package kz.solva.task.coffe_machine.repository;

import kz.solva.task.coffe_machine.domain.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Recipe_reposiroty extends JpaRepository<Recipe, Long> {
}
