package com.newidea.pedido.controllerTests;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.newidea.pedido.exceptions.ResourceNotFoundException;
import com.newidea.pedido.models.OrderEntity;
import com.newidea.pedido.services.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest
public class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;
    
    @Autowired
    private ObjectMapper mapper;
    
    @MockBean
    private OrderService service;
    
    private OrderEntity order;
    
    @BeforeEach
    public void setup() {
        // Given / Arrange
        order = new OrderEntity("CERVEJA",
                "10,00",
                "29/01/1985",
                "29/01/1985",
                "PENDENTE");
    }
    
    @Test
    @DisplayName("JUnit test for Given Order Object when Create Order then Return Saved Order")
    void testGivenOrderObject_WhenCreateOrder_thenReturnSavedOrder() throws JsonProcessingException, Exception {
        
        // Given / Arrange
        given(service.create(any(OrderEntity.class)))
            .willAnswer((invocation) -> invocation.getArgument(0));
        
        // When / Act
        ResultActions response = mockMvc.perform(post("/order")
            .contentType(MediaType.APPLICATION_JSON)
            .content(mapper.writeValueAsString(order)));
        
        // Then / Assert
        response.andDo(print()).
            andExpect(status().isOk())
            .andExpect(jsonPath("$.item", is(order.getItem())))
            .andExpect(jsonPath("$.status", is(order.getStatus())))
            .andExpect(jsonPath("$.valor", is(order.getValor())));
    }
    
    @Test
    @DisplayName("JUnit test for Given List of Persons when findAll Persons then Return Persons List")
    void testGivenListOfPersons_WhenFindAllPersons_thenReturnPersonsList() throws JsonProcessingException, Exception {
        
        // Given / Arrange
        List<OrderEntity> orders = new ArrayList<>();
        orders.add(order);
        orders.add( new OrderEntity("CERVEJA",
                "10,00",
                "29/01/1985",
                "29/01/1985",
                "PENDENTE"));
        
        given(service.findAll()).willReturn(orders);
        
        // When / Act
        ResultActions response = mockMvc.perform(get("/order"));
        
        // Then / Assert
        response.
            andExpect(status().isOk())
            .andDo(print())
            .andExpect(jsonPath("$.size()", is(orders.size())));
    }
    
    @Test
    @DisplayName("JUnit test for Given orderId when findById then Return Order Object")
    void testGivenorderId_WhenFindById_thenReturnPersonObject() throws JsonProcessingException, Exception {
        
        // Given / Arrange
        long orderId = 1L;
        given(service.findById(orderId)).willReturn(order);
        
        // When / Act
        ResultActions response = mockMvc.perform(get("/order/{id}", orderId));
        
        // Then / Assert
        response.
            andExpect(status().isOk())
            .andDo(print())
            .andExpect(jsonPath("$.item", is(order.getItem())))
            .andExpect(jsonPath("$.status", is(order.getStatus())))
            .andExpect(jsonPath("$.valor", is(order.getValor())));
    }
    
    @Test
    @DisplayName("JUnit test for Given Invalid orderId when findById then Return Not Found")
    void testGivenInvalidorderId_WhenFindById_thenReturnNotFound() throws JsonProcessingException, Exception {
        
        // Given / Arrange
        long orderId = 1L;
        given(service.findById(orderId)).willThrow(ResourceNotFoundException.class);
        
        // When / Act
        ResultActions response = mockMvc.perform(get("/order/{id}", orderId));
        
        // Then / Assert
        response.andExpect(status().isNotFound())
            .andDo(print());
    }
    
    @Test
    @DisplayName("JUnit test for Given Updated Person when Update then Return Updated Person Object")
    void testGivenUpdatedPerson_WhenUpdate_thenReturnUpdatedPersonObject() throws JsonProcessingException, Exception {
        
        // Given / Arrange
        long orderId = 1L;
        given(service.findById(orderId)).willReturn(order);
        given(service.update(any(OrderEntity.class)))
            .willAnswer((invocation) -> invocation.getArgument(0));
        
        // When / Act
        OrderEntity updatedOrder = new OrderEntity("CERVEJA",
                "10,00",
                "29/01/1985",
                "29/01/1985",
                "PENDENTE");

        
        ResultActions response = mockMvc.perform(put("/order")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(updatedOrder)));
        
        // Then / Assert
        response.
            andExpect(status().isOk())
            .andDo(print())
            .andExpect(jsonPath("$.item", is(updatedOrder.getItem())))
            .andExpect(jsonPath("$.status", is(updatedOrder.getStatus())))
            .andExpect(jsonPath("$.valor", is(updatedOrder.getValor())));
    }
    
    @Test
    @DisplayName("JUnit test for Given Unexistent Person when Update then Return Not Found")
    void testGivenUnexistentPerson_WhenUpdate_thenReturnNotFound() throws JsonProcessingException, Exception {
        
        // Given / Arrange
        long orderId = 1L;
        given(service.findById(orderId)).willThrow(ResourceNotFoundException.class);
        given(service.update(any(OrderEntity.class)))
            .willAnswer((invocation) -> invocation.getArgument(1));
        
        // When / Act
        OrderEntity updatedPerson = new OrderEntity("CERVEJA",
                "10,00",
                "29/01/1985",
                "29/01/1985",
                "PENDENTE");
        
        ResultActions response = mockMvc.perform(put("/order")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(updatedPerson)));
        
        // Then / Assert
        response.
            andExpect(status().isNotFound())
            .andDo(print());
    }
    
    @Test
    @DisplayName("JUnit test for Given orderId when Delete then Return NotContent")
    void testGivenorderId_WhenDelete_thenReturnNotContent() throws JsonProcessingException, Exception {
        
        // Given / Arrange
        long orderId = 1L;
        willDoNothing().given(service).delete(orderId);
        
        // When / Act
        ResultActions response = mockMvc.perform(delete("/order/{id}", orderId));
        
        // Then / Assert
        response.
            andExpect(status().isNoContent())
                .andDo(print());
    }
}