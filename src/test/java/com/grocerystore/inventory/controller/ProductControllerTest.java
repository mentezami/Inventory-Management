package com.grocerystore.inventory.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.grocerystore.inventory.model.Product;
import com.grocerystore.inventory.service.ProductService;
import com.grocerystore.inventory.contorller.ProductController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@WebMvcTest(ProductController.class)
public class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private ProductService productService;

    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        objectMapper = new ObjectMapper();
    }

    @Test
    public void testGetProductById() throws Exception{
        // given:
        Long productId = 1L;
        Product mockProduct = new Product();
        mockProduct.setId(productId);
        mockProduct.setUpc(10000001L);
        mockProduct.setName("Apple");
        mockProduct.setPrice(1.2);
        mockProduct.setInventoryCount(2);

        // when:
        when(productService.getProductById(1L)).thenReturn(mockProduct);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/api/products/{id}", productId)
                .accept(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();
        Product responseProduct = objectMapper.readValue(response.getContentAsString(), Product.class);

        // then:
        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertThat(responseProduct.getId()).isEqualTo(mockProduct.getId());
        assertThat(responseProduct.getUpc()).isEqualTo(mockProduct.getUpc());
        assertThat(responseProduct.getName()).isEqualTo(mockProduct.getName());
        assertThat(responseProduct.getPrice()).isEqualTo(mockProduct.getPrice());
        assertThat(responseProduct.getInventoryCount()).isEqualTo(mockProduct.getInventoryCount());
    }
}