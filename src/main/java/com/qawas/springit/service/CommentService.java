package com.qawas.springit.service;

import com.qawas.springit.domain.Comment;
import com.qawas.springit.repository.CommentRepository;
import org.springframework.stereotype.Service;

@Service
public class CommentService {

    private final CommentRepository commentRepository;

    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }


    public Comment save(Comment comment) {
        return commentRepository.save(comment);
    }
}
