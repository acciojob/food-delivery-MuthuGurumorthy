package com.driver.service.impl;

import com.driver.io.entity.OrderEntity;
import com.driver.io.repository.OrderRepository;
import com.driver.service.OrderService;
import com.driver.shared.dto.OrderDto;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class OrderServiceImpl implements OrderService {
    @Autowired
    OrderRepository orderRepository;

    @Override
    public OrderDto createOrder(OrderDto orderDto) {
        OrderEntity orderEntity = dtoConverter(orderDto);
        orderRepository.save(orderEntity);
        return entityConverter(orderEntity);
    }

    @Override
    public OrderDto getOrderById(String orderId) throws Exception {
        OrderEntity orderEntity = orderRepository.findByOrderId(orderId);
        OrderDto orderDto = entityConverter(orderEntity);
        return orderDto;
    }

    @Override
    public List<OrderDto> getOrders() {
        List<OrderDto> orderDtoList = new ArrayList<>();
        List<OrderEntity> orderEntityList = new ArrayList<>();
        orderEntityList = (List<OrderEntity>) orderRepository.findAll();
        for (OrderEntity orderEntity : orderEntityList) {
            orderDtoList.add(entityConverter(orderEntity));
        }
        return orderDtoList;
    }

    @Override
    public OrderDto updateOrderDetails(String orderId, OrderDto orderDto) throws Exception {
        OrderEntity orderEntity = orderRepository.findByOrderId(orderId);
        orderEntity.setUserId(orderDto.getUserId());
        orderEntity.setCost(orderDto.getCost());
        orderEntity.setItems(orderDto.getItems());
        orderEntity.setStatus(orderDto.isStatus());
        orderRepository.save(orderEntity);
        return entityConverter(orderEntity);
    }

    @Override
    public void deleteOrder(String orderId) throws Exception {
        OrderEntity orderEntity = orderRepository.findByOrderId(orderId);
        orderRepository.delete(orderEntity);
    }

    private OrderDto entityConverter(OrderEntity orderEntity) {
        return OrderDto.builder().id(orderEntity.getId()).
                orderId(orderEntity.getOrderId()).
                cost(orderEntity.getCost()).
                items(orderEntity.getItems()).
                userId(orderEntity.getUserId()).
                status(orderEntity.isStatus()).build();
    }

    private OrderEntity dtoConverter(OrderDto orderDto) {
        return OrderEntity.builder().orderId(orderDto.getOrderId()).
                cost(orderDto.getCost()).
                items(orderDto.getItems()).
                userId(orderDto.getUserId()).
                status(orderDto.isStatus()).build();
    }
}