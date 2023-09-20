package org.example.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.exceptions.models.NotFoundException;
import org.example.models.Id;
import org.example.models.user.User;
import org.example.models.user.UserResponse;
import org.example.models.user.mapper.UserEntityMapperImpl;
import org.example.repository.handlers.UuidTypeHandler;
import org.example.security.AuthService;
import org.example.security.LoginController;
import org.example.security.filtering.JwtFilter;
import org.example.security.filtering.SecurityConfig;
import org.example.security.models.Role;
import org.example.security.utils.JwtProvider;
import org.example.security.utils.JwtUtils;
import org.example.service.UserService;
import org.example.service.impl.UserServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.runner.RunWith;
import org.mybatis.spring.boot.test.autoconfigure.AutoConfigureMybatis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/*
TODO: В ТЗ нет информации о тестах для API, я понимаю, что это надо делать, но это довольно трудозатратно,
  поэтому я сделаю тесты только на 1 контроллер в качестве демонстрации. Большая часть приложения была проверена end-to-end
*/
@RunWith(SpringRunner.class)
@AutoConfigureMybatis
@WebMvcTest(controllers = {UserController.class, LoginController.class})
@Import({UserServiceImpl.class, UserEntityMapperImpl.class, UuidTypeHandler.class, JwtUtils.class, JwtProvider.class,
        JwtFilter.class, SecurityConfig.class, AuthService.class})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@WithMockUser
public class UserControllerTest {
    ObjectMapper mapper = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;

    private final String BASE_URL = "/api/users";

    User user = new User(
            "some@lala.com",
            "1234567",
            "John Doe",
            Role.BUYER
    );

    @Test
    @Order(1)
    @DisplayName("Test user gets created and returned")
    @WithMockUser(username = "admin", authorities = { "ADMIN" })
    void testCreateGetUser() throws Exception {
        RequestBuilder requestBuilderPost = MockMvcRequestBuilders.post(BASE_URL)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .content(mapper.writeValueAsString(user));

        MvcResult mvcResultPost = mockMvc.perform(requestBuilderPost).andReturn();
        String responseBodyPost = mvcResultPost.getResponse().getContentAsString();

        Id id = mapper.readValue(responseBodyPost, Id.class);
        assertNotNull(id);
        user.setId(id.getId());

        //Testing get request for newly created user
        RequestBuilder requestBuilderGet =
                MockMvcRequestBuilders.get(BASE_URL + "/" + user.getId())
                        .accept(MediaType.APPLICATION_JSON_VALUE);

        MvcResult mvcResultGet = mockMvc.perform(requestBuilderGet).andReturn();
        String responseBodyGet = mvcResultGet.getResponse().getContentAsString();

        UserResponse userFromRequest = mapper.readValue(responseBodyGet, UserResponse.class);
        assertEquals(user.getEmail(), userFromRequest.getEmail());
    }

    @Test
    @Order(2)
    @DisplayName("Test if user cannot use admin's endpoints")
    public void testUserAccess() throws Exception {
        RequestBuilder requestBuilderDelete = MockMvcRequestBuilders
                .delete(BASE_URL + "/" + user.getId());

        mockMvc.perform(requestBuilderDelete).andExpect(status().isForbidden());
    }

    @Test
    @Order(3)
    @DisplayName("Test user gets updated and deleted")
    @WithMockUser(username = "admin", authorities = { "ADMIN" })
    public void testUpdateDeleteUser() throws Exception {
        String newName = "Lyosha Talanov";
        //Updating user
        user.setFullName(newName);

        RequestBuilder requestBuilderPut = MockMvcRequestBuilders.put(BASE_URL)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(mapper.writeValueAsString(user));

        mockMvc.perform(requestBuilderPut);

        //Getting user to check if it was updated
        RequestBuilder requestBuilderGet = MockMvcRequestBuilders.get(BASE_URL + "/" + user.getId())
                .accept(MediaType.APPLICATION_JSON_VALUE);

        MvcResult mvcResultGet = mockMvc.perform(requestBuilderGet).andReturn();
        String responseBodyGet = mvcResultGet.getResponse().getContentAsString();

        User userFromRequest = mapper.readValue(responseBodyGet, User.class);
        assertEquals(newName, userFromRequest.getFullName());

        //Testing user to check if it was deleted
        RequestBuilder requestBuilderDelete = MockMvcRequestBuilders.delete(BASE_URL + "/" + user.getId());
        mockMvc.perform(requestBuilderDelete);

        mvcResultGet = mockMvc.perform(requestBuilderGet).andReturn();
        NotFoundException exception = (NotFoundException) mvcResultGet.getResolvedException();

        assertNotNull(exception);
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
    }
}
