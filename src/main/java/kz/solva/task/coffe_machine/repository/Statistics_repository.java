package kz.solva.task.coffe_machine.repository;

import kz.solva.task.coffe_machine.domain.Recipe;
import kz.solva.task.coffe_machine.domain.Statistics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Statistics_repository extends JpaRepository<Statistics, Long> {

     Statistics findByDrink(Recipe drink);
}
