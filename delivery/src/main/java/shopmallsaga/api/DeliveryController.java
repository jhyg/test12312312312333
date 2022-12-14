package shopmallsaga.api;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.queryhandling.QueryGateway;

import org.axonframework.eventsourcing.eventstore.EventStore;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.axonframework.eventsourcing.eventstore.EventStore;

import org.springframework.beans.BeanUtils;


import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpEntity;

import java.util.concurrent.CompletableFuture;
import java.util.ArrayList;


import shopmallsaga.aggregate.*;
import shopmallsaga.command.*;

@RestController
public class DeliveryController {

  private final CommandGateway commandGateway;
  private final QueryGateway queryGateway;

  public DeliveryController(CommandGateway commandGateway, QueryGateway queryGateway) {
      this.commandGateway = commandGateway;
      this.queryGateway = queryGateway;
  }


  @Autowired
  EventStore eventStore;

  @GetMapping(value="/deliveries/{id}/events")
  public ResponseEntity getEvents(@PathVariable("id")  id){
      ArrayList resources = new ArrayList<DeliveryAggregate>(); 
      eventStore.readEvents(id).asStream().forEach(resources::add);

      CollectionModel<DeliveryAggregate> model = CollectionModel.of(resources);
                
      return new ResponseEntity<>(model, HttpStatus.OK);
  } 


}
