package sta.cs5031p3.mealtimetinder.backend.api.hunter;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import sta.cs5031p3.mealtimetinder.backend.model.*;

import sta.cs5031p3.mealtimetinder.backend.service.MealService;
import sta.cs5031p3.mealtimetinder.backend.service.UserService;
import org.apache.commons.io.IOUtils;
import java.io.InputStream;
import java.util.List;

/**
 * Rest Controller for the hunter interface
 * Author: 160014528 CJEM
 */


@RestController
@RequestMapping("/hunter")
@OpenAPIDefinition(info = @Info(title = "Hunter Account API",
        description = "This documents Restful APIs for Hunter's Account",
        contact = @Contact(name = "CS5031 P3 Group B",
                url = "https://gitlab.cs.st-andrews.ac.uk/cs5031groupb/project-code")
))
@Slf4j
public class HunterAPI {

    @Qualifier("hunterAuthManager")
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    @Autowired
    private MealService mealService;


    @PostMapping("/login")
    @Operation(summary = "Hunter Login",
            description = "Hunter submit login form to log in")
    public ResponseEntity<JWTResponse> login(@RequestBody UserLoginForm loginForm) {
        String username= loginForm.getUsername();
        String password= loginForm.getPassword();
        String accessToken = userService.login(username,password, User.Role.HUNTER, authenticationManager);
        log.info("successful sign in");
        return ResponseEntity.ok(new JWTResponse(accessToken));
    }

    @GetMapping("/profile")
    @Operation(security = {
            @SecurityRequirement(name = "HunterBearerAuth")},
            summary = "Get Hunter Profile detail",
            description = "Hunter request profile information")
    public @ResponseBody
    User getProfile() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return userService.getRegisteredHunterByUsername(username);
    }

    @GetMapping("/meals")
    public List<Meal> getMeal() {
        log.info("meals");
        return mealService.getRandom5Meals();
    }

    @PostMapping("/getRecipesForMeal/{id}")
    @Operation(security = {
            @SecurityRequirement(name = "HunterBearerAuth")
    })
    public List<Recipe> getRecipesFromMeal(
            @PathVariable("id") long id
    ) {
        try {
            Meal meal = mealService.getMealById(id);
            return mealService.getAllRecipesForMeal(meal);
        } catch (Exception e) {
            return null;
        }
    }

    @GetMapping(value="/getImageForMeal/{id}",
                produces= MediaType.IMAGE_JPEG_VALUE)
    @Operation(security = {
            @SecurityRequirement(name = "HunterBearerAuth")
    })
    public ResponseEntity<byte[]> getImageForMeal(
            @PathVariable("id") long id
    ) {
        try {
            Meal meal = mealService.getMealById(id);
            String path = meal.getImagePath();
            log.info("getting image at path: " + path);
            InputStream in = getClass().getResourceAsStream("chicken.jpg");


            final HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_PNG);

            return new ResponseEntity<byte[]>(IOUtils.toByteArray(in),headers, HttpStatus.CREATED);
        } catch (Exception e) {
            return null;
        }
    }





    //@PostMapping("/AddRecipeForMeal/{id}/{description}/{is_default}/{name}")

    @PostMapping("/AddRecipeForMeal/{id}/{description}/{name}")
    @Operation(security = {
            @SecurityRequirement(name = "HunterBearerAuth")
    })
    public void addRecipeForMeal(
            @PathVariable("id") long id,
            @PathVariable("description") String description,
            @PathVariable("name") String name
            //@PathVariable("is_default") Boolean defaulted,
            //@PathVariable("name") String name

    ) {
        try {
            Meal meal = mealService.getMealById(id);
            //mealService.saveRecipe(new Recipe(null,name,description,defaulted,meal));
            mealService.saveRecipe(new Recipe(null,name,description,false,meal));
            //check for invalid recipe (no meal)
        } catch (Exception e) {

        }
    }

    @PostMapping("/getSpecificMeal/{id}")
    @Operation(security = {
            @SecurityRequirement(name = "HunterBearerAuth")
    })
    public Meal getMeal(
            @PathVariable("id") long id
    ) {
        try {
            return mealService.getMealById(id);
        } catch (Exception e) {
            return null;
        }
    }

    @PostMapping("/getRestaurantsForMeal/{id}")
    @Operation(security = {
            @SecurityRequirement(name = "HunterBearerAuth")
    })
    public List<Restaurant> getRestaurantFromMeal(
            @PathVariable("id") long id
    ){
        try {
            Meal meal = mealService.getMealById(id);
            return mealService.getAllRestaurantForMeal(meal);
        } catch (Exception e){
            return null;
        }
    }

    @PostMapping("/register")
    public ResponseEntity<JWTResponse> addAccount(@RequestBody HunterCreation creation) {
        log.info("register "+creation.getUsername()+" "+creation.getPassword());
        try {
            userService.saveUser(new Hunter(creation.getUsername(), creation.getPassword(), User.Status.REGISTERED
                    , creation.getAddress(), creation.getPostcode(), null));

            String accessToken = userService.login(creation.getUsername(),creation.getPassword(), User.Role.HUNTER, authenticationManager);
            log.info("successful registration");
            return ResponseEntity.ok(new JWTResponse(accessToken));

        } catch (Exception e){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,e.getMessage());
        }
    }

    @GetMapping("/viewFavourites")
    @Operation(security = {
            @SecurityRequirement(name = "HunterBearerAuth")
    })
    public List<Meal> viewFavourites() {
        try {
            String username = SecurityContextHolder.getContext().getAuthentication().getName();
            Hunter h = (Hunter) userService.getRegisteredHunterByUsername(username);
            return userService.getFavourites(h);
        } catch (Exception e){
            return null;
        }
    }

    @PostMapping("/addToFavourites/{mealId}")
    @Operation(security = {
            @SecurityRequirement(name = "HunterBearerAuth")
    })
    public Boolean addToFavourites(
            @PathVariable ("mealId") long mealId
    ) {
        try {
            String username = SecurityContextHolder.getContext().getAuthentication().getName();
            Hunter h = (Hunter) userService.getRegisteredHunterByUsername(username);

            Meal m = mealService.getMealById(mealId);
            userService.addToFavourites(h,m);
            return true;
        } catch (Exception e){
            return false;
        }
    }

    @PostMapping("/removeFromFavourites/{mealId}")
    @Operation(security = {
            @SecurityRequirement(name = "HunterBearerAuth")
    })
    public Boolean removeFromFavourites(
            @PathVariable ("mealId") long mealId
    ) {
        try {
            String username = SecurityContextHolder.getContext().getAuthentication().getName();
            Hunter h = (Hunter) userService.getRegisteredHunterByUsername(username);
            Meal m = mealService.getMealById(mealId);
            userService.removeFromFavourites(h,m);
            return true;
        } catch (Exception e){
            return false;
        }
    }


}
