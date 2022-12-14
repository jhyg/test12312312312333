package shopmallsaga.api;

import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.eventsourcing.eventstore.EventStore;
import org.axonframework.eventsourcing.eventstore.EventStore;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import shopmallsaga.aggregate.*;
import shopmallsaga.command.*;

@RestController
public class OrderController {

    private final CommandGateway commandGateway;
    private final QueryGateway queryGateway;

    public OrderController(
        CommandGateway commandGateway,
        QueryGateway queryGateway
    ) {
        this.commandGateway = commandGateway;
        this.queryGateway = queryGateway;
    }

    @RequestMapping(value = "/orders", method = RequestMethod.POST)
    public CompletableFuture startOrder(
        @RequestBody StartOrderCommand startOrderCommand
    ) throws Exception {
        System.out.println("##### /order/startOrder  called #####");

        // send command
        return commandGateway
            .send(startOrderCommand)
            .thenApply(id -> {
                OrderAggregate resource = new OrderAggregate();
                BeanUtils.copyProperties(startOrderCommand, resource);

                resource.setId((Long) id);

                EntityModel<OrderAggregate> model = EntityModel.of(resource);
                model.add(Link.of("/orders/" + resource.getId()).withSelfRel());

                return new ResponseEntity<>(model, HttpStatus.OK);
            });
    }

    @RequestMapping(value = "/orders", method = RequestMethod.DELETE)
    public CompletableFuture cancelOrder(
        @RequestBody CancelOrderCommand cancelOrderCommand
    ) throws Exception {
        System.out.println("##### /order/cancelOrder  called #####");

        // send command
        return commandGateway
            .send(cancelOrderCommand)
            .thenApply(id -> {
                OrderAggregate resource = new OrderAggregate();
                BeanUtils.copyProperties(cancelOrderCommand, resource);

                resource.setId((Long) id);

                EntityModel<OrderAggregate> model = EntityModel.of(resource);
                model.add(Link.of("/orders/" + resource.getId()).withSelfRel());

                return new ResponseEntity<>(model, HttpStatus.OK);
            });
    }

    @RequestMapping(
        value = "/orders/{id}/test23123123",
        method = RequestMethod.PUT,
        produces = "application/json;charset=UTF-8"
    )
    public CompletableFuture test23123123(
        @PathVariable("id") Long id,
        @RequestBody Test23123123Command test23123123Command
    ) throws Exception {
        System.out.println("##### /order/test23123123  called #####");
        test23123123Command.setId(id);
        // send command
        return commandGateway.send(test23123123Command);
    }

    @Autowired
    EventStore eventStore;

    @GetMapping(value = "/orders/{id}/events")
    public ResponseEntity getEvents(@PathVariable("id") Long id) {
        ArrayList resources = new ArrayList<OrderAggregate>();
        eventStore.readEvents(id).asStream().forEach(resources::add);

        CollectionModel<OrderAggregate> model = CollectionModel.of(resources);

        return new ResponseEntity<>(model, HttpStatus.OK);
    }
}
