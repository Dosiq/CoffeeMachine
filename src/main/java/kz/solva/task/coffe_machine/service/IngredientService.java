package kz.solva.task.coffe_machine.service;

import kz.solva.task.coffe_machine.DTO.RecipeIngredient;
import kz.solva.task.coffe_machine.domain.Ingredient;
import kz.solva.task.coffe_machine.domain.Recipe;
import kz.solva.task.coffe_machine.repository.Ingredient_repository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class IngredientService {

    private final Ingredient_repository ingredientRepository;

    public ResponseEntity<String> addNewIngredient(Ingredient ingredient){
        if(isIngredientExists(ingredient.getName())){
            return new ResponseEntity<>(ingredient.getName() + " is already exists!", HttpStatus.BAD_REQUEST);
        }
        ingredientRepository.save(ingredient);
        return new ResponseEntity<>(ingredient.getName() + " was added!", HttpStatus.OK);
    }

    public ResponseEntity<String> addIngredient(Ingredient ingredient){
        if(!isIngredientExists(ingredient.getName())){
            return new ResponseEntity<>(ingredient.getName() + " is not exists!", HttpStatus.BAD_REQUEST);
        }
        Ingredient currentIngredient = ingredientRepository.findByName(ingredient.getName());
        currentIngredient.setQuantity(currentIngredient.getQuantity() + ingredient.getQuantity());
        ingredientRepository.save(currentIngredient);
        return new ResponseEntity<>(currentIngredient.getName() + " quantity was changed to " + currentIngredient.getQuantity(), HttpStatus.OK);
    }

    public boolean isIngredientExists(String name){
        return ingredientRepository.existsByName(name);
    }

    @Transactional(readOnly = true)
    public boolean canMakeDrink(Recipe recipe) {
        List<RecipeIngredient> requiredIngredients = recipe.getIngredients();

        for (RecipeIngredient recipeIngredient : requiredIngredients) {
            Ingredient ingredient = recipeIngredient.getIngredient();
            int requiredQuantity = recipeIngredient.getQuantity();

            Ingredient currentIngredient = ingredientRepository.findById(ingredient.getId())
                    .orElseThrow(() -> new RuntimeException("Ingredient not found: " + ingredient.getName()));

            if (currentIngredient.getQuantity() < requiredQuantity) {
                return false;
            }
        }

        return true;
    }
    public void updateIngredientQuantities(Recipe recipe) {
        for (RecipeIngredient recipeIngredient : recipe.getIngredients()) {
            Ingredient ingredient = recipeIngredient.getIngredient();
            int requiredQuantity = recipeIngredient.getQuantity();

            Ingredient currentIngredient = ingredientRepository.findById(ingredient.getId())
                    .orElseThrow(() -> new RuntimeException("Ingredient not found: " + ingredient.getName()));

            currentIngredient.setQuantity(currentIngredient.getQuantity() - requiredQuantity);
            ingredientRepository.save(currentIngredient);
        }
    }

}
