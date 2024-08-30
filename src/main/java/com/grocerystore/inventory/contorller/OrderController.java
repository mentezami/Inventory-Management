package com.grocerystore.inventory.contorller;

import com.grocerystore.inventory.model.Order;
import com.grocerystore.inventory.service.OrderService;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.DeleteMapping;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable Long id) {
        Order order = orderService.getOrderById(id);
        return ResponseEntity.ok(order);
    }

    @GetMapping("/{id}/total_price")
    public ResponseEntity<Double> getTotalPrice(@PathVariable Long id) {
        Double totalPrice = orderService.getTotalPrice(id);
        return ResponseEntity.ok(totalPrice);
    }

    @PostMapping("/create")
    public ResponseEntity<Order> createOrder(@RequestBody Order order) {
        Order createdOrder = orderService.createOrder(order);
        return new ResponseEntity<>(createdOrder, HttpStatus.CREATED);
    }

    @PatchMapping("/update/{id}")
    public ResponseEntity<Order> updateOrder(@PathVariable Long id, @RequestBody Order patchedOrder) {
        Order updateOrder = orderService.updateOrder(id, patchedOrder);
        return new ResponseEntity<>(updateOrder, HttpStatus.OK);
    }

    @PatchMapping("/{orderId}/addProduct")
    public ResponseEntity<Order> addProductToOrder(@PathVariable Long orderId,
                                                   @RequestParam @Min(10000000) Long upc,
                                                   @RequestParam @Min(1) int quantity) {
        Order updateOrder = orderService.addProductToOrder(orderId, upc, quantity);
        return new ResponseEntity<>(updateOrder, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> addProductToOrder(@PathVariable Long id) {
        orderService.deleteOrder(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}