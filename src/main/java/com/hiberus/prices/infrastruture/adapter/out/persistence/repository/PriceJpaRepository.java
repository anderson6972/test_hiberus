package com.hiberus.prices.infrastruture.adapter.out.persistence.repository;


import com.hiberus.prices.application.port.out.PriceRepository;
import com.hiberus.prices.domain.model.Price;
import com.hiberus.prices.infrastruture.adapter.out.persistence.entity.PriceEntity;
import com.hiberus.prices.infrastruture.adapter.out.persistence.mapper.PriceEntityMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public class PriceJpaRepository implements PriceRepository {

    private final SpringDataPriceRepository springDataPriceRepository;
    private final PriceEntityMapper priceEntityMapper;

    @Autowired
    public PriceJpaRepository(SpringDataPriceRepository springDataPriceRepository, PriceEntityMapper priceEntityMapper) {
        this.springDataPriceRepository = springDataPriceRepository;
        this.priceEntityMapper = priceEntityMapper;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Price> findPrice(Long productId, Long brandId, LocalDateTime applicationDate) {
        // Se consulta la entidad JPA aplicable a partir del repositorio Spring Data
        Optional<PriceEntity> priceEntity = springDataPriceRepository.findApplicablePrice(productId, brandId, applicationDate);
        // Se mapea la entidad a un objeto de dominio y se retorna, si se encuentra
        return priceEntity.map(priceEntityMapper::toDomain);
    }
}
