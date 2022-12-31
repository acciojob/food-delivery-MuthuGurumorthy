package com.driver.ui.controller;

import java.util.ArrayList;
import java.util.List;

import com.driver.model.request.OrderDetailsRequestModel;
import com.driver.model.response.OperationStatusModel;
import com.driver.model.response.OrderDetailsResponse;
import com.driver.service.OrderService;
import com.driver.service.impl.OrderServiceImpl;
import com.driver.shared.dto.OrderDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orders")
public class OrderController {
	@Autowired
	OrderServiceImpl orderService;
	@GetMapping(path="/{id}")
	public OrderDetailsResponse getOrder(@PathVariable String id) throws Exception{
		OrderDto orderDto = orderService.getOrderById(id);
		return dtoConverter(orderDto);
	}
	
	@PostMapping()
	public OrderDetailsResponse createOrder(@RequestBody OrderDetailsRequestModel order) {
		OrderDto orderDto = requestConverter(order);
		OrderDto respondedOrderDto = orderService.createOrder(orderDto);
		return dtoConverter(respondedOrderDto);
	}
		
	@PutMapping(path="/{id}")
	public OrderDetailsResponse updateOrder(@PathVariable String id, @RequestBody OrderDetailsRequestModel order) throws Exception{
		OrderDto orderDto = requestConverter(order);
		OrderDto OrderDtoResponse = orderService.updateOrderDetails(id,orderDto);
		return dtoConverter(OrderDtoResponse);
	}
	
	@DeleteMapping(path = "/{id}")
	public OperationStatusModel deleteOrder(@PathVariable String id) throws Exception {
		orderService.deleteOrder(id);
		OperationStatusModel operationStatusModel = new OperationStatusModel();
		operationStatusModel.setOperationName("Delete "+id);
		operationStatusModel.setOperationResult(id+" deleted successfully");
		return operationStatusModel;
	}
	
	@GetMapping()
	public List<OrderDetailsResponse> getOrders() {
		List<OrderDto> orderDtoList = orderService.getOrders();
		List<OrderDetailsResponse> orderDetailsResponseList = new ArrayList<>();
		for(OrderDto orderDto : orderDtoList){
			orderDetailsResponseList.add(dtoConverter(orderDto));
		}
		return orderDetailsResponseList;
	}

	public OrderDetailsResponse dtoConverter(OrderDto orderDto){
		return OrderDetailsResponse.builder().
				orderId(orderDto.getOrderId()).
				cost(orderDto.getCost()).
				items(orderDto.getItems()).
				userId(orderDto.getUserId()).
				status(orderDto.isStatus()).build();
	}

	public OrderDto requestConverter(OrderDetailsRequestModel requestModel){
		return OrderDto.builder().
				userId(requestModel.getUserId()).
				cost(requestModel.getCost()).
				items(requestModel.getItems()).build();
	}
}
