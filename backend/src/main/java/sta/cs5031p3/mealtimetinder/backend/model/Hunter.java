package sta.cs5031p3.mealtimetinder.backend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

/**
 * The Hunter model is for administrator, which inherits features from the general user.
 * It refers to the hunter table in database.
 * @author 200011181
 */
@Entity
@Getter
@Setter
@Table(name = "hunter")
@NoArgsConstructor
@AllArgsConstructor
public class Hunter extends User {

    public Hunter(String username,String password,Status status,String address,String postcode,List<Meal> meals){
        super(null, username,password,status,Role.HUNTER ,address,postcode);
        this.favouriteMeals=meals;
    }

    @JsonIgnore
    @ManyToMany
    @JoinTable(name = "favourite_meals",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "meal_id"))
    private List<Meal> favouriteMeals;

    public void addFavouritedMeal(Meal meal){
        favouriteMeals.add(meal);
    }

    public void removeFavouritedMeal(Meal meal){

        if(favouriteMeals.contains(meal)){
            favouriteMeals.remove(meal);
        }

    }

}
