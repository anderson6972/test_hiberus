package com.hiberus.prices.unit.domain.model;

import com.hiberus.prices.domain.model.Price;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Tag("unit")
public class PriceTest {

    @Test
    void testPriceCreation() {
        LocalDateTime startDate = LocalDateTime.of(2020, 6, 14, 0, 0);
        LocalDateTime endDate = LocalDateTime.of(2020, 12, 31, 23, 59);
        Price price = new Price(1L, 35455L, 1, startDate, endDate, 35.50);

        assertEquals(35455L, price.getBrandId());
        assertEquals(1L, price.getProductId());
        assertEquals(35.50, price.getPrice());
    }
}