package com.hiberus.prices.application.port.out;

import com.hiberus.prices.domain.model.Price;

import java.time.LocalDateTime;
import java.util.Optional;

/**
 * Interfaz que define las operaciones de persistencia para la entidad {@link Price}.
 */
public interface PriceRepository {

    /**
     * Busca el precio aplicable para un producto en una fecha determinada.
     *
     * @param productId       Identificador del producto.
     * @param brandId         Identificador de la cadena/brand.
     * @param applicationDate Fecha de aplicación.
     * @return Un {@link Optional} que contiene el precio si se encuentra; de lo contrario, un Optional vacío.
     */
    Optional<Price> findPrice(Long productId, Long brandId, LocalDateTime applicationDate);
}
