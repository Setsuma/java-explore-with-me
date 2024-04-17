package ru.practicum.ewm.entity.event.comment.repository;

import lombok.NonNull;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.practicum.ewm.entity.event.comment.entity.Comment;
import ru.practicum.ewm.entity.event.comment.exception.CommentNotFoundException;

import java.util.List;

@Repository
public interface CommentJpaRepository extends JpaRepository<Comment, Long> {

    List<Comment> findAllByEventId(Long eventId, Pageable pageable);

    default void checkCommentExistsById(@NonNull Long commentId) {
        if (!existsById(commentId)) {
            throw CommentNotFoundException.fromCommentId(commentId);
        }
    }
}