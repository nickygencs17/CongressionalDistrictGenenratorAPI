package com.sbu.data;
import com.sbu.data.entitys.CongressionalDistrict;
import com.sbu.data.entitys.VotingDistrict;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;

@Transactional
public interface VotingDistrictRepository extends CrudRepository<VotingDistrict, String> {

    @Query(value = "SELECT * FROM precincts WHERE state_id = ?1",nativeQuery = true)
    Iterable<VotingDistrict> findByState_id(String state_id);

}
