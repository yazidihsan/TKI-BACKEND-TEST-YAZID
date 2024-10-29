package com.crud.demo;

import com.crud.demo.service.UserService;
import com.crud.demo.util.JwtUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.crud.demo.config.SecurityConfig;
import com.crud.demo.controller.UserController;
import com.crud.demo.dto.UserDto;
import com.crud.demo.model.User;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;
import java.util.List;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@Import(SecurityConfig.class)
@WebMvcTest(value = UserController.class, includeFilters = {
    @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = JwtUtil.class)
})
@AutoConfigureMockMvc
public class UserControllerTest {

    Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JwtUtil jwtUtil;

   @InjectMocks
    private UserController userController;  // Inject the controller

    private UserDto userDto;
    

    @MockBean
    private UserService userService;

    private Page<User> usersPage;

@BeforeEach
public void setUp() {
    // Mock the SecurityContext
    SecurityContext securityContext = Mockito.mock(SecurityContext.class);
    Authentication authentication = Mockito.mock(Authentication.class);
 
    Mockito.when(authentication.getName()).thenReturn("pojo"); // Use the authenticated username
    Mockito.when(authentication.isAuthenticated()).thenReturn(true);

    // Set the mocked SecurityContext
    Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
    SecurityContextHolder.setContext(securityContext);

  

}
 

    @Test
    public void testGetAllUsers() throws Exception {

        User user1 = new User("6713fdd5c84f3816afd57d27", "nama", "871356389483004", "nama@gmail.com", "ROLE_ADMIN", "nama", "$2a$10$cTHkcddryf1h7F2Y7ETCDu4qgv21ecEVnxz5rzom0IQjU3vRYW2rq");
        User user2 = new User("6713fe41c84f3816afd57d2a", "namaku", "871356389483004", "namaku@gmail.com", "ROLE_FINANCE", "namaku", "n3004");
        User user3 = new User("1", "yazid ihsan", "085701701721", "yazidihsan1@gmail.com", "ROLE_ADMIN", "yazid", "RahasiaDong123!@");
        
        String jwtToken = jwtUtil.generateToken(user3.getUsername(),user3.getRole());
        RequestBuilder request = MockMvcRequestBuilders.get("/api/users")
             .header("Authorization","Bearer " + jwtToken)
             .param("page", "0")
             .param("size", "10")
             .param("sortField", "nama")
             .param("sortDirection", "ASC")
             .accept(MediaType.APPLICATION_JSON)
             .contentType(MediaType.APPLICATION_JSON);

             List<User> users = Arrays.asList(user1,user2,user3);

        usersPage = new PageImpl<>(users, PageRequest.of(0, 10), users.size());

        Mockito.when(userService.getUsers(0, 10, "nama","ASC", "Bearer "+ jwtToken))
            .thenReturn(usersPage);

            MvcResult mvcResult = (MvcResult) mockMvc.perform(request).andDo(print()).andExpect(status().isOk())
                                            // .andExpect(jsonPath("$.content[0].nama").value("nama"))
                                            // .andExpect(jsonPath("$.content[1].nama").value("namaku"))
                                            // .andExpect(jsonPath("$.content[2].nama").value("pojo"))
                                            .andReturn();
                System.out.println("Generated Token: " + jwtToken);
                System.out.println("length of result: " +jsonPath("$.content.length").value(3));
                logger.info(mvcResult.getResponse().getContentAsString());


    }

     @Test
    public void testCreateUser_Success() throws Exception {

        userDto = new UserDto();
        userDto.setNama("okejon");
        userDto.setPhone("08989898989");
        userDto.setEmail("okejon@gmail.com");
        userDto.setRole("ROLE_ADMIN");
        userDto.setUsername("okejon");
        // Mocking the service response
        User createdUser = new User();
        // createdUser.setId("2");
        createdUser.setNama(userDto.getNama());
        createdUser.setPhone(userDto.getPhone());
        createdUser.setEmail(userDto.getEmail());
        createdUser.setRole(userDto.getRole());
        createdUser.setUsername(userDto.getUsername());
        // createdUser.setPassword("pojo12345678");
       User user = new User("1", "yazid ihsan", "085701701721", "yazidihsan1@gmail.com", "ROLE_ADMIN", "yazid", "RahasiaDong123!@");

        String jwtToken = jwtUtil.generateToken(user.getUsername(), user.getRole());
        // Mock the service call
        when(userService.createUser(any(UserDto.class), any(String.class)))
            .thenReturn(createdUser);

        // Perform the POST request to the /users endpoint
        mockMvc.perform(MockMvcRequestBuilders.post("/api/users")  // Adjust the path to match your endpoint
                .header("Authorization", "Bearer " + jwtToken)  // Simulate the token
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(userDto)))
                .andDo(print())
                .andExpect(status().isCreated())  // Expect HTTP 201 Created
                .andExpect(jsonPath("$.message").value("User Created Successfully"))
                .andExpect(jsonPath("$.data.nama").value("okejon"))
                .andExpect(jsonPath("$.data.phone").value("08989898989"))
                .andExpect(jsonPath("$.data.email").value("okejon@gmail.com"))
                .andExpect(jsonPath("$.data.role").value("ROLE_ADMIN"))
                .andExpect(jsonPath("$.data.username").value("okejon"))
                .andReturn();

                System.out.println("token created:" +jwtToken);
    }




  
}
