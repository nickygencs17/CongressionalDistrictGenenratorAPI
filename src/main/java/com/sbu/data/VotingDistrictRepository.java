package com.sbu.data;
import com.sbu.data.entitys.VotingDistrict;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
public interface VotingDistrictRepository extends CrudRepository<VotingDistrict, String> {
    List<VotingDistrict> findByVd_idStartingWith(String vd_id);

}
