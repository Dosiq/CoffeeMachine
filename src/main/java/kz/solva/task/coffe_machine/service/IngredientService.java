package kz.solva.task.coffe_machine.service;

import kz.solva.task.coffe_machine.DTO.RecipeIngredient;
import kz.solva.task.coffe_machine.domain.Ingredient;
import kz.solva.task.coffe_machine.domain.Recipe;
import kz.solva.task.coffe_machine.repository.Ingredient_repository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class IngredientService {

    private final Ingredient_repository ingredientRepository;

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
