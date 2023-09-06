package ru.bot.romanmessageapibot.entity;


import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;

@Entity
@Table(name = "messages")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MessageUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "message_id")
    long messageId;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_name",referencedColumnName = "user_id")
    BotUser user;

    @Column(name = "request", nullable = false, length = 2048)
    String request;
    @Column(name = "response", nullable = false, length = 2048)
    String response;
}
