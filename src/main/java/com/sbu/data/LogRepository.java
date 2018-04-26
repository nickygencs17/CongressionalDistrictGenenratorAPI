package com.sbu.data;

import com.sbu.data.entitys.Log;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;

@Transactional
public interface LogRepository extends CrudRepository<Log, Integer> {


}
