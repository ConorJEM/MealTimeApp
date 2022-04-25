package sta.cs5031p3.mealtimetinder.backend.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import sta.cs5031p3.mealtimetinder.backend.model.*;
import sta.cs5031p3.mealtimetinder.backend.repository.MealRepository;
import sta.cs5031p3.mealtimetinder.backend.repository.RecipeRepository;
import sta.cs5031p3.mealtimetinder.backend.service.impl.MealServiceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
public class MealServiceTests {

    @Mock
    private RecipeRepository recipeRepository;

    @Mock
    private MealRepository mealRepository;

    @InjectMocks
    private MealService mealService = new MealServiceImpl();

    @Mock
    private ImageFileService imageFileService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        when(imageFileService.validateImagePath(ImageFileService.DEFAULT_MEAL_PATH)).thenReturn(true);
        when(imageFileService.validateImagePath("/meals/burger.jpg")).thenReturn(true);
    }


    @Test
    public void addMealWithoutRecipesTest() {
        //add test meal
        Meal test = new Meal(null, "testmeal", null, null, null, null);
        //mock repositories
        when(mealRepository.findMealByName("testmeal")).thenReturn(Optional.empty());
        when(mealRepository.save(test)).thenReturn(test);
        //test the service
        Meal meal = mealService.saveMeal(test);
        assertEquals(test.getName(), meal.getName());
        assertNull(meal.getRecipes());
    }

    @Test
    public void addNewMealWithValidImagePathAndNameRecipesTest() {
        //add test recipes
        List<Recipe> recipes = new ArrayList<>();
        Recipe r1 = new Recipe(null, "r1", "ds", true, null);
        recipes.add(r1);
        Recipe r2 = new Recipe(null, "r2", "ds", false, null);
        recipes.add(r2);
        //add test meal
        Meal test = new Meal(null, "testmeal", "/meals/burger.jpg", recipes, null, null);
        //mock repositories
        when(mealRepository.findMealByName("testmeal")).thenReturn(Optional.empty());
        when(mealRepository.save(test)).thenReturn(test);
        when(recipeRepository.saveAll(recipes)).thenReturn(recipes);
        //test the service
        mealService.saveMeal(test);
        Meal meal = mealService.saveMeal(test);
        assertEquals(test.getName(), meal.getName());
        assertEquals(test.getImagePath(), meal.getImagePath());
        assertEquals(r1, meal.getRecipes().get(0));
        assertEquals(meal.getRecipes().get(0).getMeal(), meal);
    }

    @Test
    public void addNewMealWithoutNameThrowExceptionTest() {
        //add test meal
        Meal test = new Meal(null, null , "/meals/burger.jpg", null, null, null);
        //test the service
        assertThrows(IllegalArgumentException.class, () -> mealService.saveMeal(test));
    }


    @Test
    public void addNewMealWithoutImagePathWillGetDefaultPathTest() {
        //Fixed, but Junit will not recognise @Value from Spring
        //add test meal
        Meal test = new Meal(null, "test" , null, null, null, null);
        //mock
        when(mealRepository.findMealByName("test")).thenReturn(Optional.empty());
        when(mealRepository.save(test)).thenReturn(test);
        //test the service
        Meal meal = mealService.saveMeal(test);
        assertEquals(test.getName(), meal.getName());
        assertEquals(ImageFileService.DEFAULT_MEAL_PATH, meal.getImagePath());
    }

    @Test
    public void addMealWithInValidImagePathThrowExceptionTest() {
        //add test meal
        Meal test = new Meal(null, "testmeal", "wrongPath", null, null, null);
        //mock repositories
        when(mealRepository.findMealByName("testmeal")).thenReturn(Optional.empty());
        //test the service
        assertThrows(IllegalArgumentException.class, () -> mealService.saveMeal(test));
    }

    @Test
    public void addOneRecipeToExistingMealTest() {
        Meal testMeal = new Meal((long)1, "testmeal", "meals/burger.jpg", null, null, null);
        Recipe recipe = new Recipe(null, "Vegetable Pakora", "Heat up the oil in a karahi or wok to a medium heat", false, testMeal);
        //mock repositories
        when(mealRepository.findById(testMeal.getId())).thenReturn(Optional.of(testMeal));
        when(recipeRepository.save(recipe)).thenReturn(recipe);
        //test
        Recipe savedRecipe = mealService.saveRecipe(recipe);
       assertEquals(savedRecipe.getMeal(), testMeal);
    }

    @Test
    public void addOneRecipeToNonExistingMealTest() {
        Meal testMeal = new Meal((long)1, "testmeal", "meals/burger.jpg", null, null, null);
        Recipe recipe = new Recipe(null, "Vegetable Pakora", "Heat up the oil in a karahi or wok to a medium heat", false, testMeal);
        //mock repositories
        when(mealRepository.findById(testMeal.getId())).thenReturn(Optional.empty());
        //test
        assertThrows(IllegalArgumentException.class, () -> mealService.saveRecipe(recipe));
    }

    @Test
    public void addTwoRecipesToExistingMealTest() {
        Meal testMeal = new Meal((long)1, "testmeal", "meals/burger.jpg", null, null, null);
        Recipe recipe = new Recipe(null, "Vegetable Pakora", "Heat up the oil in a karahi or wok to a medium heat", true, testMeal);
        Recipe recipe2 = new Recipe(null, "Vegetable Pakora", "Heat up the oil in a karahi or wok to a medium heat", false, testMeal);
        //mock repositories
        List<Recipe> recipes = new ArrayList<>();
        recipes.add(recipe);
        recipes.add(recipe2);
        Meal updatedMeal = new Meal((long)1, "testmeal", "meals/burger.jpg", recipes, null, null);
        when(mealRepository.findById(testMeal.getId())).thenReturn(Optional.of(testMeal)).thenReturn(Optional.of(testMeal)).thenReturn(Optional.of(updatedMeal));
        when(recipeRepository.save(recipe)).thenReturn(recipe);
        when(recipeRepository.save(recipe2)).thenReturn(recipe2);
        //test
        mealService.saveRecipe(recipe);
        mealService.saveRecipe(recipe2);
        assertEquals(2, mealService.getMealById(testMeal.getId()).getRecipes().size());
    }

    @Test
    public void addRepeatMealThrowExceptionTest() {

        Meal test = new Meal(null, "Burger Test", "meals/burger.jpg", null, null, null);
        Meal test2 = new Meal(null, "Burger Test", "meals/burger.jpg", null, null, null);
        when(mealRepository.findMealByName(test.getName())).thenReturn(Optional.of(test));
        assertThrows(IllegalArgumentException.class, () -> mealService.saveMeal(test2));
    }

}
