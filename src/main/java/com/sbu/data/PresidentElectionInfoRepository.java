package com.sbu.data;
import com.sbu.data.entitys.PresidentElectionInfo;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;

@Transactional
public interface PresidentElectionInfoRepository extends CrudRepository<PresidentElectionInfo, Integer> {

    @Query(value = "SELECT * FROM president_election_info WHERE state_id = ?1",nativeQuery = true)
    Iterable<PresidentElectionInfo> findByState_id(String state_id);
}
