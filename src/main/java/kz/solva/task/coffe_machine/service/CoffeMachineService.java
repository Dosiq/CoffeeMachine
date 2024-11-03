package kz.solva.task.coffe_machine.service;

import kz.solva.task.coffe_machine.domain.Drink;
import kz.solva.task.coffe_machine.domain.Recipe;
import kz.solva.task.coffe_machine.repository.Machine_repository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
@AllArgsConstructor
public class CoffeMachineService {

    //Repository
    private final Machine_repository repository;

    //Service
    private final HolidayService holidayService;
    private final IngredientService ingredientService;

    public boolean isMachineAvailable() {
        LocalDate today = LocalDate.now();
        LocalTime now = LocalTime.now();
        if (today.getDayOfWeek() == DayOfWeek.SATURDAY || today.getDayOfWeek() == DayOfWeek.SUNDAY) {
            return false;
        }
        if (now.isBefore(LocalTime.of(8, 0)) || now.isAfter(LocalTime.of(17, 0))) {
            return false;
        }
        String countryCode = "KZ";
        return !holidayService.isHoliday(today, countryCode);
    }

    public List<Drink> getAllDrinks() {
        if(!isMachineAvailable()){
            return null;
        }
        return repository.findAll();
    }

    public Drink makeCoffee(String name) {
        Drink drink = repository.findByName(name);

        if(!isMachineAvailable()){
            return null;
        }

        return drink;

    }

    public Recipe addnewRecipe(Recipe newRecipe) {

    }

}
