package com.javaweb.repository.impl;

import java.util.List;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import com.javaweb.builder.BuildingSearchBuilder;
import com.javaweb.repository.BuildingRepository;
import com.javaweb.repository.entity.BuildingEntity;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

@Repository
@Primary
public class BuildingRepositoryImpl implements BuildingRepository {
	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	public List<BuildingEntity> findAll(BuildingSearchBuilder buildingSearchBuilder) {
		//JPQL
//		String sql = "FROM BuildingEntity b WHERE b.id = 1";
//		Query query = entityManager.createQuery(sql, BuildingEntity.class);
		
		//SQL native
		String sql = "SELECT * FROM building b WHERE b.name LIKE '%building%' ";
		Query query = entityManager.createNativeQuery(sql, BuildingEntity.class);
		return query.getResultList();
	}

}
