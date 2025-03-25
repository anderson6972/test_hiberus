package com.hiberus.prices.domain.exception;

/**
 * Para el contexto de esta aplicacion en este punto es donde mejor vendria el uso de las clases selladas.
 */
public sealed abstract class PriceNotFoundException extends RuntimeException permits PriceNotFoundExceptionImpl {

    public PriceNotFoundException(String message) {
        super(message);
    }
}
