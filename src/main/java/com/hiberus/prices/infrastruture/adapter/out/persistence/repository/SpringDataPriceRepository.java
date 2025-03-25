package com.hiberus.prices.infrastruture.adapter.out.persistence.repository;

import com.hiberus.prices.infrastruture.adapter.out.persistence.entity.PriceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.Optional;

public interface SpringDataPriceRepository extends JpaRepository<PriceEntity, Long> {

    @Query(value = """
            SELECT * FROM prices p
            WHERE p.product_id = :productId
            AND p.brand_id = :brandId
            AND p.start_date <= :applicationDate
            AND p.end_date >= :applicationDate
            ORDER BY p.priority DESC
            LIMIT 1
            """,
            nativeQuery = true)
    Optional<PriceEntity> findApplicablePrice(
            @Param("productId") Long productId,
            @Param("brandId") Long brandId,
            @Param("applicationDate") LocalDateTime applicationDate);

}