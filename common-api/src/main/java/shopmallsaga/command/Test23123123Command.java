package shopmallsaga.command;

import java.util.List;
import lombok.Data;
import lombok.ToString;
import org.axonframework.modelling.command.TargetAggregateIdentifier;
import shopmallsaga.query.*;

@ToString
@Data
public class Test23123123Command {

    @TargetAggregateIdentifier
    private Long id;
}
