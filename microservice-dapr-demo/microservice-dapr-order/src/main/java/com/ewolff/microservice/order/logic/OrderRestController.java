package com.ewolff.microservice.order.logic;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

import com.ewolff.microservice.order.OrderFeed;
import com.ewolff.microservice.order.OrderFeedEntry;

import jakarta.servlet.http.HttpServletRequest;

@RestController
public class OrderRestController {

    private final Logger log = LoggerFactory.getLogger(OrderRestController.class);
    
    private String baseUrl = null;

    private OrderRepository orderRepository;

    public OrderRestController(OrderRepository orderRepository, @Value("${baseUrl}") String baseUrl) {
        this.orderRepository = orderRepository;
        this.baseUrl = baseUrl;
    }

    @RequestMapping(value = "/feed", produces = "application/json")
    public OrderFeed orderFeed(WebRequest webRequest, HttpServletRequest httpRequest) {
        if ((orderRepository.lastUpdate() != null)
                && (webRequest.checkNotModified(orderRepository.lastUpdate().getTime()))) {
            log.trace("Not Modified returned - request with If-Modified-Since {}",
                    webRequest.getHeader(HttpHeaders.IF_MODIFIED_SINCE));
            return null;
        }
        log.trace("Returned Feed");
        List<OrderFeedEntry> orderFeedEntries = new ArrayList<OrderFeedEntry>();
        for (Order order : orderRepository.findAll()) {
            orderFeedEntries.add(new OrderFeedEntry(order.getId(),
                    baseUrl + "order/" + Long.toString(order.getId()), order.getUpdated()));
        }
        OrderFeed orderFeed = new OrderFeed(orderRepository.lastUpdate());
        orderFeed.setOrders(orderFeedEntries);
        return orderFeed;
    }

}