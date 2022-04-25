package sta.cs5031p3.mealtimetinder.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import sta.cs5031p3.mealtimetinder.backend.model.Meal;

import java.util.Optional;

/**
 * Repository for all types of user information.
 * Extend CrudRepository allows it to use built in implementation of Spring Data JPA
 * with right method names or customised query.
 * @author 200011181
 */
@Repository
public interface MealRepository extends JpaRepository<Meal, Long> {

    Optional<Meal> findMealByName(String name);

    @Override
    Optional<Meal> findById(Long aLong);



    // Optional<Meal> findMealbyName()
}