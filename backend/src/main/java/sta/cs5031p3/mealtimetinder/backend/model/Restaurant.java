package sta.cs5031p3.mealtimetinder.backend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

/**
 * The Admin model is for administrator, which inherits features from the general user.
 * It refers to the admin table in database.
 */
@Entity
@Getter
@Setter
@Table(name = "restaurant")
@NoArgsConstructor
public class Restaurant extends User {

    public Restaurant(String username,String password,Status status,String address,String postcode,String description,List<Meal> meals){
        super(null, username, password, status,Role.RESTAURANT, address, postcode);
        this.description = description;
        this.servedMeals = meals;
    }

    private String description;


    @ManyToMany
    @JoinTable(name = "restaurant_serves",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "meal_id"))
    @JsonIgnore
    private List<Meal> servedMeals;

    public void addToServedMeal(Meal meal){
        this.servedMeals.add(meal);
    }

    public void removeServedMeal(Meal meal){
        this.servedMeals.remove(meal);
    }
}
