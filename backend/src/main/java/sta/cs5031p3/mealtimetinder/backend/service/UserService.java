package sta.cs5031p3.mealtimetinder.backend.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Service;
import sta.cs5031p3.mealtimetinder.backend.model.*;

import javax.management.relation.Role;
import java.util.List;
@Service
public interface UserService {
    /**
     * Different role of User login with username and password.
     * @param loginForm includes username and password
     * @param role
     * @param authenticationManager manage the authentication logic, can get from individual security configs.
     * @return a String type access token.
     */
    String login(String username, String password, User.Role role, AuthenticationManager authenticationManager);

    User getUserById(long id);

    List<User> getAllUsers();

    User getRegisteredHunterByUsername(String username);

    User saveUser(User user);

    User getRegisteredAdminByUsername(String username);

    List<Meal> getFavourites(Hunter user);

    void addToFavourites(Hunter hunter,Meal meal);

    void removeFromFavourites(Hunter hunter,Meal meal);

    User getRegisteredRestaurantByUsername(String username);

    public List<Meal> getServedMeals(Restaurant restaurant);

 }
