package com.hc.listing.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.hc.listing.exception.BadRequestException;
import com.hc.listing.models.VehicleEntity;
import com.hc.listing.models.VehicleEntry;
import com.hc.listing.response.VehicleSearchResponse;

public class Utils {

	public static VehicleSearchResponse convert(VehicleEntity ve) {
		VehicleSearchResponse response = new VehicleSearchResponse();
		response.setCode(ve.getKey().getCode());
		response.setDealerId(ve.getKey().getDealerId());
		response.setMake(ve.getMake());
		response.setModel(ve.getModel());
		response.setKw(ve.getKw());
		response.setYear(ve.getYear());
		response.setColor(ve.getColor());
		response.setPrice(ve.getPrice());

		return response;
	}

	public static List<VehicleEntry> getListFromCSV(MultipartFile file) throws IOException {
		CsvSchema schema = CsvSchema.emptySchema().withHeader();
		CsvMapper mapper = new CsvMapper();
		File convFile = new File(file.getOriginalFilename());
		convFile.createNewFile();
		FileOutputStream fos = new FileOutputStream(convFile);
		fos.write(file.getBytes());
		fos.close();
		ObjectReader oReader = mapper.readerFor(VehicleEntry.class).with(schema);
		MappingIterator<VehicleEntry> readValues = oReader.readValues(convFile);
		return readValues.readAll();

	}

	public static void validateInput(VehicleEntry entry) {
		if (entry.getCode() == null) {
			throw new BadRequestException(HttpStatus.BAD_REQUEST, "Give a valid vehicle code for " + entry.getModel());
		}
	}

}
