package com.server.master.repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.server.master.models.TrainEntry;

public interface TrainRepository extends CrudRepository<TrainEntry, Integer> {


}
