package com.sbu.data;

/**
 * Created by nicholasgenco on 4/24/17.
 */

import com.sbu.data.entitys.BlogPost;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;


@Transactional
public interface BlogPostRepository extends CrudRepository<BlogPost, Integer> {

}
