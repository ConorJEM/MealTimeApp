package sta.cs5031p3.mealtimetinder.backend;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.security.SecuritySchemes;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import sta.cs5031p3.mealtimetinder.backend.model.*;
import sta.cs5031p3.mealtimetinder.backend.service.MealService;
import sta.cs5031p3.mealtimetinder.backend.service.UserService;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SecuritySchemes(value = {
        @SecurityScheme(
                name = "AdminBearerAuth",
                type = SecuritySchemeType.HTTP,
                bearerFormat = "JWT",
                scheme = "bearer"
        ),
        @SecurityScheme(
                name = "HunterBearerAuth",
                type = SecuritySchemeType.HTTP,
                bearerFormat = "JWT",
                scheme = "bearer"
        ),
        @SecurityScheme(
                name = "RestaurantBearerAuth",
                type = SecuritySchemeType.HTTP,
                bearerFormat = "JWT",
                scheme = "bearer"
        )
})
@SpringBootApplication
public class BackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(BackendApplication.class, args);

    }

    @Bean
    BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    /**
     * Initialise data in database.
     * @param userService
     * @return
     */
    @Transactional
    @Bean
    CommandLineRunner run(UserService userService, MealService mealService) {
        return args -> {
            try {

                Meal burger = new Meal(null,"Burger","https://images.pexels.com/photos/1639557/pexels-photo-1639557.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1",null,null,null);


                Meal chicken = new Meal(null,"Grilled Chicken","https://images.pexels.com/photos/2338407/pexels-photo-2338407.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1",null,null,null);
                mealService.saveMeal(chicken);
                Meal peppizza = new Meal(null,"Pepperoni Pizza","https://images.pexels.com/photos/4109111/pexels-photo-4109111.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1",null,null,null);
                mealService.saveMeal(peppizza);
                List<Meal> meals = Arrays.asList(burger,chicken);
                Recipe recipe = new Recipe(null,"Cheeseburger recipe","Bacon, Meat, Beef,Tomato, Cheese",false,burger);
                mealService.saveMeal(burger);

                mealService.saveRecipe(recipe);

                Hunter conor = new Hunter("Conor test","1204578606",
                        User.Status.REGISTERED, "St Andrews","G64 128",meals);
                Hunter damon = new Hunter("damon","1204578616",
                        User.Status.REGISTERED, "St Andrews","G64 128",meals);


                Restaurant paesano = new Restaurant("damon","1204578616",
                        User.Status.REGISTERED,"Glasgow","G64 123","Italian Food",meals);


                List<Hunter> hunters = Arrays.asList(conor,damon);
                userService.saveUser(admin);
                userService.saveUser(conor);
                userService.saveUser(damon);
                userService.saveUser(paesano);





            } catch (Exception e) {
                e.getStackTrace();
            }
        };
    }

}
