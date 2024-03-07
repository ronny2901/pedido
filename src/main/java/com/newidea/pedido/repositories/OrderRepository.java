package com.newidea.pedido.repositories;

import com.newidea.pedido.models.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, Long> {

    Optional<OrderEntity> findByStatus(String status);

    // Define custom query using JPQL with index parameters
    @Query("select o from OrderEntity o where o.item =?1 and o.status =?2")
    OrderEntity findByJPQL(String item, String status);

    // Define custom query using JPQL with named parameters
    // Define custom query using JPQL with named parameters
    @Query("select o from OrderEntity o where o.item =:item and o.status =:status")
    OrderEntity findByJPQLNamedParameters(
            @Param("item") String item,
            @Param("status") String status);

    // Define custom query using Native SQL with index parameters
    @Query(value = "select * from orders p where p.item =?1 and p.status =?2", nativeQuery = true)
    OrderEntity findByNativeSQL(String item, String status);

    // Define custom query using Native SQL with named parameters
    @Query(value = "select * from orders p where p.item =:item and p.status =:status", nativeQuery = true)
    OrderEntity findByNativeSQLwithNamedParameters(
            @Param("item") String item,
            @Param("status") String status);
}
