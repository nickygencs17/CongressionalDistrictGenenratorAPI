package com.sbu.data;
import com.sbu.data.entitys.AppUser;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;

@Transactional
public interface AppUserRepository extends CrudRepository<AppUser, String> {

}
