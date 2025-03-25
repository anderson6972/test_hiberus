package com.hiberus.prices.infrastruture.adapter.in.rest;


import com.hiberus.prices.application.port.in.PriceQueryUseCase;
import com.hiberus.prices.domain.model.Price;
import com.hiberus.prices.api.PricesApi;
import com.hiberus.prices.models.PriceResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class PriceController implements PricesApi {

    private final PriceQueryUseCase priceQueryUseCase;
    private final PriceMapper priceMapper;

    @Autowired
    public PriceController(PriceQueryUseCase priceQueryUseCase, PriceMapper priceMapper) {
        this.priceQueryUseCase = priceQueryUseCase;
        this.priceMapper = priceMapper;
    }

    @Override
    public ResponseEntity<PriceResponse> pricesGet(Long productId, Long brandId, String applicationDate) {
        log.info("Iniciando busqueda con los siguiente parametros de entrada {} , {} , {}", productId, brandId, applicationDate);
        // Se invoca el caso de uso para obtener el precio (devuelve un objeto Price)
        Price price = priceQueryUseCase.queryPrice(productId, brandId, applicationDate);
        // Se mapea el objeto de dominio al DTO de respuesta.
        PriceResponse priceResponse = priceMapper.toPriceResponse(price);
        log.info("Proceso de busqueda finalizado");
        return ResponseEntity.ok(priceResponse);
    }
}
