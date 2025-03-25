package com.hiberus.prices.application.service;


import com.hiberus.prices.application.port.in.PriceQueryUseCase;
import com.hiberus.prices.application.port.out.PriceRepository;
import com.hiberus.prices.domain.exception.PriceNotFoundException;
import com.hiberus.prices.domain.exception.PriceNotFoundExceptionImpl;
import com.hiberus.prices.domain.model.Price;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Optional;

@Slf4j
@Service
public class PriceQueryService implements PriceQueryUseCase {

    private final PriceRepository priceRepository;

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public PriceQueryService(PriceRepository priceRepository) {
        this.priceRepository = priceRepository;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public Price queryPrice(Long productId, Long brandId, String applicationDate) throws PriceNotFoundException {
        log.debug("Iniciando consulta de precio para productId: {}, brandId: {}, applicationDate: {}", productId, brandId, applicationDate);

        // Validar que applicationDate no sea nulo ni esté vacío
        if (applicationDate == null || applicationDate.trim().isEmpty()) {
            log.error("El parámetro applicationDate es nulo o está vacío.");
            throw new IllegalArgumentException("El parámetro applicationDate no puede ser nulo o vacío.");
        }
        LocalDateTime localDateTime;
        try {
            localDateTime = LocalDateTime.parse(applicationDate.trim(), formatter);
        } catch (DateTimeParseException ex) {
            log.error("Formato de fecha inválido para applicationDate: {}", applicationDate, ex);
            throw new IllegalArgumentException("Formato de fecha inválido para applicationDate: " + applicationDate, ex);
        }

        // Invocar al repositorio para obtener el precio correspondiente
        Optional<Price> optionalPrice = priceRepository.findPrice(productId, brandId, localDateTime);
        if (optionalPrice.isPresent()) {
            log.info("Busqueda exitosa : {}", optionalPrice.get());
            return optionalPrice.get();
        } else {
            log.warn("No se encontró registros para productId: {}, brandId: {}, applicationDate: {}", productId, brandId, applicationDate);
            throw new PriceNotFoundExceptionImpl(
                    String.format("No se encontró precio para productId %d, brandId %d en la fecha %s", productId, brandId, applicationDate)
            );
        }
    }

}
