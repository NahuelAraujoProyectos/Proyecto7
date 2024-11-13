package com.name.productstorage.controller;

import com.name.productstorage.model.Product;
import com.name.productstorage.service.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProductController.class)  // Especificamos la clase de controlador que queremos probar
public class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ProductController productController;

    @MockBean
    private ProductService productService;

    @Test
    void getProductById_test() throws Exception {
        // Given: se preparan datos ficticios
        Product product = new Product(1, "Coche", "Deportivo", 10.0);

        // Simula que el service devuelve el producto para el ID 1
        when(productService.getProductById(1)).thenReturn(Optional.of(product));

        // Realizar la petición GET y verifica que el resultado es el esperado
        mockMvc.perform(get("/products/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Coche"))
                .andExpect(jsonPath("$.description").value("Deportivo"))
                .andExpect(jsonPath("$.price").value(10.0));
    }

    @Test
    void getAllProducts_test() throws Exception {
        // Given: se preparan datos ficticios
        Product product1 = new Product(1, "Coche", "Deportivo", 10.0);
        Product product2 = new Product(2, "Moto", "Clásica", 20.0);
        List<Product> listProducts = Arrays.asList(product1,product2);

        // Simula que el service devuelve una lista de products
        when(productService.getAllProducts()).thenReturn(listProducts);

        // Realizar la petición GET y verifica que el resultado es el esperado
        mockMvc.perform(get("/products"))
                .andExpect(status().isOk())  // Esperamos un código de estado 200 OK
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("Coche"))
                .andExpect(jsonPath("$[0].description").value("Deportivo"))
                .andExpect(jsonPath("$[0].price").value(10.0))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].name").value("Moto"))
                .andExpect(jsonPath("$[1].description").value("Clásica"))
                .andExpect(jsonPath("$[1].price").value(20.0));
    }
}
