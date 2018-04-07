package com.sbu.data;

/**
 * Created by nicholasgenco on 4/24/17.
 */

import com.sbu.data.entitys.PresidentElectionInfo;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;


@Transactional
public interface PresidentElectionInfoRepository extends CrudRepository<PresidentElectionInfo, Integer> {


}
