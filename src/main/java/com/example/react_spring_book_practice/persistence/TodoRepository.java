package com.example.react_spring_book_practice.persistence;

import com.example.react_spring_book_practice.model.TodoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
// JpaRepository<T, ID> : Entity Class T의 primary key는 ID이다
public interface TodoRepository extends JpaRepository<TodoEntity, String> {
    List<TodoEntity> findByUserId(String userId);
}
