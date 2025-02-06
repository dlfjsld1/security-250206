package com.example.securityExam.domain.post.post.repository;

import com.example.securityExam.domain.member.member.entity.Member;
import com.example.securityExam.domain.post.post.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {
    Optional<Post> findTopByOrderByIdDesc();
    Page<Post> findByListed(boolean listed, PageRequest p);
    Page<Post> findByListedAndTitleLike(boolean b, String k, PageRequest p);
    Page<Post> findByListedAndContentLike(boolean b, String k, PageRequest p);

    Page<Post> findByAuthorAndTitleLike(Member author, String likeKeyword, PageRequest pageRequest);
    Page<Post> findByAuthorAndContentLike(Member author, String likeKeyword, PageRequest pageRequest);
}
