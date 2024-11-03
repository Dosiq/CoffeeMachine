package kz.solva.task.coffe_machine.service;

import kz.solva.task.coffe_machine.DTO.RecipeIngredient;
import kz.solva.task.coffe_machine.domain.Recipe;
import kz.solva.task.coffe_machine.repository.Machine_repository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
@AllArgsConstructor
public class CoffeeMachineService {

    private final Machine_repository repository;
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

    public ResponseEntity<String> getAllDrinks() {
        if(!isMachineAvailable()){
            return new ResponseEntity("Coffee machine is not availabe", HttpStatus.SERVICE_UNAVAILABLE);
        }
        List<Recipe> availableDrinks = repository.findAll();
        return new ResponseEntity("All available drinks" + availableDrinks, HttpStatus.OK);
    }

    public ResponseEntity<String> makeCoffee(String name) {
        if(!isMachineAvailable()){
            return new ResponseEntity("Coffee machine is not availabe", HttpStatus.SERVICE_UNAVAILABLE);
        }
        Recipe drink = repository.findByName(name);
        if(!ingredientService.canMakeDrink(drink)){
            return new ResponseEntity<>("Not enough ingredients to make " + drink.getName(), HttpStatus.BAD_REQUEST);
        }
        ingredientService.updateIngredientQuantities(drink);
        return new ResponseEntity("Your " + drink.getName() + " is ready!", HttpStatus.OK);
    }

    public ResponseEntity<String> addNewRecipe(Recipe newRecipe) {
        if(repository.findByName(newRecipe.getName()) != null){
            return new ResponseEntity<>("Recipe with this name already exists.", HttpStatus.BAD_REQUEST);
        }
        if (isRecipeExists(newRecipe.getIngredients())) {
            return new ResponseEntity<>("Recipe with the same ingredients and quantities already exists.", HttpStatus.BAD_REQUEST);
        }
        repository.save(newRecipe);
        return new ResponseEntity("Recipe added", HttpStatus.OK);
    }

    public boolean isRecipeExists(List<RecipeIngredient> newRecipeIngredients) {
        List<Recipe> existingRecipes = repository.findAll();

        for (Recipe existingRecipe : existingRecipes) {
            if (areIngredientsEqual(existingRecipe.getIngredients(), newRecipeIngredients)) {
                return true;
            }
        }
        return false;
    }

    private boolean areIngredientsEqual(List<RecipeIngredient> existingIngredients, List<RecipeIngredient> newIngredients) {
        if (existingIngredients.size() != newIngredients.size()) {
            return false;
        }
        for (RecipeIngredient newIngredient : newIngredients) {
            boolean matchFound = existingIngredients.stream()
                    .anyMatch(existingIngredient ->
                            existingIngredient.getIngredient().getId().equals(newIngredient.getIngredient().getId()) &&
                                    existingIngredient.getQuantity().equals(newIngredient.getQuantity()));
            if (!matchFound) {
                return false;
            }
        }
        return true;
    }

}
