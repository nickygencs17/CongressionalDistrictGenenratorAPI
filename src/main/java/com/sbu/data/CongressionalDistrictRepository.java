package com.sbu.data;
import com.sbu.data.entitys.CongressionalDistrict;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;

@Transactional
public interface CongressionalDistrictRepository extends CrudRepository<CongressionalDistrict, String> {


}
