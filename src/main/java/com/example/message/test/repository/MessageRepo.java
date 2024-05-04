package com.example.message.test.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.message.test.model.Message;

public interface MessageRepo extends JpaRepository<Message, Long> {
	public List<Message> findAllByConversationIdIn(List<Long> conversations);
}
