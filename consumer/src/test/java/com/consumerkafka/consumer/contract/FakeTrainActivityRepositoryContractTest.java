package com.consumerkafka.consumer.contract;

import com.consumerkafka.consumer.core.port.TrainActivityRepository;
import com.consumerkafka.consumer.core.fake.FakeTrainActivityRepository;

class FakeTrainActivityRepositoryContractTest extends TrainActivityRepositoryContract {

    private FakeTrainActivityRepository repository;

    @Override
    protected TrainActivityRepository createSubject() {
        repository = new FakeTrainActivityRepository();
        return repository;
    }

    @Override
    protected void clearData() {
        if (repository != null) {
            repository.clear();
        }
    }
}


