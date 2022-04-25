package sta.cs5031p3.mealtimetinder.backend.model;

import lombok.Getter;

import java.io.Serializable;
import java.util.List;

/**
 * Model for creating meal.
 * @author 200011181
 */
@Getter
public class MealCreation implements Serializable {
    private String name;

    private String imagePath;

    private List<Recipe> recipes;
}
