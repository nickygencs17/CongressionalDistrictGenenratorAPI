package com.sbu.data;

import com.sbu.data.entitys.Redistrict;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
public interface RedistrictRepository extends CrudRepository<Redistrict, String> {

    @Query(value = "SELECT * FROM saved_redistricting WHERE username = ?1", nativeQuery = true)
    List<Redistrict> findByUsername(String username);
}
