package sta.cs5031p3.mealtimetinder.backend.model;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * Model for create recipe for specific meal.
 * @author 200011181
 */
@Getter
public class RecipeCreation implements Serializable {
    private Long meal_id;

    private String name;

    private String description;

    private boolean isDefault;
}
