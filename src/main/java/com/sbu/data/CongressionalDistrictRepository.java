package com.sbu.data;

import com.sbu.data.entitys.CongressionalDistrict;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;

@Transactional
public interface CongressionalDistrictRepository extends CrudRepository<CongressionalDistrict, String> {

    @Query(value = "SELECT * FROM congressional_districts WHERE state_id = ?1 AND in_use = 1", nativeQuery = true)
    Iterable<CongressionalDistrict> findByState_id(String state_id);

}
