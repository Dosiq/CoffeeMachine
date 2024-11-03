package kz.solva.task.coffe_machine.controller;

import kz.solva.task.coffe_machine.domain.Recipe;
import kz.solva.task.coffe_machine.service.CoffeeMachineService;

import lombok.AllArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



@RestController
@AllArgsConstructor
public class CoffeeMachineController {

    private final CoffeeMachineService drinkService;

    @GetMapping("/getAll")
    public ResponseEntity<String> getAllAvailableDrinks(){
        return drinkService.getAllDrinks();
    }

    @GetMapping("/getDrink")
    public ResponseEntity<String> makeCoffee(@RequestParam String coffeeName){
        return drinkService.makeCoffee(coffeeName);
    }

    @PostMapping("/addRecipe")
    public ResponseEntity<String> addNewRecipe(@RequestBody Recipe newRecipe){
        return drinkService.addNewRecipe(newRecipe);
    }

}
