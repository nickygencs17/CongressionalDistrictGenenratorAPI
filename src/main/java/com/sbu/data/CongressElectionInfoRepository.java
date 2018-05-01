package com.sbu.data;

import com.sbu.data.entitys.CongressElectionInfo;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;

@Transactional
public interface CongressElectionInfoRepository extends CrudRepository<CongressElectionInfo, Integer> {

    @Query(value = "SELECT * FROM congress_election_info WHERE state_id = ?1", nativeQuery = true)
    Iterable<CongressElectionInfo> findByState_id(String state_id);
}
