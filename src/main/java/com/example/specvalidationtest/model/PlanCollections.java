package com.example.specvalidationtest.model;

import java.util.List;

public record PlanCollections(
    CollectionModel initialCollection,
    List<CollectionModel> regularCollections
) {
}
