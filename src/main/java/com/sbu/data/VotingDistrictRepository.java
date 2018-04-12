package com.sbu.data;
import com.sbu.data.entitys.VotingDistrict;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;

@Transactional
public interface VotingDistrictRepository extends CrudRepository<VotingDistrict, String> {


}
