package shopmallsaga.aggregate;

import static org.axonframework.modelling.command.AggregateLifecycle.*;

import java.util.List;
import lombok.Data;
import lombok.ToString;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.beans.BeanUtils;
import shopmallsaga.command.*;
import shopmallsaga.event.*;

@Aggregate
@Data
@ToString
public class DeliveryAggregate {

    @AggregateIdentifier
    private Long id;

    private Long orderId;
    private Long productId;
    private String productName;

    public DeliveryAggregate() {}

    @EventSourcingHandler
    public void on(DeliveryStartedEvent event) {
        BeanUtils.copyProperties(event, this);
    }

    @EventSourcingHandler
    public void on(DeliveryCancelledEvent event) {
        BeanUtils.copyProperties(event, this);
    }
}
