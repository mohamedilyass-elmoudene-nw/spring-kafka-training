package com.consumerkafka.consumer.core.fake;

import com.consumerkafka.consumer.core.domain.model.TrainService;
import com.consumerkafka.consumer.core.port.TrainServiceRepository;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

public class FakeTrainServiceRepository implements TrainServiceRepository {

    private final Map<Integer, TrainService> byId = new LinkedHashMap<>();
    private final Map<String, Integer> rsidIndex = new HashMap<>();
    private int sequence = 1;

    @Override
    public TrainService save(TrainService service) {
        Integer id = service.getId() != null ? service.getId() : sequence++;
        TrainService persisted = service.withId(id);
        byId.put(id, persisted);
        if (persisted.getRsid() != null) {
            rsidIndex.put(persisted.getRsid(), id);
        }
        return persisted;
    }

    @Override
    public Optional<TrainService> findById(Integer id) {
        return Optional.ofNullable(byId.get(id));
    }

    @Override
    public Optional<TrainService> findByRsid(String rsid) {
        if (rsid == null) {
            return Optional.empty();
        }
        Integer id = rsidIndex.get(rsid);
        return id == null ? Optional.empty() : findById(id);
    }

    public void clear() {
        byId.clear();
        rsidIndex.clear();
        sequence = 1;
    }

    public int size() {
        return byId.size();
    }
}


