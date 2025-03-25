package com.hiberus.prices.integracion.infrastructure.adapter.in.rest;


import com.hiberus.prices.infrastruture.adapter.out.persistence.entity.PriceEntity;
import com.hiberus.prices.models.PriceResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.Timestamp;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@Tag("integracion")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PriceControllerIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @LocalServerPort
    private int port;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @DisplayName("Test de integración para la clase PriceController con diferentes fechas")
    @ParameterizedTest
    @ValueSource(strings = {
            "2020-06-14 10:00:00",
            "2020-06-14 16:00:00",
            "2020-06-14 21:00:00",
            "2020-06-15 10:00:00",
            "2020-06-16 21:00:00"
    })
    void testPricesGetWithDifferentDates(String applicationDate) {
        // Convertir la fecha a Timestamp para la consulta
        Timestamp applicationTimestamp = Timestamp.valueOf(applicationDate);

        // Obtener los datos esperados de la base de datos
        String sql = "SELECT * FROM PRICES WHERE product_id = ? AND brand_id = ? AND start_date <= ? AND end_date >= ? " +
                "ORDER BY priority DESC LIMIT 1";

        PriceEntity expectedPriceEntity = jdbcTemplate.queryForObject(sql, new Object[]{35455, 1, applicationTimestamp, applicationTimestamp},
                (rs, rowNum) -> new PriceEntity(
                        rs.getLong("id"),
                        rs.getLong("product_id"),
                        rs.getLong("brand_id"),
                        rs.getInt("price_list"),
                        rs.getTimestamp("start_date").toLocalDateTime(),
                        rs.getTimestamp("end_date").toLocalDateTime(),
                        rs.getDouble("price"),
                        rs.getInt("priority")
                ));

        assertNotNull(expectedPriceEntity, "No se encontraron datos en la base de datos para la fecha proporcionada");

        // Construir la URL de la petición
        String url = "http://localhost:" + port + "/prices?productId=35455&brandId=1&applicationDate=" + applicationDate;

        // Realizar la petición GET
        ResponseEntity<PriceResponse> response = restTemplate.getForEntity(url, PriceResponse.class);

        // Verificar el código de estado de la respuesta
        assertEquals(HttpStatus.OK, response.getStatusCode());

        // Verificar el cuerpo de la respuesta
        PriceResponse priceResponse = response.getBody();
        assertNotNull(priceResponse, "La respuesta de la API es nula");
        assertEquals(expectedPriceEntity.getProductId(), priceResponse.getProductId().longValue());
        assertEquals(expectedPriceEntity.getBrandId(), priceResponse.getBrandId().longValue());
        assertEquals(expectedPriceEntity.getPriceList(), priceResponse.getPriceList());
        assertEquals(expectedPriceEntity.getStartDate().toString(), priceResponse.getStartDate());
        assertEquals(expectedPriceEntity.getEndDate().toString(), priceResponse.getEndDate());
        assertEquals(expectedPriceEntity.getPrice(), priceResponse.getPrice());
    }

    @Test
    @DisplayName("Test de integracion para la clase PriceController cuando no se encuentra un precio")
    void testPricesGetNotFound() {
        // 1. Construir la URL de la petición con parámetros que no existan en la base de datos
        String applicationDate = "2025-02-13 10:00:00"; // Fecha en la que no hay precios
        String url = "http://localhost:" + port + "/prices?productId=99999&brandId=99999&applicationDate=" + applicationDate;

        // 2. Realizar la petición GET
        ResponseEntity<PriceResponse> response = restTemplate.getForEntity(url, PriceResponse.class);

        // 3. Verificar el código de estado de la respuesta
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

    }

    @Test
    @DisplayName("Test de integración para la clase PriceController con productId inválido")
    void testPricesGetWithInvalidProductId() {
        // 1. Construir la URL de la petición con un productId inválido
        String url = "http://localhost:" + port + "/prices?productId=abc&brandId=1&applicationDate=2020-06-14 10:00:00";

        // 2. Realizar la petición GET
        ResponseEntity<PriceResponse> response = restTemplate.getForEntity(url, PriceResponse.class);

        // 3. Verificar el código de estado de la respuesta
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    @DisplayName("Test de integración para la clase PriceController con brandId inválido")
    void testPricesGetWithInvalidBrandId() {
        // 1. Construir la URL de la petición con un brandId inválido
        String url = "http://localhost:" + port + "/prices?productId=35455&brandId=abc&applicationDate=2020-06-14 10:00:00";

        // 2. Realizar la petición GET
        ResponseEntity<PriceResponse> response = restTemplate.getForEntity(url, PriceResponse.class);

        // 3. Verificar el código de estado de la respuesta
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    @DisplayName("Test de integración para la clase PriceController con applicationDate inválido")
    void testPricesGetWithInvalidApplicationDate() {
        // 1. Construir la URL de la petición con un applicationDate inválido
        String url = "http://localhost:" + port + "/prices?productId=35455&brandId=1&applicationDate=2020-06-14"; // Formato inválido

        // 2. Realizar la petición GET
        ResponseEntity<PriceResponse> response = restTemplate.getForEntity(url, PriceResponse.class);

        // 3. Verificar el código de estado de la respuesta
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

}
