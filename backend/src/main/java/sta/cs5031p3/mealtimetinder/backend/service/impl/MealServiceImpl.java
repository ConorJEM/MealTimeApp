package sta.cs5031p3.mealtimetinder.backend.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import sta.cs5031p3.mealtimetinder.backend.model.Meal;
import sta.cs5031p3.mealtimetinder.backend.model.Recipe;
import sta.cs5031p3.mealtimetinder.backend.model.Restaurant;
import sta.cs5031p3.mealtimetinder.backend.repository.MealRepository;
import sta.cs5031p3.mealtimetinder.backend.repository.RecipeRepository;
import sta.cs5031p3.mealtimetinder.backend.service.ImageFileService;
import sta.cs5031p3.mealtimetinder.backend.service.MealService;

import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class MealServiceImpl implements MealService {

    @Autowired
    private MealRepository mealRepository;

    @Autowired
    private RecipeRepository recipeRepository;

    private ImageFileService fileService = new ImageFileServiceImpl();

    @Override
    public List<Meal> getRandom5Meals() {
        //Get a random page number
        int totalPage = (int) (mealRepository.count() / 5);
        Random random = new Random();
        int pageNum = totalPage == 0 ? 0 : random.nextInt(totalPage);
        return mealRepository.findAll(PageRequest.of(pageNum, 5)).toList();
    }



    @Override
    public Meal saveMeal(Meal meal) {
        if (meal == null || meal.getName() == null) {
            throw new IllegalArgumentException("Can't save an empty meal");
        }
        // Optional<Meal> existingMeal = userRepository.findMealByName
        Optional<Meal> existingMeal = mealRepository.findMealByName(meal.getName());
        if (existingMeal.isPresent()) {
            throw new IllegalArgumentException("Meal with this name already exists");
        }
        if (meal.getImagePath() == null) {
            meal.setImagePath(ImageFileService.DEFAULT_MEAL_PATH);
        }
        //validate the path if it exists
        /*if (!fileService.validateImagePath(meal.getImagePath())) {
            throw new IllegalArgumentException("Invalid image path");
        }*/
        //save receipts to meal
        if (meal.getRecipes() != null) {
            for (Recipe recipe: meal.getRecipes()) {
                recipe.setMeal(meal);
            }
        }

        Meal savedMeal = mealRepository.save(meal);
        if (meal.getRecipes() != null && meal.getRecipes().size() > 0) {
            recipeRepository.saveAll(meal.getRecipes());
        }
        return savedMeal;
    }

    @Override
    public Recipe saveRecipe(Recipe recipe){
        //validate recipe
        if (recipe == null || recipe.getName() == null) {
            throw new IllegalArgumentException("Can't save an empty meal");
        }

        //check if the meal exists
        if (mealRepository.findById(recipe.getMeal().getId()).isEmpty()) {
            throw new IllegalArgumentException("No such meal to add");
        }
        return recipeRepository.save(recipe);
    }


    @Override
    public List<Recipe> getAllRecipesForMeal(Meal meal) {
        return recipeRepository.findByMeal(meal);
    }

    @Override
    public List<Restaurant> getAllRestaurantForMeal(Meal meal) {
        return meal.getRestaurants();
    }

    @Override
    public Meal getMealById(Long id){

        return mealRepository.findById(id).orElseThrow();
    }

    @Override
    public List<Meal> getAllMeals(){
        return mealRepository.findAll();
    }

    @Override
    public List<Meal> getMealsForRestaurant(Restaurant restaurant) {
        return restaurant.getServedMeals();
    }

    @Override
    public void addMealToRestaurantImpl(Restaurant restaurant, Meal meal) {
        restaurant.addToServedMeal(meal);
    }

    @Override
    public void removeMealFromRestaurantImpl(Restaurant restaurant, Meal meal){
        restaurant.removeServedMeal(meal);
    }

    @Override
    public void addRestaurantToMealImpl(Restaurant restaurant, Meal meal){
        meal.addRestaurantToMeal(restaurant);
    }

    @Override
    public void removeRestaurantFromMealImpl(Restaurant restaurant, Meal meal){
        meal.removeRestaurantToMeal(restaurant);
    }

}
