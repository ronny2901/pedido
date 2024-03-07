package com.newidea.pedido.controllerTests;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.newidea.pedido.abstractIntegrationTests.AbstractIntegrationTest;
import com.newidea.pedido.config.TestConfigs;
import com.newidea.pedido.models.OrderEntity;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.specification.RequestSpecification;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

@TestMethodOrder(OrderAnnotation.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class OrderControllerIntegrationTest extends AbstractIntegrationTest {
    
    private static RequestSpecification specification;
    private static ObjectMapper objectMapper;
    private static OrderEntity order;
    
    @BeforeAll
    public static void setup() {
        
        // Given / Arrange
        objectMapper = new ObjectMapper();
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        
        specification = new RequestSpecBuilder()
            .setBasePath("/order")
            .setPort(TestConfigs.SERVER_PORT)
                .addFilter(new RequestLoggingFilter(LogDetail.ALL))
                .addFilter(new ResponseLoggingFilter(LogDetail.ALL))
            .build();
        
        order = new OrderEntity(
            1L,
            "CERVEJA",
            "10,00",
            "29/01/1985",
                "29/01/1985",
            "PENDENTE");
    }

    @Test
    @Order(1)
    @DisplayName("JUnit integration given Order Object when Create one Order should Return a Order Object")
    void integrationTestGivenOrderObject_when_CreateOneOrder_ShouldReturnAOrderObject() throws JsonMappingException, JsonProcessingException {
        
        var content = given().spec(specification)
                    .contentType(TestConfigs.CONTENT_TYPE_JSON)
                    .body(order)
                .when()
                    .post()
                .then()
                    .statusCode(200)
                        .extract()
                            .body()
                                .asString();
        
        OrderEntity createdOrder = objectMapper.readValue(content, OrderEntity.class);
        
        order = createdOrder;
        
        assertNotNull(createdOrder);
        
        assertNotNull(createdOrder.getId());
        assertNotNull(createdOrder.getStatus());
        assertNotNull(createdOrder.getValor());
        assertNotNull(createdOrder.getItem());
        assertNotNull(createdOrder.getDataEntrada());
        assertNotNull(createdOrder.getDataAtualizacao());
        
        assertTrue(createdOrder.getId() > 0);
        assertEquals("PENDENTE", createdOrder.getStatus());
        assertEquals("10,00", createdOrder.getValor());
        assertEquals("29/01/1985", createdOrder.getDataEntrada());
        assertEquals("29/01/1985", createdOrder.getDataAtualizacao());
        assertEquals("CERVEJA", createdOrder.getItem());
    }

    @Test
    @Order(2)
    @DisplayName("JUnit integration given Order Object when Update one Order should Return a Updated Order Object")
    void integrationTestGivenOrderObject_when_UpdateOneOrder_ShouldReturnAUpdatedOrderObject() throws JsonMappingException, JsonProcessingException {
        
        order.setItem("CERVEJA");
        order.setValor("10,00");
        
        var content = given().spec(specification)
                    .contentType(TestConfigs.CONTENT_TYPE_JSON)
                    .body(order)
                .when()
                    .put()
                .then()
                    .statusCode(200)
                        .extract()
                            .body()
                                .asString();
        
        OrderEntity updatedOrder = objectMapper.readValue(content, OrderEntity.class);
        
        order = updatedOrder;
        
        assertNotNull(updatedOrder);

        assertNotNull(updatedOrder.getId());
        assertNotNull(updatedOrder.getStatus());
        assertNotNull(updatedOrder.getValor());
        assertNotNull(updatedOrder.getItem());
        assertNotNull(updatedOrder.getDataEntrada());
        assertNotNull(updatedOrder.getDataAtualizacao());
        
        assertTrue(updatedOrder.getId() > 0);
        assertEquals("PENDENTE", updatedOrder.getStatus());
        assertEquals("10,00", updatedOrder.getValor());
        assertEquals("29/01/1985", updatedOrder.getDataEntrada());
        assertEquals("29/01/1985", updatedOrder.getDataAtualizacao());
        assertEquals("CERVEJA", updatedOrder.getItem());
    }
    
    @Test
    @Order(3)
    @DisplayName("JUnit integration given Order Object when findById should Return a Order Object")
    void integrationTestGivenOrderObject_when_findById_ShouldReturnAOrderObject() throws JsonMappingException, JsonProcessingException {
        
        var content = given().spec(specification)
                .pathParam("id", order.getId())
            .when()
                .get("{id}")
            .then()
                .statusCode(200)
                    .extract()
                        .body()
                            .asString();
        
        OrderEntity foundOrder = objectMapper.readValue(content, OrderEntity.class);
        
        assertNotNull(foundOrder);
        
        assertNotNull(foundOrder.getId());
        assertNotNull(foundOrder.getStatus());
        assertNotNull(foundOrder.getValor());
        assertNotNull(foundOrder.getItem());
        assertNotNull(foundOrder.getDataEntrada());
        assertNotNull(foundOrder.getDataAtualizacao());
        
        assertTrue(foundOrder.getId() > 0);
        assertEquals("PENDENTE", foundOrder.getStatus());
        assertEquals("10,00", foundOrder.getValor());
        assertEquals("29/01/1985", foundOrder.getDataEntrada());
        assertEquals("29/01/1985", foundOrder.getDataAtualizacao());
        assertEquals("CERVEJA", foundOrder.getItem());
    }
    
    @Test
    @Order(4)
    @DisplayName("JUnit integration given Order Object when findAll should Return a Orders List")
    void integrationTest_when_findAll_ShouldReturnAOrdersList() throws JsonMappingException, JsonProcessingException {
        
        OrderEntity anotherOrder = new OrderEntity(
                0L,
                "PINGA",
                "5,00",
                "29/01/1985",
                "29/01/1985",
                "PENDENTE"
            );
        
        given().spec(specification)
            .contentType(TestConfigs.CONTENT_TYPE_JSON)
            .body(anotherOrder)
        .when()
            .post()
        .then()
            .statusCode(200);
        
        var content = given().spec(specification)
                .when()
                    .get()
                .then()
                    .statusCode(200)
                .extract()
                    .body()
                        .asString();
        
        OrderEntity[] myArray = objectMapper.readValue(content, OrderEntity[].class);
        List<OrderEntity> people = Arrays.asList(myArray);
        
        OrderEntity foundOrderOne = people.get(0);
        
        assertNotNull(foundOrderOne);
        
        assertNotNull(foundOrderOne.getId());
        assertNotNull(foundOrderOne.getStatus());
        assertNotNull(foundOrderOne.getValor());
        assertNotNull(foundOrderOne.getItem());
        assertNotNull(foundOrderOne.getDataEntrada());
        assertNotNull(foundOrderOne.getDataAtualizacao());
        
        assertTrue(foundOrderOne.getId() > 0);
        assertEquals("PENDENTE", foundOrderOne.getStatus());
        assertEquals("10,00", foundOrderOne.getValor());
        assertEquals("29/01/1985", foundOrderOne.getDataEntrada());
        assertEquals("29/01/1985", foundOrderOne.getDataAtualizacao());
        assertEquals("CERVEJA", foundOrderOne.getItem());
        
        OrderEntity foundOrderTwo = people.get(1);
        
        assertNotNull(foundOrderTwo);
        
        assertNotNull(foundOrderTwo.getId());
        assertNotNull(foundOrderOne.getStatus());
        assertNotNull(foundOrderOne.getValor());
        assertNotNull(foundOrderOne.getItem());
        assertNotNull(foundOrderOne.getDataEntrada());
        assertNotNull(foundOrderOne.getDataAtualizacao());


        assertTrue(foundOrderTwo.getId() > 0);
        assertEquals("PENDENTE", foundOrderTwo.getStatus());
        assertEquals("5,00", foundOrderTwo.getValor());
        assertEquals("29/01/1985", foundOrderTwo.getDataEntrada());
        assertEquals("29/01/1985", foundOrderTwo.getDataAtualizacao());
        assertEquals("PINGA", foundOrderTwo.getItem());
    }
    
    @Test
    @Order(5)
    @DisplayName("JUnit integration given Order Object when delete should Return No Content")
    void integrationTestGivenOrderObject_when_delete_ShouldReturnNoContent() throws JsonMappingException, JsonProcessingException {
        
        given().spec(specification)
                .pathParam("id", order.getId())
            .when()
                .delete("{id}")
            .then()
                .statusCode(204);
    }
}
