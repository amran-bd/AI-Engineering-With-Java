package com.sdd.demo.model;

import com.sdd.demo.entity.CustomerEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface CustomerMapper {

	@Mapping(target = "custId", ignore = true)
	@Mapping(target = "status", constant = "ACTIVE")
	@Mapping(target = "emailNormalized", ignore = true)
	@Mapping(target = "createdAt", ignore = true)
	@Mapping(target = "updatedAt", ignore = true)
	CustomerEntity toEntity(CreateCustomerRequest request);

	@Mapping(target = "custId", ignore = true)
	@Mapping(target = "emailNormalized", ignore = true)
	@Mapping(target = "createdAt", ignore = true)
	@Mapping(target = "updatedAt", ignore = true)
	void updateEntity(UpdateCustomerRequest request, @MappingTarget CustomerEntity entity);

	@Mapping(target = "id", source = "custId")
	CustomerResponse toResponse(CustomerEntity entity);
}
