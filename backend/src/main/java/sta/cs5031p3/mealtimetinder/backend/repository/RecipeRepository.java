package sta.cs5031p3.mealtimetinder.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import sta.cs5031p3.mealtimetinder.backend.model.Meal;
import sta.cs5031p3.mealtimetinder.backend.model.Recipe;
import sta.cs5031p3.mealtimetinder.backend.model.User;

import java.util.List;
import java.util.Optional;

/**
 * Repository for all types of user information.
 * Extend CrudRepository allows it to use built in implementation of Spring Data JPA
 * with right method names or customised query.
 * @author 200011181
 */
@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Long> {

    Optional<Recipe> findRecipeByName(String name);

    List<Recipe> findByMeal(Meal meal);


    // Optional<Meal> findMealbyName()
}
