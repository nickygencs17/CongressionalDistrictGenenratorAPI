package com.sbu.data;

/**
 * Created by nicholasgenco on 4/24/17.
 */

import com.sbu.data.entitys.Comment;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;


@Transactional
public interface CommentRepository extends CrudRepository<Comment, Integer> {


    @Query(value = "SELECT * FROM comment WHERE post_id = ?1",nativeQuery = true)
    Iterable<Comment> findByPost_Id(String post_id);

}
