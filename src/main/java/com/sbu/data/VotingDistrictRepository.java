package com.sbu.data;
import com.sbu.data.entitys.VotingDistrict;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
public interface VotingDistrictRepository extends CrudRepository<VotingDistrict, String> {

    @Query(value = "SELECT * FROM voting_districts WHERE state_id = ?1",nativeQuery = true)
    List<VotingDistrict> findByState_id(String state_id);

}
