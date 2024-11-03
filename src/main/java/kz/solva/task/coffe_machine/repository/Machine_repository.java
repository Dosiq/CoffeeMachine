package kz.solva.task.coffe_machine.repository;

import kz.solva.task.coffe_machine.domain.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Machine_repository extends JpaRepository<Recipe, Long> {

    Recipe findByName(String name);
}
