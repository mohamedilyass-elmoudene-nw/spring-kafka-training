package com.consumerkafka.consumer.core.fake;

import com.consumerkafka.consumer.core.domain.model.TrainActivity;
import com.consumerkafka.consumer.core.port.TrainActivityRepository;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class FakeTrainActivityRepository implements TrainActivityRepository {

    private final Map<Long, TrainActivity> store = new LinkedHashMap<>();
    private long sequence = 1L;

    @Override
    public TrainActivity save(TrainActivity activity) {
        Long id = activity.getId() != null ? activity.getId() : sequence++;
        TrainActivity persisted = activity.withId(id);
        store.put(id, persisted);
        return persisted;
    }

    @Override
    public Optional<TrainActivity> findById(Long id) {
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public List<TrainActivity> findAll() {
        return new ArrayList<>(store.values());
    }

    public void clear() {
        store.clear();
        sequence = 1L;
    }

    public int count() {
        return store.size();
    }
}


