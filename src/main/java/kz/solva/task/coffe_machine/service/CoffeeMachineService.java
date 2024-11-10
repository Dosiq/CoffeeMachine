package kz.solva.task.coffe_machine.service;

import kz.solva.task.coffe_machine.DTO.RecipeIngredient;
import kz.solva.task.coffe_machine.domain.Ingredient;
import kz.solva.task.coffe_machine.domain.Recipe;
import kz.solva.task.coffe_machine.repository.Ingredient_repository;
import kz.solva.task.coffe_machine.repository.Machine_repository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CoffeeMachineService {

    private final Machine_repository repository;
    private final HolidayService holidayService;
    private final IngredientService ingredientService;
    private final Ingredient_repository ingredientRepository;
    private final StatisticsService statisticsService;

    public boolean isMachineAvailable() {
        LocalDate today = LocalDate.now();
        LocalTime now = LocalTime.now();
//        if (today.getDayOfWeek() == DayOfWeek.SATURDAY || today.getDayOfWeek() == DayOfWeek.SUNDAY) {
//            return false;
//        }
//        if (now.isBefore(LocalTime.of(8, 0)) || now.isAfter(LocalTime.of(17, 0))) {
//            return false;
//        }
//        String countryCode = "KZ";
//        return !holidayService.isHoliday(today, countryCode);
        return true;
    }

    public ResponseEntity<String> getAllDrinks() {
        if(!isMachineAvailable()){
            return new ResponseEntity("Coffee machine is not availabe", HttpStatus.SERVICE_UNAVAILABLE);
        }
        List<Recipe> availableDrinks = repository.findAll();
        String allAvailableDrinks = availableDrinks.stream()
                .map(Recipe::getName)
                .map(name -> "\"" + name + "\"")
                .collect(Collectors.joining(", "));
        return new ResponseEntity("All available drinks" + allAvailableDrinks, HttpStatus.OK);
    }

    public ResponseEntity<String> makeCoffee(String name) {
        if(!isMachineAvailable()){
            return new ResponseEntity("Coffee machine is not availabe", HttpStatus.SERVICE_UNAVAILABLE);
        }
        if (repository.findByName(name) == null){
            return new ResponseEntity("There is no such thing.", HttpStatus.SERVICE_UNAVAILABLE);
        }
        Recipe drink = repository.findByName(name);
        if(!ingredientService.canMakeDrink(drink)){
            return new ResponseEntity<>("Not enough ingredients to make " + drink.getName(), HttpStatus.BAD_REQUEST);
        }
        ingredientService.updateIngredientQuantities(drink);
        statisticsService.updateStatistics(drink);
        return new ResponseEntity("Your " + drink.getName() + " is ready!", HttpStatus.OK);
    }

    public ResponseEntity<String> addNewRecipe(Recipe newRecipe) {
        if (isRecipeExists(newRecipe.getIngredients())) {
            return new ResponseEntity<>("Recipe with the same ingredients and quantities already exists.", HttpStatus.BAD_REQUEST);
        }
        if(repository.findByName(newRecipe.getName()) != null){
            return new ResponseEntity<>("Recipe with the same name already exists.", HttpStatus.BAD_REQUEST);
        }
        for (RecipeIngredient recipeIngredient : newRecipe.getIngredients()) {
            Ingredient existingIngredient = ingredientRepository.findByName(recipeIngredient.getIngredient().getName());

            if (existingIngredient == null) {
                return new ResponseEntity<>("Ingredient " + recipeIngredient.getIngredient().getName() + " does not exist.", HttpStatus.BAD_REQUEST);
            }

            recipeIngredient.setIngredient(existingIngredient);
            recipeIngredient.setRecipe(newRecipe);
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

    private boolean areIngredientsEqual(List<RecipeIngredient> ingredients, List<RecipeIngredient> newRecipeIngredients) {
        if (ingredients.size() != newRecipeIngredients.size()) {
            return false;
        }
        for (RecipeIngredient newIngredient : newRecipeIngredients) {
            boolean matchFound = ingredients.stream()
                    .anyMatch(existingIngredient ->
                            existingIngredient.getIngredient().getName().equals(newIngredient.getIngredient().getName()) &&
                                    existingIngredient.getQuantity().equals(newIngredient.getQuantity())
                    );

            if (!matchFound) {
                return false;
            }
        }
        return true;
    }

}
