package kz.solva.task.coffe_machine.controller;

import kz.solva.task.coffe_machine.domain.Recipe;
import kz.solva.task.coffe_machine.service.CoffeMachineService;

import lombok.AllArgsConstructor;

import org.springframework.web.bind.annotation.*;


@RestController
@AllArgsConstructor
public class Machine_controller {

    private final CoffeMachineService drinkService;

    @GetMapping("/getAll")
    public List<Drink> getAllAvailableDrinks(){
        return drinkService.getAllDrinks();
    }

    @GetMapping("/getDrink")
    public Drink makeCoffee(@RequestParam String coffeeName){
        return drinkService.makeCoffee(coffeeName);
    }

    @PostMapping("/addRecipe")
    public Recipe addNewRecipe(@RequestBody Recipe newRecipe){
        drinkService.addnewRecipe(newRecipe);
    }





}
