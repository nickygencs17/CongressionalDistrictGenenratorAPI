package com.sbu.data;

import com.sbu.data.entitys.CurrentOfficial;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;

@Transactional
public interface CurrentOfficialRepository extends CrudRepository<CurrentOfficial, Integer> {

    @Query(value = "SELECT * FROM current_officials WHERE state_id  = ?1", nativeQuery = true)
    Iterable<CurrentOfficial> findByState_id(String state_id);
}
