package com.sbu.data;

/**
 * Created by nicholasgenco on 4/24/17.
 */

import com.sbu.data.entitys.CongressElectionInfo;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;


@Transactional
public interface CongressElectionInfoRepository extends CrudRepository<CongressElectionInfo, Integer> {


}
