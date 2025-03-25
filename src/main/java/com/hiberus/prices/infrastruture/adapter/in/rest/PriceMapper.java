package com.hiberus.prices.infrastruture.adapter.in.rest;

import com.hiberus.prices.domain.model.Price;
import com.hiberus.prices.models.PriceResponse;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class PriceMapper {

    private final ModelMapper modelMapper;

    public PriceMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    /**
     * Convierte un objeto de dominio Price en un DTO PriceResponse.
     *
     * @param price el objeto de dominio Price
     * @return un objeto PriceResponse mapeado
     */
    public PriceResponse toPriceResponse(Price price) {
        return modelMapper.map(price, PriceResponse.class);
    }

    // Si se necesita mapear de un request DTO a dominio, se puede agregar un método similar:
    // public Price toDomain(PriceRequest priceRequest) {
    //     return modelMapper.map(priceRequest, Price.class);
    // }
}
