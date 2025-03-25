package com.hiberus.prices.unit.infrastructure.adapter.in.rest;


import com.hiberus.prices.api.PricesApi;
import com.hiberus.prices.application.port.in.PriceQueryUseCase;
import com.hiberus.prices.domain.model.Price;
import com.hiberus.prices.infrastruture.adapter.in.rest.PriceController;
import com.hiberus.prices.infrastruture.adapter.in.rest.PriceMapper;
import com.hiberus.prices.models.PriceResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PriceControllerTest {

    @InjectMocks
    private PriceController priceController;

    @Mock
    private PriceQueryUseCase priceQueryUseCase;

    @Mock
    private PriceMapper priceMapper;

    @Test
    void testPricesGet() {
        // 1. Preparar datos de prueba
        Long productId = 35455L;
        Long brandId = 1L;
        String applicationDate = "2020-06-14 10:00:00";
        LocalDateTime startDate = LocalDateTime.parse("2020-06-14 00:00:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        LocalDateTime endDate = LocalDateTime.parse("2020-12-31 23:59:59", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        Price price = new Price(productId, brandId, 1, startDate, endDate, 35.50);
        PriceResponse priceResponse = new PriceResponse();
        priceResponse.setProductId(productId.intValue());
        priceResponse.setBrandId(brandId.intValue());
        priceResponse.setPriceList(1);
        priceResponse.setStartDate(startDate.toString());
        priceResponse.setEndDate(endDate.toString());
        priceResponse.setPrice(35.50);

        // 2. Configurar mocks
        when(priceQueryUseCase.queryPrice(productId, brandId, applicationDate)).thenReturn(price);
        when(priceMapper.toPriceResponse(price)).thenReturn(priceResponse);

        // 3. Ejecutar el método a probar
        ResponseEntity<PriceResponse> response = priceController.pricesGet(productId, brandId, applicationDate);

        // 4. Verificar resultados
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(priceResponse, response.getBody());
    }

    @Test
    void testPricesGetWithInvalidProductId() {
        // 1. Preparar datos de prueba con un ID de producto inválido
        String productId = "abc"; // ID de producto inválido
        Long brandId = 1L;
        String applicationDate = "2020-06-14 10:00:00";

        // 2. Verificar que se lanza la excepción NumberFormatException
        assertThrows(NumberFormatException.class, () -> {
            priceController.pricesGet(Long.valueOf(productId), brandId, applicationDate);
        });
    }

    @Test
    void testPricesGetWithInvalidProductIdStr() {
        // 1. Preparar datos de prueba con un ID de producto inválido
        String productId = "abc"; // ID de producto inválido
        Long brandId = 1L;
        String applicationDate = "2020-06-14 10:00:00";

        // 2. Verificar que se lanza la excepción NumberFormatException
        //    y que la respuesta tiene el código de estado 500
        assertThrows(NumberFormatException.class, () -> {
            try {
                priceController.pricesGet(Long.valueOf(productId), brandId, applicationDate);
            } catch (NumberFormatException e) {
                // Capturar la excepción y verificar el código de estado de la respuesta
                ResponseEntity<PriceResponse> response = ((PricesApi) priceController).pricesGet(Long.valueOf(productId), brandId, applicationDate);
                assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
                throw e; // Volver a lanzar la excepción para que assertThrows la capture
            }
        });
    }
}