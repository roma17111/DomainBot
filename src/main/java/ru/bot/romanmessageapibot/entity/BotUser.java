package ru.bot.romanmessageapibot.entity;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BotUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    long userId;

    @Column(name = "chat_id", nullable = false)
    long chatId;

    @Column(name = "last_message_at", nullable = false)
    LocalDateTime lastMessageAt;
}
