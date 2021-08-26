package com.terry.blog.domain.posts;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostsRepository extends JpaRepository<Posts, Long> {
    @Query("SELECT p FROM Posts p ORDER BY p.id DESC")
    List<Posts> findAllDesc();

    @Query("SELECT p FROM Posts p WHERE p.content LIKE CONCAT('%',:keyword,'%')")
    List<Posts> searchPosts(@Param("keyword") String searchKeyword);
}
