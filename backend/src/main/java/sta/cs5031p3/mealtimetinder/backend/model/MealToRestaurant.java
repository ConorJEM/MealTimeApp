package sta.cs5031p3.mealtimetinder.backend.model;

import lombok.Getter;

import java.io.Serializable;

/**
 * Model for add meal to restu
 */
@Getter
public class MealToRestaurant implements Serializable {
    private Long mealId;
    private long restaurantId;
}
