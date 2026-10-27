package org.school.exam.recipe;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Getter
@Setter
@NoArgsConstructor
public class Recipe {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "recipe_gen")
    @SequenceGenerator(name = "recipe_gen", sequenceName = "recipe_seq", allocationSize = 1)
    @Column(name = "recipe_id")
    private int recipeId;

    @Column(name = "chef_id")
    private int cookId;

    @Column(name = "name")
    private String recipeName;

    private boolean active;
    private int pictureId;
    private int instanceOf;
    @Enumerated(EnumType.STRING)
    @Column(name = "category")
    private FoodCategory foodCategory;
    private boolean gluten;
    private boolean crustaceans;
    private boolean egg;
    private boolean fish;
    private boolean peanuts;
    private boolean soybeans;
    private boolean milk;
    private boolean nuts;
    private boolean celery;
    private boolean mustard;
    private boolean sesame;
    private boolean sulfites;
    private boolean lupin;
    private boolean molluscs;

    public Recipe(int cookId, String recipeName, boolean active, int pictureId,
                  int instanceOf, FoodCategory foodCategory, boolean gluten,
                  boolean crustaceans, boolean egg, boolean fish, boolean peanuts,
                  boolean soybeans, boolean milk, boolean nuts, boolean celery,
                  boolean mustard, boolean sesame, boolean sulfites, boolean lupin, boolean molluscs)
    {
        this.cookId = cookId;
        this.recipeName = recipeName;
        this.active = active;
        this.pictureId = pictureId;
        this.instanceOf = instanceOf;
        this.foodCategory = foodCategory;
        this.gluten = gluten;
        this.crustaceans = crustaceans;
        this.egg = egg;
        this.fish = fish;
        this.peanuts = peanuts;
        this.soybeans = soybeans;
        this.milk = milk;
        this.nuts = nuts;
        this.celery = celery;
        this.mustard = mustard;
        this.sesame = sesame;
        this.sulfites = sulfites;
        this.lupin = lupin;
        this.molluscs = molluscs;
    }
}
