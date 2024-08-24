package com.qlish.qlish_api.config;

import io.mongock.api.annotations.ChangeUnit;
import io.mongock.api.annotations.Execution;
import io.mongock.api.annotations.RollbackExecution;
import lombok.RequiredArgsConstructor;
import org.bson.Document;
import org.springframework.data.mongodb.core.MongoTemplate;

@RequiredArgsConstructor
@ChangeUnit(id = "client-initializer", order = "001", author = "mongock")
public class ClientInitializerChange {

    private final MongoTemplate mongoTemplate;
    private final ThirPartyService thirdPartyService;

    /** This is the method with the migration code **/
    @Execution
    public void changeSet() {
        thirdPartyService.getData()
                .stream()
                .forEach(client -> mongoTemplate.save(client, CLIENTS_COLLECTION_NAME));
    }
    /**
     This method is mandatory even when transactions are enabled.
     They are used in the undo operation and any other scenario where transactions are not an option.
     However, note that when transactions are avialble and Mongock need to rollback, this method is ignored.
     **/
    @RollbackExecution
    public void rollback() {
        mongoTemplate.remove(new Document());
    }

}
