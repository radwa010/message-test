package com.example.message.test.serviceImpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.example.message.test.model.Conversation;
import com.example.message.test.model.Message;
import com.example.message.test.repository.ConversationRepo;
import com.example.message.test.repository.MessageRepo;
import com.example.message.test.service.MessageService;
import com.example.message.test.util.ExportMessagesReportUtil;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class MessageServiceImpl implements MessageService {

	private final ConversationRepo conversationRepo;

	private final MessageRepo messageRepo;

	@Override
	public String getMessagesPerDayReport() {

		// find all coversations
		List<Long> conversations = conversationRepo.findAll().stream().map(Conversation::getId).toList();
		// find list of massages by conversation id
		List<Message> messages = messageRepo.findAllByConversationIdIn(conversations);
		// create map with date as key and number of message per day as value
		Map<String, Integer> messagesPerDay = new HashMap<>();
		messages.stream().forEach(m -> {
			String date = m.getCreateAt().toString();
			messagesPerDay.put(date, messagesPerDay.getOrDefault(date, 0) + 1);
		});
		// generate excel file
		ExportMessagesReportUtil.generateMessagePerDayReport(messagesPerDay);
		return "Export Done";
	}

}
