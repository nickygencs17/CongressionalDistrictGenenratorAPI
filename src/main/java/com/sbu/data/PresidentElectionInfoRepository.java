package com.sbu.data;
import com.sbu.data.entitys.PresidentElectionInfo;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;

@Transactional
public interface PresidentElectionInfoRepository extends CrudRepository<PresidentElectionInfo, Integer> {


}
