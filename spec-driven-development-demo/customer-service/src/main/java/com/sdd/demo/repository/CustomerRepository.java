package com.sdd.demo.repository;

import com.sdd.demo.entity.CustomerEntity;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<CustomerEntity, UUID> {

	boolean existsByEmailNormalized(String emailNormalized);

	boolean existsByEmailNormalizedAndCustIdNot(String emailNormalized, UUID custId);
}
