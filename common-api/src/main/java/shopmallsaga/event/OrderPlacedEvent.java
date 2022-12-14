package shopmallsaga.event;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class OrderPlacedEvent {

    private Long id;
    private Long productId;
    private Integer qty;
    private String productName;
}
