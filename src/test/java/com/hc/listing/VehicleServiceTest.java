package com.hc.listing;

import static org.mockito.Mockito.verify;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.hc.listing.models.VehicleEntity;
import com.hc.listing.models.VehicleEntry;
import com.hc.listing.repository.VehicleListingRepository;
import com.hc.listing.service.VehicleListingService;

@RunWith(MockitoJUnitRunner.class)
public class VehicleServiceTest {

	@InjectMocks
	private VehicleListingService vehicleService;
	
	@Mock
	private VehicleListingRepository dao;
	
	@SuppressWarnings("deprecation")
	@Test
	public void testVehicleDataEntry() {
		List<VehicleEntry> entries=new ArrayList<VehicleEntry>() {{
			add(getVehicleEntry());
			add(getVehicleEntry());
		}};
		vehicleService.saveVehicleData(entries, "123");
		verify(dao).saveAll(Mockito.anyIterableOf(VehicleEntity.class));

	}
	
	@Test
	public void testVehicleSearch() {
		MultiValueMap<String, String> searchParams=new LinkedMultiValueMap<String, String>() ;
		searchParams.add("make", "test1");
		searchParams.add("make", "test2");
		searchParams.add("year", "2014");
		List<Integer> testList = new ArrayList<>();
		testList.add(2014);
		vehicleService.search(searchParams);
		verify(dao).filterByMakeModelColorAndYear(searchParams.get("make"), searchParams.get("model"),
				searchParams.get("color"), testList);
	}
	
	
	
	private static VehicleEntry getVehicleEntry() {
		VehicleEntry ve1 = new VehicleEntry();
		ve1.setCode(Instant.now().toString());
		ve1.setColor("red");
		ve1.setKw(2500);
		ve1.setMake("benz");
		ve1.setModel("mercedes");
		ve1.setPrice(123576.0);
		ve1.setYear(2006);
		return ve1;
	}

}
