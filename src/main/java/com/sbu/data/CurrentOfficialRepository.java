package com.sbu.data;
import com.sbu.data.entitys.CurrentOfficial;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;

@Transactional
public interface CurrentOfficialRepository extends CrudRepository<CurrentOfficial, Integer> {


}
