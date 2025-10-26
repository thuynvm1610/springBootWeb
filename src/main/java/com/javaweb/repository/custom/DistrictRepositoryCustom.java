package com.javaweb.repository.custom;

import com.javaweb.repository.entity.DistrictEntity;

public interface DistrictRepositoryCustom {
	DistrictEntity findNameById(Long id);
}
