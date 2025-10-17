package com.javaweb.api;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.javaweb.modal.BuildingDTO;
import com.javaweb.service.BuildingService;

@RestController
@PropertySource("classpath:application.properties")
public class BuildingAPI {
	@Autowired
	private BuildingService buildingService;
	
	@Value("${dev.nguyen}")
	private String data;
	
	@GetMapping(value="/api/building/")
	public List<BuildingDTO> getBuilding(@RequestParam Map<String,Object> params,
										@RequestParam(name="typeCode", required = false) List<String> typeCode) {
		List<BuildingDTO> res = buildingService.findAll(params, typeCode);
		return res;
	}
	
//	public void validate(BuildingDTO buildingDTO) {
//		if (buildingDTO.getName() == null || buildingDTO.getName().equals("") || buildingDTO.getNumberOfBasement() == null) {
//			throw new FieldRequireException("Name or numberOfBasement is null");
//		}
//	}
	
	@PostMapping(value = "/api/building/")
	public void getBuilding2(@RequestBody BuildingDTO buildingDTO) {
		System.out.print("ok");
	}
	
	@DeleteMapping(value = "/api/building/{id}")
	public void deleteBuilding(@PathVariable Integer id) {
		System.out.println(data);
	}
}
