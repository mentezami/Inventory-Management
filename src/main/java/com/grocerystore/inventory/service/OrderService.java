package com.grocerystore.inventory.service;

import com.grocerystore.inventory.execption.ResourceNotFoundException;
import com.grocerystore.inventory.model.Order;
import com.grocerystore.inventory.model.Product;
import com.grocerystore.inventory.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductService productService;

    @Cacheable(value = "orders", key = "#id")
    public Order getOrderById(Long id) {
        log.debug("Fetching order with ID: {}", id);
        return orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order with ID: " + id + " not found."));
    }

    @Transactional
    @CachePut(value = "orders", key = "#order.id")
    public Order createOrder(Order order) {
        return orderRepository.save(order);
    }


    @Transactional
    @CachePut(value = "orders", key = "#orderId")
    public Order updateOrder(Long orderId, Order pathcedOrder) {
        if (orderId == null) {
            throw new IllegalArgumentException("Order ID cannot be null for update.");
        }
        Order existingOrder = getOrderById(orderId);

        if (pathcedOrder.getTotalPrice() > 0) {
            existingOrder.setTotalPrice(pathcedOrder.getTotalPrice());
        }

        if (pathcedOrder.getCashierId() != null) {
            existingOrder.setCashierId(pathcedOrder.getCashierId());
        }

        if (pathcedOrder.getItems() != null && !pathcedOrder.getItems().isEmpty()) {
            existingOrder.setItems(pathcedOrder.getItems());
        }
        log.info("Updated order with ID: {}", orderId);
        return orderRepository.save(pathcedOrder);
    }

    @Transactional
    @CachePut(value = "orders", key = "#orderId")
    public Order addProductToOrder(Long orderId, Long upc, int quantity) {
        Order order = getOrderById(orderId);
        Product product = productService.getProductByUpc(upc);

        if (product == null) {
            throw new ResourceNotFoundException("Product with UPC: " + upc + " not found.");
        }

        Map<Long, Integer> items = order.getItems();
        items.put(product.getId(), quantity);
        order.setItems(items);
        order.setTotalPrice(calculateTotalPrice(order));

        return orderRepository.save(order);
    }

    @Cacheable(value = "orderTotalPrice", key = "#id")
    public double getTotalPrice(Long id) {
        log.debug("Calculating total price for order ID: {}", id);
        Order order = getOrderById(id);
        return calculateTotalPrice(order);
    }

    @CacheEvict(value = "orders", key = "#id")
    public void deleteOrder(Long id) {
        log.debug("Fetching order with ID: {}", id);
        if (!orderRepository.existsById(id)) {
            log.error("Failed to delete order with id {}", id);
            throw new ResourceNotFoundException("Order with ID: " + id + " not found.");
        }
        log.info("Deleting order with ID: {}", id);
        orderRepository.deleteById(id);
    }

    private double calculateTotalPrice(Order order) {
        double totalPrice = 0;
        for (Map.Entry<Long, Integer> entry : order.getItems().entrySet()) {
            Product product = productService.getProductById(entry.getKey());
            if (product != null) {
                totalPrice += product.getPrice() * entry.getValue();
            }
        }
        return totalPrice;
    }
}