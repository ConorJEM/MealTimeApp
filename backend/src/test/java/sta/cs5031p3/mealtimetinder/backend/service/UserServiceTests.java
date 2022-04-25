package sta.cs5031p3.mealtimetinder.backend.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Example;
import sta.cs5031p3.mealtimetinder.backend.model.*;
import sta.cs5031p3.mealtimetinder.backend.repository.MealRepository;
import sta.cs5031p3.mealtimetinder.backend.repository.UserRepository;
import sta.cs5031p3.mealtimetinder.backend.service.impl.MealServiceImpl;
import sta.cs5031p3.mealtimetinder.backend.service.impl.UserServiceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

public class UserServiceTests {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserRepository mealRepository;

    @InjectMocks
    private UserService userService = new UserServiceImpl();

    @InjectMocks
    private MealService mealService = new MealServiceImpl();

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void getAllUserTest() {
        List<User> users = new ArrayList<>();
        users.add(new Admin("conor", "120",
                User.Status.REGISTERED, "St Andrews", "KY16"));
        users.add(new Admin("cono", "120",
                User.Status.REGISTERED, "St Andrews", "KY16"));

        when(userRepository.findAll()).thenReturn(users);

        assertEquals(2, userRepository.findAll().size());

    }
    @Test
    public void checkGetFavourites() {

        ArrayList<Meal> meals = new ArrayList<Meal>();
        Meal meal = new Meal(null, "test meal", "imagePath", null, null, null);
        meals.add(meal);
        Hunter testhunt = new Hunter("Michael", "1204578608",
                User.Status.REGISTERED, "St Andrews", "G64 126", meals);
        when(userRepository.save(testhunt)).thenReturn(testhunt);

        assertEquals(userRepository.save(testhunt).getFavouriteMeals().size(), 1);

        Meal meal2 = new Meal(null, "test meal 2", "imagePath 2", null, null, null);
        userService.addToFavourites(testhunt, meal2);

        Meal meal3 = new Meal(null, "test meal 3", "imagePath 2", null, null, null);
        userService.addToFavourites(testhunt, meal3);


        assertEquals(userRepository.save(testhunt).getFavouriteMeals().size(), 3);
    }


    @Test
    public void addToFavourites(){

        ArrayList<Meal> meals= new ArrayList<Meal>();
        Meal meal = new Meal(null, "test meal","imagePath",null, null, null);
        meals.add(meal);
        Hunter testhunt = new Hunter("Michael","1204578608",
                User.Status.REGISTERED, "St Andrews","G64 126",meals);
        when(userRepository.save(testhunt)).thenReturn(testhunt);

        assertEquals(userRepository.save(testhunt).getFavouriteMeals().size(),1);

        Meal meal2 = new Meal(null, "test meal 2","imagePath 2",null, null, null);
        userService.addToFavourites(testhunt,meal2);

        assertEquals(userRepository.save(testhunt).getFavouriteMeals().size(),2);

    }

    @Test
    public void cantAddDuplicateFavourites(){

        ArrayList<Meal> meals= new ArrayList<Meal>();
        Meal meal = new Meal(null, "test meal","imagePath",null, null, null);
        meals.add(meal);
        Hunter testhunt = new Hunter("Michael","1204578608",
                User.Status.REGISTERED, "St Andrews","G64 126",meals);
        when(userRepository.save(testhunt)).thenReturn(testhunt);

        assertEquals(userRepository.save(testhunt).getFavouriteMeals().size(),1);

        assertThrows(IllegalArgumentException.class,()-> userService.addToFavourites(testhunt,meal));


        assertEquals(testhunt.getFavouriteMeals().size(),1);

    }



    @Test
    public void removeFromFavourites(){

        ArrayList<Meal> meals= new ArrayList<Meal>();
        Meal meal = new Meal(null, "test meal","imagePath",null, null, null);
        meals.add(meal);
        Hunter testhunt = new Hunter("Michael","1204578608",
                User.Status.REGISTERED, "St Andrews","G64 126",meals);
        when(userRepository.save(testhunt)).thenReturn(testhunt);

        assertEquals(userRepository.save(testhunt).getFavouriteMeals().size(),1);

        Meal meal2 = new Meal(null, "test meal 2","imagePath 2",null, null, null);
        userService.addToFavourites(testhunt,meal2);
        userService.removeFromFavourites(testhunt,meal2);

        assertEquals(userRepository.save(testhunt).getFavouriteMeals().size(),1);

    }



    @Test
    public void getAllAdminUserTest2() {

        List<User> users = new ArrayList<>();
        users.add(new Admin("conor", "120",
                User.Status.REGISTERED, "St Andrews", "KY16"));
        users.add(new Admin("cono", "120",
                User.Status.REGISTERED, "St Andrews", "KY16"));
        users.add(new Restaurant("Ziggys", "123",
                User.Status.REGISTERED, "St Andrews", "KY16", "American Food", null));

        //mocked removal of restaurant
        users.remove(users.size()-1);

        when(userRepository.getAllByRole(User.Role.ADMIN)).thenReturn(users);
        assertEquals(userRepository.getAllByRole(User.Role.ADMIN).size(),2);
    }
    @Test
    public void duplicateUserThrowExceptionTest(){
        User testuser = new Admin("conor", "120",
                User.Status.REGISTERED, "St Andrews", "KY16");
        User testuser2 = new Admin("conor", "120",
                User.Status.REGISTERED, "St Andrews", "KY16");
        userService.saveUser(testuser);
        when(userRepository.findUserByUsernameAndRoleAndStatus(testuser.getUsername(),testuser.getRole(),testuser.getStatus())).thenReturn(Optional.of(testuser));
        when(userRepository.save(testuser2)).thenReturn(testuser2);
        assertThrows(IllegalArgumentException.class,() -> userService.saveUser(testuser2));
    }

    @Test
    public void addNewUserWithoutNameThrowExceptionTest(){}
}

