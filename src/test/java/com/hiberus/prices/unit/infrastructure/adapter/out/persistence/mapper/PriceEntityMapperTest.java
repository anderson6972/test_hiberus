package com.hiberus.prices.unit.infrastructure.adapter.out.persistence.mapper;


import com.hiberus.prices.domain.model.Price;
import com.hiberus.prices.infrastruture.adapter.out.persistence.entity.PriceEntity;
import com.hiberus.prices.infrastruture.adapter.out.persistence.mapper.PriceEntityMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@Tag("unit")
@ExtendWith(MockitoExtension.class)
class PriceEntityMapperTest {

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private PriceEntityMapper priceEntityMapper;

    private Price price;
    private PriceEntity priceEntity;

    @BeforeEach
    void setUp() {
        // Inicialización de objetos simulados
        price = new Price();
        price.setProductId(35455L);
        price.setBrandId(1L);
        price.setPrice(25.45);

        priceEntity = new PriceEntity();
        priceEntity.setId(1L);
        priceEntity.setProductId(35455L);
        priceEntity.setBrandId(1L);
        priceEntity.setPrice(25.45);
    }

    @Test
    void testToDomain() {
        when(modelMapper.map(priceEntity, Price.class)).thenReturn(price);

        Price result = priceEntityMapper.toDomain(priceEntity);

        assertNotNull(result);
        assertEquals(price.getProductId(), result.getProductId());
        assertEquals(price.getBrandId(), result.getBrandId());
        assertEquals(price.getPrice(), result.getPrice());

        verify(modelMapper).map(priceEntity, Price.class);
    }

    @Test
    void testToEntity() {
        when(modelMapper.map(price, PriceEntity.class)).thenReturn(priceEntity);

        PriceEntity result = priceEntityMapper.toEntity(price);

        assertNotNull(result);
        assertEquals(priceEntity.getId(), result.getId());
        assertEquals(priceEntity.getProductId(), result.getProductId());
        assertEquals(priceEntity.getBrandId(), result.getBrandId());
        assertEquals(priceEntity.getPrice(), result.getPrice());

        verify(modelMapper).map(price, PriceEntity.class);
    }
}
