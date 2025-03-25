package com.hiberus.prices.domain.exception;

/**
 * Excepción lanzada cuando no se encuentra un precio aplicable para los parámetros indicados.
 */
public final class PriceNotFoundExceptionImpl extends PriceNotFoundException {
    public PriceNotFoundExceptionImpl(final String brand) {
        super(brand);
    }
}
