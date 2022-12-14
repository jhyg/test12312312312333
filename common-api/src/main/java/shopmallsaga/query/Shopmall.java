package shopmallsaga.query;

import java.util.Date;
import java.util.List;
import javax.persistence.*;
import lombok.Data;
import org.springframework.hateoas.server.core.Relation;

@Entity
@Table(name = "Shopmall_table")
@Data
@Relation(collectionRelation = "shopmalls")
public class Shopmall {

    @Id
    //@GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;

    private Long orderId;
    private Long productId;
    private Integer qty;
    private Long deliveryId;
}
