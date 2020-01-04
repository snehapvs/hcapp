package com.hc.listing.repository;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.hc.listing.models.VehicleEntity;
import com.hc.listing.models.VehicleEntityKey;

@Transactional
@Repository
public interface VehicleListingRepository
		extends CrudRepository<VehicleEntity, VehicleEntityKey>, VehicleSearchRepository {

}
