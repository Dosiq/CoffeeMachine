package kz.solva.task.coffe_machine.repository;

import kz.solva.task.coffe_machine.domain.Drink;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Machine_repository extends JpaRepository<Drink, Long> {

    Drink findByName(String name);
}
