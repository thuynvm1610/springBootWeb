package com.javaweb.service;

import java.util.List;

import com.javaweb.modal.BuildingDTO;

public interface BuildingService {
	List<BuildingDTO> findAll(String name, Long districtid);
}
