package com.hc.listing.repository;

import java.util.List;

import com.hc.listing.models.VehicleEntity;

public interface VehicleSearchRepository {

	List<VehicleEntity> filterByMakeModelColorAndYear(List<String> make, List<String> model, List<String> color,
			List<Integer> year);

}
