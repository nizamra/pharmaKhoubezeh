package com.axsos.sys.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.axsos.sys.models.PharmaRequest;

@Repository
public interface PharmaRequestRepository extends CrudRepository<PharmaRequest, Long> {
	List<PharmaRequest> findAll();
}
