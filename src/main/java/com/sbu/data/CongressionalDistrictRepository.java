package com.sbu.data;

/**
 * Created by nicholasgenco on 4/24/17.
 */

import com.sbu.data.entitys.CongressionalDistrict;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;


@Transactional
public interface CongressionalDistrictRepository extends CrudRepository<CongressionalDistrict, String> {


}
