package org.example.recipeproduct.recipe;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.recipeproduct.productlisting.ProductListing;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Recipe {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "recipe_gen")
    @SequenceGenerator(name = "recipe_gen", sequenceName = "recipe_seq", allocationSize = 1)
    @Column(name = "r_id")
    private Long recipeId;

    @Column(name = "co_id")
    private Long cookId;

    @Column(name = "name")
    private String recipeName;
    private boolean active;

    @OneToMany(fetch = FetchType.LAZY)
    private List<ProductListing> productListing;

    public Recipe(Long cookId, String recipeName, boolean active) {
        this.cookId = cookId;
        this.recipeName = recipeName;
        this.active = active;
    }
}
