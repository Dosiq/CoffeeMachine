package kz.solva.task.coffe_machine.controller;

import kz.solva.task.coffe_machine.domain.Ingredient;
import kz.solva.task.coffe_machine.service.IngredientService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class IngredientController {

    private final IngredientService ingredientService;

    @PostMapping("/addNewIngredient")
    public ResponseEntity<String> addNewIngredient(@RequestBody Ingredient ingredient){
        return ingredientService.addNewIngredient(ingredient);
    }

    @PostMapping("/addIngredient")
    public ResponseEntity<String> addIngredient(@RequestBody Ingredient ingredient){
        return ingredientService.addIngredient(ingredient);
    }
}
