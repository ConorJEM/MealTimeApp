# Check out the finished project here: https://mealtimefront.azurewebsites.net/

Project was developed with a Spring Boot backend, mySQL database and react.js front end.

The application helps users help decide what they want to eat. Once logged in, the user can swipe through different meals, swiping right on meals to add them to their favourites. Once added to favourites, the user can then view recipes for that meal OR restaurants that can serve it for them. 

Home Page:
![image](https://user-images.githubusercontent.com/75184002/163822951-e8e062a1-4494-415a-9785-cf29c26b540d.png)

Dashboard:
![image](https://user-images.githubusercontent.com/75184002/163823325-46d62947-1de3-4d96-b250-f4974af1da15.png)

The project implements Spring Security. All calls are authenticated through an auth token that is created on account registration/log in. 
![image](https://user-images.githubusercontent.com/75184002/163824181-1b493ae9-06f8-462f-9b82-15356e642a95.png)


Restaurant users have their own API where they can view the entire list of meals, and add meals to their menu for users to see.

The backend has implementations for admin, restaurant and user (hunter) REST services. This can be viewed at https://mealtimebackend.azurewebsites.net/swagger-ui/index.html#/.

![image](https://user-images.githubusercontent.com/75184002/163824532-fe304749-6826-4aa6-8d94-5def79b6ce6f.png)

# TODO:
Add the 'view restaurants' tab on the front end

Populate the database with more meals and recipes
