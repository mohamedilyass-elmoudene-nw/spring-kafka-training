package com.consumerkafka.consumer.core.fake;

import com.consumerkafka.consumer.core.domain.model.TimeDimension;
import com.consumerkafka.consumer.core.port.TimeDimensionRepository;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

public class FakeTimeDimensionRepository implements TimeDimensionRepository {

    private final Map<Integer, TimeDimension> store = new LinkedHashMap<>();

    @Override
    public TimeDimension save(TimeDimension timeDimension) {
        store.put(timeDimension.getDateId(), timeDimension);
        return timeDimension;
    }

    @Override
    public Optional<TimeDimension> findByDateId(int dateId) {
        return Optional.ofNullable(store.get(dateId));
    }

    public void clear() {
        store.clear();
    }

    public int size() {
        return store.size();
    }
}


