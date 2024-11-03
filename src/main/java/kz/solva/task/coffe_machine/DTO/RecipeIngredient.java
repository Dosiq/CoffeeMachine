package kz.solva.task.coffe_machine.DTO;

import jakarta.persistence.*;
import kz.solva.task.coffe_machine.domain.Ingredient;
import kz.solva.task.coffe_machine.domain.Recipe;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class RecipeIngredient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "recipe_id", nullable = false)
    private Recipe recipe;
    @ManyToOne
    @JoinColumn(name = "ingredient_id", nullable = false)
    private Ingredient ingredient;
    @Column(nullable = false)
    private Integer quantity;
}
