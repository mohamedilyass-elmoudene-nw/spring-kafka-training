package com.consumerkafka.consumer.contract;

import com.consumerkafka.consumer.core.domain.model.TimeDimension;
import com.consumerkafka.consumer.core.domain.model.TrainService;
import com.consumerkafka.consumer.core.port.TrainActivityRepository;
import com.consumerkafka.consumer.infrastructure.persistence.adapter.JpaTrainActivityAdapter;
import com.consumerkafka.consumer.infrastructure.persistence.adapter.JpaTimeDimensionAdapter;
import com.consumerkafka.consumer.infrastructure.persistence.adapter.JpaTrainServiceAdapter;
import com.consumerkafka.consumer.infrastructure.persistence.jpa.JpaTrainActivityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@SpringBootTest(properties = "spring.kafka.listener.auto-startup=false")
@Testcontainers
class JpaTrainActivityRepositoryContractTest extends TrainActivityRepositoryContract {

    @Container
    @SuppressWarnings("resource")
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:16-alpine")
        .withDatabaseName("consumer")
        .withUsername("test")
        .withPassword("test");

    @DynamicPropertySource
    static void overrideDataSource(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
        registry.add("spring.liquibase.url", postgres::getJdbcUrl);
        registry.add("spring.liquibase.user", postgres::getUsername);
        registry.add("spring.liquibase.password", postgres::getPassword);
        registry.add("spring.jpa.hibernate.ddl-auto", () -> "validate");
    }

    @Autowired
    private JpaTrainActivityAdapter adapter;

    @Autowired
    private JpaTrainActivityRepository jpaRepository;

    @Autowired
    private JpaTrainServiceAdapter trainServiceAdapter;

    @Autowired
    private JpaTimeDimensionAdapter timeDimensionAdapter;

    @Override
    protected TrainActivityRepository createSubject() {
        return adapter;
    }

    @Override
    protected void clearData() {
        jpaRepository.deleteAll();
    }

    @Override
    protected TrainService ensureTrainService(TrainService service) {
        return trainServiceAdapter.save(service);
    }

    @Override
    protected TimeDimension ensureTimeDimension(TimeDimension timeDimension) {
        return timeDimensionAdapter.save(timeDimension);
    }
}


