package com.newidea.pedido.service;


import com.newidea.pedido.exceptions.ResourceNotFoundException;
import com.newidea.pedido.models.OrderEntity;
import com.newidea.pedido.repositories.OrderRepository;
import com.newidea.pedido.services.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.*;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;


@ExtendWith(MockitoExtension.class)
public class OrderServicesTest {

    @Mock
    private OrderRepository repository;
    
    @InjectMocks
    private OrderService services;

    private OrderEntity order0;
    private OrderEntity orderNew;

    @BeforeEach
    public void setup() {
        // Given / Arrange
        order0 = new OrderEntity(
                1L,
                "CERVEJA",
                "10,00",
                "29/01/1985",
                "29/01/1985",
                "PENDENTE");

        orderNew = new OrderEntity(
                "CERVEJA",
                "10,00",
                "29/01/1985",
                "29/01/1985",
                "PENDENTE");

    }
    
    @DisplayName("JUnit test for Given Order Object when Save Order then Return Order Object")
    @Test
    void testGivenOrderObject_WhenSaveOrder_thenReturnOrderObject() {
        
        // Given / Arrange
        given(repository.save(orderNew)).willReturn(orderNew);
        
        // When / Act
        OrderEntity savedOrder = services.create(orderNew);
        
        // Then / Assert
        assertNotNull(savedOrder);
        assertEquals("PENDENTE", savedOrder.getStatus());
    }   
    
    @DisplayName("JUnit test for Given Existing ID when Save Order then throws Exception")
    @Test
    void testGivenExistingId_WhenSaveOrder_thenThrowsException() {
        
        // Given / Arrange
        given(repository.findById(anyLong())).willReturn(Optional.of(order0));
        
        // When / Act
        assertThrows(ResourceNotFoundException.class, () -> {
            services.create(order0);
        });
        
        // Then / Assert
        verify(repository, never()).save(any(OrderEntity.class));
    }   
    
    @DisplayName("JUnit test for Given Persons List when findAll Persons then Return Persons List")
    @Test
    void testGivenPersonsList_WhenFindAllPersons_thenReturnPersonsList() {
        
        // Given / Arrange
        OrderEntity person1 = new OrderEntity("CERVEJA",
                "10,00",
                "29/01/1985",
                "29/01/1985",
                "PENDENTE");
        
        given(repository.findAll()).willReturn(List.of(order0, person1));
        
        // When / Act
        List<OrderEntity> personsList = services.findAll();
        
        // Then / Assert
        assertNotNull(personsList);
        assertEquals(2, personsList.size());
    }   
    
    @DisplayName("JUnit test for Given Empty Persons List when findAll Persons then Return Empty Persons List")
    @Test
    void testGivenEmptyPersonsList_WhenFindAllPersons_thenReturnEmptyPersonsList() {
        
        // Given / Arrange
        given(repository.findAll()).willReturn(Collections.emptyList());
        
        // When / Act
        List<OrderEntity> personsList = services.findAll();
        
        // Then / Assert
        assertTrue(personsList.isEmpty());
        assertEquals(0, personsList.size());
    }   
    
    @DisplayName("JUnit test for Given PersonId when findById then Return Person Object")
    @Test
    void testGivenPersonId_WhenFindById_thenReturnPersonObject() {
        
        // Given / Arrange
        given(repository.findById(anyLong())).willReturn(Optional.of(order0));
        
        // When / Act
        OrderEntity savedOrder = services.findById(1L);
        
        // Then / Assert
        assertNotNull(savedOrder);
        assertEquals("CERVEJA", savedOrder.getItem());
    }  
    
    @DisplayName("JUnit test for Given Person Object when Update Person then Return Updated Person Object")
    @Test
    void testGivenPersonObject_WhenUpdatePerson_thenReturnUpdatedPersonObject() {
        
        // Given / Arrange
        order0.setId(1L);
        given(repository.findById(anyLong())).willReturn(Optional.of(order0));
        
        order0.setItem("CERVEJA");
        order0.setStatus("PENDENTE");
        
        given(repository.save(order0)).willReturn(order0);
        
        // When / Act
        OrderEntity updatedPerson = services.update(order0);
        
        // Then / Assert
        assertNotNull(updatedPerson);
        assertEquals("CERVEJA", updatedPerson.getItem());
        assertEquals("PENDENTE", updatedPerson.getStatus());
    }  
    
    @DisplayName("JUnit test for Given PersonID when Delete Person then do Nothing")
    @Test
    void testGivenPersonID_WhenDeletePerson_thenDoNothing() {
        
        // Given / Arrange
        order0.setId(1L);
        given(repository.findById(anyLong())).willReturn(Optional.of(order0));
        willDoNothing().given(repository).delete(order0);
        
        // When / Act
        services.delete(order0.getId());
        
        // Then / Assert
        verify(repository, times(1)).delete(order0);
    }  
}
