package ru.bot.romanmessageapibot.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.bot.romanmessageapibot.dto.DomainDto;
import ru.bot.romanmessageapibot.entity.Domain;
import ru.bot.romanmessageapibot.exceptions.IncorrectUrlException;
import ru.bot.romanmessageapibot.repository.DomainRepository;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class BackOrderClient {

    private static final String url = "https://backorder.ru/json/?order=desc&expired=1&by=hotness&page=1&items=50";
    private static final List<DomainDto> collection1 = new ArrayList<>();
    private static final List<DomainDto> collection2 = new ArrayList<>();
    private static final List<DomainDto> collection3 = new ArrayList<>();
    private static final List<DomainDto> collection4 = new ArrayList<>();

    private final DomainRepository repository;

    public List<DomainDto> readAllDomains() {
        try {
            return new ObjectMapper().readValue(new URL(url), new TypeReference<List<DomainDto>>() {
            });
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            log.error("Ошибка парсинка url адреса");
            throw new IncorrectUrlException("Ошибка парсинга url адреса \n" + e.getMessage());
        }
    }


    public List<Domain> getAllDomains() {
        return Domain.mapAll(readAllDomains());
    }

    public long countDomains() {
        return repository.count();
    }

    private String getDateFormat() {
        LocalDate date = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return date.format(formatter);
    }

    public String getInfo() {
        return String.format("%s \n Собрано %d доменов.",
                getDateFormat(),
                countDomains());
    }

    private void addDomainsTOCollections() {
        List<DomainDto> domains = readAllDomains();
        int c1 = domains.size() / 4;
        int c2 = domains.size() / 4 * 2;
        int c3 = domains.size() / 4 * 3;
        int c4 = domains.size() / 4 * 4;
        for (int i = 0; i < c1; i++) {
            collection1.add(domains.get(i));
        }
        for (int i = c1; i < c2; i++) {
            collection2.add(domains.get(i));
        }
        for (int i = c2; i < c3; i++) {
            collection3.add(domains.get(i));
        }
        for (int i = c3; i < c4; i++) {
            collection4.add(domains.get(i));
        }
    }

    public void runCheckingDomains() {
        addDomainsTOCollections();
        Thread thread1 = new Thread() {
            public void run() {
                repository.saveAll(Domain.mapAll(collection1));

            }
        };
        Thread thread2 = new Thread() {
            public void run() {
                repository.saveAll(Domain.mapAll(collection2));

            }
        };
        Thread thread3 = new Thread() {
            public void run() {
                repository.saveAll(Domain.mapAll(collection3));

            }
        };
        Thread thread4 = new Thread() {
            public void run() {
                repository.saveAll(Domain.mapAll(collection4));

            }
        };
        thread1.start();
        thread2.start();
        thread3.start();
        thread4.start();
        try {
            thread1.join();
            thread2.join();
            thread3.join();
            thread4.join();

        } catch (InterruptedException e) {
            log.error(e.getMessage(),e);
            throw new RuntimeException(e);
        }
    }

    public void deleteAllDomains() {
        addDomainsTOCollections();
        System.out.println("collections added");
        Thread thread1 = new Thread() {
            public void run() {
                for (Domain domain : Domain.mapAll(collection1)) {
                    if (repository.findByDomainName(domain.getDomainName()) != null) {
                        repository.delete(domain);
                    }
                }
            }
        };
        Thread thread2 = new Thread() {
            public void run() {
                for (Domain domain : Domain.mapAll(collection2)) {
                    if (repository.findByDomainName(domain.getDomainName()) != null) {
                        repository.delete(domain);
                    }
                }

            }
        };
        Thread thread3 = new Thread() {
            public void run() {
                for (Domain domain : Domain.mapAll(collection3)) {
                    if (repository.findByDomainName(domain.getDomainName()) != null) {
                        repository.delete(domain);
                    }
                }
            }
        };
        Thread thread4 = new Thread() {
            public void run() {
                for (Domain domain : Domain.mapAll(collection4)) {
                    if (repository.findByDomainName(domain.getDomainName()) != null) {
                        repository.delete(domain);
                    }
                }
            }
        };
        thread1.start();
        thread2.start();
        thread3.start();
        thread4.start();
        try {
            thread1.join();
            thread2.join();
            thread3.join();
            thread4.join();

        } catch (InterruptedException e) {
            log.error(e.getMessage(),e);
            throw new RuntimeException(e);
        }
    }
}
