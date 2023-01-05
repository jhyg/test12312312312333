package shopmallsaga.query;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import shopmallsaga.event.*;

@Service
@ProcessingGroup("shopmall")
public class ShopmallCQRSHandler {

    @Autowired
    private ShopmallRepository shopmallRepository;

    @QueryHandler
    public List<Shopmall> handle(ShopmallQuery query) {
        return shopmallRepository.findAll();
    }

    @EventHandler
    public void whenOrderPlaced_then_CREATE_1(OrderPlacedEvent orderPlaced)
        throws Exception {
        // view 객체 생성
        Shopmall shopmall = new Shopmall();
        // view 객체에 이벤트의 Value 를 set 함
        shopmall.setOrderId(orderPlaced.getId());
        shopmall.setProductId(orderPlaced.getProductId());
        shopmall.setQty(orderPlaced.getQty());
        // view 레파지 토리에 save
        shopmallRepository.save(shopmall);
    }

    @EventHandler
    public void whenDeliveryStarted_then_UPDATE_1(
        DeliveryStartedEvent deliveryStarted
    ) throws Exception {
        // view 객체 조회

        List<Shopmall> shopmallList = shopmallRepository.findByOrderId(
            deliveryStarted.getOrderId()
        );
        for (Shopmall shopmall : shopmallList) {
            // view 객체에 이벤트의 eventDirectValue 를 set 함
            shopmall.setDeliveryId(deliveryStarted.getId());
            // view 레파지 토리에 save
            shopmallRepository.save(shopmall);
        }
    }

    @EventHandler
    public void whenOrderCancelled_then_DELETE_(
        OrderCancelledEvent orderCancelled
    ) {
        // view 레파지 토리에 삭제 쿼리
        shopmallRepository.deleteByOrderId(orderCancelled.getId());
    }
}
