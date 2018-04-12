package com.sbu.data;
import com.sbu.data.entitys.BlogPost;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;

@Transactional
public interface BlogPostRepository extends CrudRepository<BlogPost, Integer> {

}
