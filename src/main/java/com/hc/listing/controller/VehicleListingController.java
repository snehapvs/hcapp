package com.hc.listing.controller;

import static org.slf4j.LoggerFactory.getLogger;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.hc.listing.exception.BadRequestException;
import com.hc.listing.models.VehicleEntry;
import com.hc.listing.repository.VehicleListingRepository;
import com.hc.listing.response.ErrorReponse;
import com.hc.listing.service.VehicleListingService;
import com.hc.listing.utils.Utils;

@RestController
@RequestMapping("/api/heycar")
public class VehicleListingController {

	@Autowired
	private VehicleListingService vehicleService;

	@Autowired
	private VehicleListingRepository dao;

	private static final Logger log = getLogger(VehicleListingController.class);

	@RequestMapping(method = RequestMethod.POST, value = "/upload_csv/{dealerId}")
	public ResponseEntity listVehiclesViaCsv(@PathVariable String dealerId,
			@RequestParam("file") MultipartFile listingsFile) throws IOException {
		List<VehicleEntry> vehicleEntry = Utils.getListFromCSV(listingsFile);
		vehicleEntry.forEach(Utils::validateInput);
		vehicleService.saveVehicleData(vehicleEntry, dealerId);
		return ResponseEntity.ok(vehicleEntry);
	}

	@RequestMapping(method = RequestMethod.POST, value = "/vehicle_listings/{dealerId}")
	public ResponseEntity listVehiclesViaApi(@RequestBody List<VehicleEntry> vehicleEntry,
			@PathVariable String dealerId) {
		vehicleEntry.forEach(Utils::validateInput);
		vehicleService.saveVehicleData(vehicleEntry, dealerId);
		return new ResponseEntity<>(HttpStatus.ACCEPTED);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/search")
	public ResponseEntity getMothlyExchangeRateHistory(@RequestParam MultiValueMap<String, String> searchParams) {
		return ResponseEntity.ok(vehicleService.search(searchParams));
	}

	/**
	 *
	 * Exception handlers for this controller are added below.
	 */

	@ExceptionHandler(BadRequestException.class)
	public ResponseEntity handleBadRequestException(BadRequestException exception) {
		log.error("Exception while processing request", exception);
		return ResponseEntity.badRequest().body(new ErrorReponse(exception.getValue().value(),
				"Please verify the given Inputs. ", exception.getMessage()));
	}

	@ExceptionHandler(NumberFormatException.class)
	public ResponseEntity handleNumberFormatException(NumberFormatException exception) {
		log.error("Exception while processing request", exception);
		return ResponseEntity.badRequest()
				.body(new ErrorReponse(HttpStatus.BAD_REQUEST.value(), "Please verify the given Inputs.", exception.getMessage()));
	}

	@ExceptionHandler(IOException.class)
	public ResponseEntity handleIOException(IOException exception) {
		log.error("Exception while processing request", exception);
		return ResponseEntity.badRequest()
				.body(new ErrorReponse(HttpStatus.BAD_REQUEST.value(), "Please verify the given Input request.", exception.getMessage()));
	}

}
