package kz.solva.task.coffe_machine.service;

import kz.solva.task.coffe_machine.domain.Ingredient;
import kz.solva.task.coffe_machine.repository.Ingredient_repository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class IngredientService {

    private final Ingredient_repository ingredientRepository;

    public boolean isIngredientsAvailable(List<Ingredient> ingredients){

    }
}
