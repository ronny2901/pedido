package com.newidea.pedido.repositories;

import com.newidea.pedido.abstractIntegrationTests.AbstractIntegrationTest;
import com.newidea.pedido.models.OrderEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;


@DataJpaTest
@AutoConfigureTestDatabase(replace=AutoConfigureTestDatabase.Replace.NONE)
class OrderRepositoryTest extends AbstractIntegrationTest {

    @Autowired
    private OrderRepository repository;

    private OrderEntity order0;

    @BeforeEach
    public void setup() {
        // Given / Arrange
        order0 = new OrderEntity(
                "CERVEJA",
                "10,00",
                "29/01/1985",
                "29/01/1985",
                "PENDENTE");


    }

    @DisplayName("JUnit test for Given Person Object when Save then Return Saved Person")
    @Test
    void testGivenPersonObject_whenSave_thenReturnsavedOrder() {

        // Given / Arrange

        // When / Act
        OrderEntity savedOrder = repository.save(order0);

        // Then / Assert
        assertNotNull(savedOrder);
        assertTrue(savedOrder.getId() > 0);
    }

    @DisplayName("JUnit test for Given Order List when findAll then Return Order List")
    
    void testGivenOrderList_whenFindAll_thenReturnOrderList() {

        // Given / Arrange
        OrderEntity order1 = new OrderEntity(
                "PINGA",
                "5,00",
                "29/01/1985",
                "29/01/1985",
                "PENDENTE");

        repository.save(order0);
        repository.save(order1);

        // When / Act
        List<OrderEntity> personList = repository.findAll();

        // Then / Assert
        assertNotNull(personList);
        assertEquals(2, personList.size());
    }

    @DisplayName("JUnit test for Given Person Object when findByID then Return Person Object")
    @Test
    void testGivenPersonObject_whenFindByID_thenReturnPersonObject() {

        // Given / Arrange
        repository.save(order0);

        // When / Act
        OrderEntity savedOrder = repository.findById(order0.getId()).get();

        // Then / Assert
        assertNotNull(savedOrder);
        assertEquals(order0.getId(), savedOrder.getId());
    }

    @DisplayName("JUnit test for Given Person Object when findByEmail then Return Person Object")
    
    void testGivenPersonObject_whenFindByStatus_thenReturnPersonObject() {

        // Given / Arrange
        repository.save(order0);

        // When / Act
        OrderEntity savedOrder = repository.findByStatus(order0.getStatus()).get();

        // Then / Assert
        assertNotNull(savedOrder);
        assertEquals(order0.getId(), savedOrder.getId());
    }

    @DisplayName("JUnit test for Given Person Object when Update Person then Return Updated Person Object")
    @Test
    void testGivenOrderObject_whenUpdateOrder_thenReturnUpdatedOrderObject() {

        // Given / Arrange
        repository.save(order0);

        // When / Act
        OrderEntity savedOrder = repository.findById(order0.getId()).get();
        savedOrder.setItem("CERVEJA");
        savedOrder.setStatus("PENDENTE");
        savedOrder.setDataAtualizacao(String.valueOf(new Date()));

        OrderEntity updatedOrder = repository.save(savedOrder);

        // Then / Assert
        assertNotNull(updatedOrder);
        assertEquals("CERVEJA", updatedOrder.getItem());
        assertEquals("PENDENTE", updatedOrder.getStatus());
    }

    @DisplayName("JUnit test for Given Order Object when Delete then Remove Order")
    @Test
    void testGivenOrderObject_whenDelete_thenRemoveOrder() {

        // Given / Arrange
        repository.save(order0);

        // When / Act
        repository.deleteById(order0.getId());

        Optional<OrderEntity> orderOptional = repository.findById(order0.getId());

        // Then / Assert
        assertTrue(orderOptional.isEmpty());
    }

    @DisplayName("JUnit test for Given status and valor when findJPQL then Return Order Object")
    @Test
    void testGivenstatusAndvalor_whenFindJPQL_thenReturnOrderObject() {

        // Given / Arrange
        repository.save(order0);

        String item = "CERVEJA";
        String status = "PENDENTE";

        // When / Act
        OrderEntity savedOrder = repository.findByJPQL(item, status);

        // Then / Assert
        assertNotNull(savedOrder);
        assertEquals(item, savedOrder.getItem());
        assertEquals(status, savedOrder.getStatus());
    }

    @DisplayName("JUnit test for Given item and status when findByJPQLNamedParameters then Return Order Object")
    @Test
    void testGivenstatusAndvalor_whenFindByJPQLNamedParameters_thenReturnOrderObject() {

        // Given / Arrange
        repository.save(order0);

        String item = "CERVEJA";
        String status = "PENDENTE";

        // When / Act
        OrderEntity savedOrder = repository.findByJPQLNamedParameters(item, status);

        // Then / Assert
        assertNotNull(savedOrder);
        assertEquals(item, savedOrder.getItem());
        assertEquals(status, savedOrder.getStatus());
    }

    @DisplayName("JUnit test for Given status and valor when findByNativeSQL then Return Order Object")
    @Test
    void testGivenStatusAndvalor_whenFindByNativeSQL_thenReturnOrderObject() {

        // Given / Arrange
        repository.save(order0);

        String item = "CERVEJA";
        String status = "PENDENTE";

        // When / Act
        OrderEntity savedOrder = repository.findByNativeSQL(item, status);

        // Then / Assert
        assertNotNull(savedOrder);
        assertEquals(item, savedOrder.getItem());
        assertEquals(status, savedOrder.getStatus());
    }

    @DisplayName("JUnit test for Given item and stats when findByNativeSQLwithNamedParameters then Return Order Object")
    @Test
    void testGivenstatusAndvalor_whenFindByNativeSQLwithNamedParameters_thenReturnOrderObject() {

        // Given / Arrange
        repository.save(order0);

        String item = "CERVEJA";
        String status = "PENDENTE";

        // When / Act
        OrderEntity savedOrder = repository.findByNativeSQLwithNamedParameters(item, status);

        // Then / Assert
        assertNotNull(savedOrder);
        assertEquals(item, savedOrder.getItem());
        assertEquals(status, savedOrder.getStatus());
    }
}
