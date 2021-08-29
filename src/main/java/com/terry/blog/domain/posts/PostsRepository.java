package com.terry.blog.domain.posts;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostsRepository extends JpaRepository<Posts, Long> {
    @Query("SELECT p FROM Posts p ORDER BY p.id DESC")
    List<Posts> findAllDesc();

    @Query(nativeQuery = true, value = "SELECT * FROM posts p WHERE p.kategorie LIKE CONCAT('%',?1,'%')")
    Page<Posts> viewKategorie(@Param("kategorie") String searchKeyword, @Param("pageRequest")PageRequest pageRequest);

    @Query(nativeQuery = true,value = "SELECT * FROM posts p ORDER BY p.view_ctn DESC Limit 5")
    List<Posts> hotviewList();

    @Query("SELECT p FROM Posts p WHERE p.content LIKE CONCAT('%',:keyword,'%')")
    List<Posts> searchPosts(@Param("keyword") String searchKeyword);

    @Modifying
    @Query("UPDATE Posts p SET viewCtn = viewCtn + 1 where p.id = :id")
    void viewCount(@Param("id") Long id);
}
