package com.javaweb.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.javaweb.converter.BuildingDTOConverter;
import com.javaweb.modal.BuildingDTO;
import com.javaweb.repository.BuildingRepository;
import com.javaweb.repository.entity.BuildingEntity;
import com.javaweb.service.BuildingService;

@Service
public class BuildingServiceImpl implements BuildingService {
	@Autowired
	private BuildingRepository buildingRepository;
	
	@Autowired
	private BuildingDTOConverter buildingDTOConverter;
	
	@Override
	public List<BuildingDTO> findAll(Map<String,Object> params, List<String> typeCode) {
		List<BuildingEntity> buildingEntities = buildingRepository.findAll(params, typeCode);
		List<BuildingDTO> res = new ArrayList<BuildingDTO>();
		for(BuildingEntity item : buildingEntities) {
			BuildingDTO building = buildingDTOConverter.toBuildingDTO(item);
			res.add(building);
		}
		return res;
	}
	
}
