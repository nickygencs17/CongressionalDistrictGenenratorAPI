package com.sbu.data;

import com.sbu.data.entitys.CongressElectionInfo;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;

@Transactional
public interface CongressElectionInfoRepository extends CrudRepository<CongressElectionInfo, Integer> {
}
