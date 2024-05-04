package com.example.message.test.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.message.test.model.Participant;

public interface ParticipantRepo extends JpaRepository<Participant, Long> {

}
