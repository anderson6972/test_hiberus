package com.hiberus.prices.application.port.in;

import com.hiberus.prices.domain.exception.PriceNotFoundException;
import com.hiberus.prices.domain.model.Price;

/**
 * Interfaz que define el caso de uso para consultar el precio aplicable a un producto
 * en función de identificador de producto, cadena y la fecha de aplicación, .
 */
public interface PriceQueryUseCase {

    /**
     * Consulta el precio aplicable para un producto en una fecha determinada.
     *
     * @param productId       Identificador del producto.
     * @param brandId         Identificador de la cadena/brand.
     * @param applicationDate Fecha de aplicación en formato String yyyy-MM-dd HH:mm:ss (por ejemplo: "2020-06-14 10:00:00").
     * @return Un objeto {@link Price} con la información del precio, tarifa y fechas de aplicación.
     * @throws PriceNotFoundException Si no se encuentra un precio para los parámetros indicados.
     */
    Price queryPrice(Long productId, Long brandId, String applicationDate) throws PriceNotFoundException;
}
