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
public class OrderAggregate {

    @AggregateIdentifier
    private Long id;

    private Long productId;
    private Integer qty;
    private String productName;

    public OrderAggregate() {}

    @CommandHandler
    public OrderAggregate(StartOrderCommand command) {}

    @CommandHandler
    public void handle(CancelOrderCommand command) {}

    @CommandHandler
    public void handle(Test23123123Command command) {}

    @EventSourcingHandler
    public void on(OrderPlacedEvent event) {
        BeanUtils.copyProperties(event, this);
    }

    @EventSourcingHandler
    public void on(Test123Event event) {
        BeanUtils.copyProperties(event, this);
    }
}
