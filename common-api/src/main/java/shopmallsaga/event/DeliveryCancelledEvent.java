package shopmallsaga.event;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class DeliveryCancelledEvent {

    private Long id;
    private Long orderId;
}
