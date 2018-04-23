package com.sbu.data;
import com.sbu.data.entitys.Precinct;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
public interface PrecinctRepository extends CrudRepository<Precinct, String> {

    @Query(value = "SELECT * FROM precinct WHERE state_id = ?1",nativeQuery = true)
    List<Precinct> findByState_id(String state_id);

}
