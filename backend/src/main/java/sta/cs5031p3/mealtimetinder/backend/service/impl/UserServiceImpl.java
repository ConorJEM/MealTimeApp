package sta.cs5031p3.mealtimetinder.backend.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sta.cs5031p3.mealtimetinder.backend.model.*;
import sta.cs5031p3.mealtimetinder.backend.repository.UserRepository;
import sta.cs5031p3.mealtimetinder.backend.security.JWTProvider;
import sta.cs5031p3.mealtimetinder.backend.service.UserService;
import sta.cs5031p3.mealtimetinder.backend.model.Meal;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@Slf4j
@Transactional
public class UserServiceImpl implements UserService {

    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Autowired
    private UserRepository userRepository;

    @Override
    public String login(String username, String password, User.Role role, AuthenticationManager authenticationManager) {
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(username, password);
        Authentication authentication = authenticationManager.authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        log.info("Login....... \n username: {} \t password {}", username, password);
        return JWTProvider.generateToken(authentication);
    }

    @Override
    public User getUserById(long id) {
        return userRepository.findById(id).orElseThrow();

    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public List<Meal> getFavourites(Hunter hunter){

        return hunter.getFavouriteMeals();
    }

    @Override
    public List<Meal> getServedMeals(Restaurant restaurant){
        return restaurant.getServedMeals();
    }


    @Override
    public void addToFavourites(Hunter hunter,Meal meal){
        if(hunter.getFavouriteMeals().contains(meal)){
            throw new IllegalArgumentException("Already Favourited!");
        }else{
            hunter.addFavouritedMeal(meal);
        }
    }
    @Override
    public void removeFromFavourites(Hunter hunter,Meal meal){

        hunter.removeFavouritedMeal(meal);
    }

    @Override
    public User getRegisteredHunterByUsername(String username) {
        return userRepository.findUserByUsernameAndRoleAndStatus(username, User.Role.HUNTER, User.Status.REGISTERED).orElseThrow();
    }

    @Override
    public User getRegisteredRestaurantByUsername(String username) {
        return userRepository.findUserByUsernameAndRoleAndStatus(username, User.Role.RESTAURANT, User.Status.REGISTERED).orElseThrow();
    }


    @Override
    public User saveUser(User user) {
        //No such user before: username does not match any registered username in database.
        Optional<User> existingUser = userRepository.findUserByUsernameAndRoleAndStatus(user.getUsername(), user.getRole(), User.Status.REGISTERED);
        if (existingUser.isPresent()) {
            throw new IllegalArgumentException("User already exists");
        }
        //encode the password
        String encodedPassword = passwordEncoder.encode(user.getPassword());

        user.setPassword(encodedPassword);
        return userRepository.save(user);
    }

    @Override
    public User getRegisteredAdminByUsername(String username) {
        return userRepository.findUserByUsernameAndRoleAndStatus(username, User.Role.ADMIN, User.Status.REGISTERED).orElseThrow();
    }

}
