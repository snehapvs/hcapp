package com.hc.listing.service;

import static org.slf4j.LoggerFactory.getLogger;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.hc.listing.models.VehicleEntry;
import com.hc.listing.models.VehicleEntity;
import com.hc.listing.repository.VehicleListingRepository;
import com.hc.listing.response.VehicleSearchResponse;
import com.hc.listing.utils.Utils;

@Component
public class VehicleListingService {

	private static final Logger log = getLogger(VehicleListingService.class);

	private static DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

	@Autowired
	public VehicleListingRepository vehicleListingDao;

	public void saveVehicleData(List<VehicleEntry> carEntry, String dealerId) {

		List<VehicleEntity> ve = carEntry.stream().map(x -> new VehicleEntity(x, dealerId))
				.collect(Collectors.toList());
		vehicleListingDao.saveAll(ve);

	}

	public List<VehicleSearchResponse> search(MultiValueMap<String, String> searchParams) {
		List<String> year = searchParams.get("year") == null ? new ArrayList<>() : searchParams.get("year");
		List<VehicleEntity> vehicles = vehicleListingDao.filterByMakeModelColorAndYear(searchParams.get("make"),
				searchParams.get("model"), searchParams.get("color"),
				year.stream().map(Integer::parseInt).collect(Collectors.toList()));
		return vehicles.stream().map(Utils::convert).collect(Collectors.toList());
	}

}
