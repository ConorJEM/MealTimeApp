package sta.cs5031p3.mealtimetinder.backend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Table(name = "meal")
@Getter

@NoArgsConstructor
@AllArgsConstructor
public class Meal {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    @NotNull
    String name;

    @Setter
    String imagePath;

    @Setter
    @OneToMany(mappedBy = "meal")
    List<Recipe> recipes;

    @JsonIgnore
    @ManyToMany(mappedBy ="servedMeals")
    List<Restaurant> restaurants;

    @JsonIgnore
    @ManyToMany(mappedBy ="favouriteMeals")
    List<Hunter> likes;

    public void addRecipe(Recipe recipe){
        this.recipes.add(recipe);
    }

    public void addRestaurantToMeal(Restaurant restaurant){
        this.restaurants.add(restaurant);
    }

    public void removeRestaurantToMeal(Restaurant restaurant){
        this.restaurants.remove(restaurant);
    }
}
