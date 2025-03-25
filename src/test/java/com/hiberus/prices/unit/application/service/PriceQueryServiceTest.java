package com.hiberus.prices.unit.application.service;


import com.hiberus.prices.application.port.out.PriceRepository;
import com.hiberus.prices.application.service.PriceQueryService;
import com.hiberus.prices.domain.exception.PriceNotFoundException;
import com.hiberus.prices.domain.model.Price;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@Tag("unit")
@ExtendWith(MockitoExtension.class)
public class PriceQueryServiceTest {

    @InjectMocks
    private PriceQueryService priceQueryService;
    @Mock
    private PriceRepository priceRepository;

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private LocalDateTime applicationDate;
    private final String applicationDateString = "2020-06-14 16:00:00"; // Formato correcto

    @BeforeEach
    void setUp() {
        applicationDate = LocalDateTime.parse(applicationDateString, formatter);
    }

    @Test
    void testQueryPrice_Success() {

        Price expectedPrice = new Price(1L, 35455L, 2, applicationDate, applicationDate.plusHours(2), 25.45);

        when(priceRepository.findPrice(35455L, 1L, applicationDate)).thenReturn(Optional.of(expectedPrice));

        Price result = priceQueryService.queryPrice(35455L, 1L, applicationDateString);

        assertNotNull(result);
        assertEquals(25.45, result.getPrice());
    }

    @Test
    void testQueryPrice_NotFound() {
        when(priceRepository.findPrice(35455L, 1L, applicationDate)).thenReturn(Optional.empty());
        assertThrows(PriceNotFoundException.class, () -> priceQueryService.queryPrice(35455L, 1L, applicationDateString));
    }

    @Test
    void testQueryPrice_NullApplicationDate() {
        assertThrows(IllegalArgumentException.class,
                () -> priceQueryService.queryPrice(35455L, 1L, null));
    }

    @Test
    void testQueryPrice_EmptyApplicationDate() {
        assertThrows(IllegalArgumentException.class,
                () -> priceQueryService.queryPrice(35455L, 1L, ""));
    }

    @Test
    void testQueryPrice_InvalidDateFormat() {
        String invalidDate = "14-06-2020 16:00:00"; // Formato incorrecto (dd-MM-yyyy)

        assertThrows(IllegalArgumentException.class,
                () -> priceQueryService.queryPrice(35455L, 1L, invalidDate));
    }

}
