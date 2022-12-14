package shopmallsaga.event;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class DeliveryStartedEvent {

    private Long id;
    private Long orderId;
    private Long productId;
    private String productName;
}
