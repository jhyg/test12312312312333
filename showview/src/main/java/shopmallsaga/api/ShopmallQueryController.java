package shopmallsaga.api;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import shopmallsaga.query.*;

@RestController
public class ShopmallQueryController {

    private final QueryGateway queryGateway;

    public ShopmallQueryController(QueryGateway queryGateway) {
        this.queryGateway = queryGateway;
    }

    @GetMapping("/shopmalls")
    public CompletableFuture findAll() {
        return queryGateway
            .query(
                new ShopmallQuery(),
                ResponseTypes.multipleInstancesOf(Shopmall.class)
            )
            .thenApply(resources -> {
                CollectionModel<Shopmall> model = CollectionModel.of(resources);

                return new ResponseEntity<>(model, HttpStatus.OK);
            });
    }

    @GetMapping("/shopmalls/{id}")
    public CompletableFuture findById(@PathVariable("id") Long id) {
        ShopmallSingleQuery query = new ShopmallSingleQuery();
        query.setId(id);

        return queryGateway
            .query(query, ResponseTypes.optionalInstanceOf(Shopmall.class))
            .thenApply(resource -> {
                if (!resource.isPresent()) {
                    return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
                }

                EntityModel<Shopmall> model = EntityModel.of(resource.get());
                model.add(
                    Link
                        .of("/shopmalls/" + resource.get().getId())
                        .withSelfRel()
                );

                return new ResponseEntity<>(model, HttpStatus.OK);
            })
            .exceptionally(ex -> {
                throw new RuntimeException(ex);
            });
    }
}
