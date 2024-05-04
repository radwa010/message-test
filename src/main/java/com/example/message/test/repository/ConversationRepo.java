package com.example.message.test.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.message.test.model.Conversation;
@Repository
public interface ConversationRepo extends JpaRepository<Conversation, Long> {

}
