package ru.bot.romanmessageapibot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.bot.romanmessageapibot.entity.Domain;

@Repository
public interface DomainRepository extends JpaRepository<Domain,Long> {

    long count();
}
