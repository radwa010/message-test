package com.example.message.test.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Table;

import javax.persistence.Id;

import lombok.Data;

@Data
@Entity
@Table(name = "reaction")
public class Reaction {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private Long messageId;
	private Long participantId;
	private String reactionType;
	private Date createAt;
}
