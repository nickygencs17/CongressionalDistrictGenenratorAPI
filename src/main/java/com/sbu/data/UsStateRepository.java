package com.sbu.data;
import com.sbu.data.entitys.UsState;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;

@Transactional
public interface UsStateRepository extends CrudRepository<UsState, String> {


}
