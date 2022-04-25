package sta.cs5031p3.mealtimetinder.backend.service;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import sta.cs5031p3.mealtimetinder.backend.BackendApplication;
import sta.cs5031p3.mealtimetinder.backend.api.admin.AdminAPI;
import sta.cs5031p3.mealtimetinder.backend.api.hunter.HunterAPI;
import sta.cs5031p3.mealtimetinder.backend.api.restaurant.RestaurantAPI;
import sta.cs5031p3.mealtimetinder.backend.model.Admin;
import sta.cs5031p3.mealtimetinder.backend.model.Hunter;
import sta.cs5031p3.mealtimetinder.backend.model.Restaurant;
import sta.cs5031p3.mealtimetinder.backend.model.User;
import sta.cs5031p3.mealtimetinder.backend.service.impl.HunterDetailServiceImpl;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
public class APItest {

    @Autowired
    private HunterAPI hunterAPI;

    @Autowired
    private AdminAPI adminAPI;

    @Autowired
    private RestaurantAPI restaurantAPI;

    @Autowired
    private MockMvc mockMVC;

    @MockBean
    private UserService userService;

    @Test
    public void registerAdminTest() throws Exception{
        mockMVC.perform(MockMvcRequestBuilders
                .get("/hunter/meals").accept(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isOk());
    }
    @WithMockUser(username="admin", roles = {"HUNTER"})
    @Test
    public void registerAdminTest2() throws Exception{
        mockMVC.perform(MockMvcRequestBuilders
                .get("/hunter/profile").accept(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isOk());
    }

    @Test
    public void registerAdminTest3() throws Exception{
        mockMVC.perform(MockMvcRequestBuilders
                .get("/admin/allUsers").accept(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isOk());
    }

    @Test
    public void registerAdminTest4() throws Exception{
        mockMVC.perform(MockMvcRequestBuilders
                .get("/restaurant/allMeals").accept(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isOk());
    }
}
