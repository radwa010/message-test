package com.example.message.test.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.message.test.model.Reaction;

public interface ReactionRepo extends JpaRepository<Reaction, Long> {

}
