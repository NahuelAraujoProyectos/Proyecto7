package com.name.productstorage.service;

import com.name.productstorage.model.Product;
import com.name.productstorage.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {
    @InjectMocks
    private ProductService productService;

    @Mock
    private ProductRepository productRepository;

    @Test
    void test_getProductById(){
        // Given: se preparan datos ficticios
        Product product1 = new Product(1, "Product 1", "Description 1", 10.0);

        // Simula que el repositorio devuelve este producto cuando se busca con el ID 1
        when(productRepository.findById(1)).thenReturn(Optional.of(product1));

        // When: se llama al método del servicio con el ID 1
        Optional<Product> result = productService.getProductById(1);

        // Then: se verifica que el resultado es el esperado
        assertTrue(result.isPresent()); // Comprueba que el resultado no está vacío
        assertEquals(product1, result.get()); // Verifica que el producto devuelto es el esperado
    }

    @Test
    void test_getAllProducts(){
        // Given: se preparan datos ficiticios que simulan el comportamiento del repositorio
        Product product1 = new Product(1,"Coche","Deportivo",10.0);
        Product product2 = new Product(2,"Moto","Clásica",5.0);
        List<Product> listProducts = Arrays.asList(product1,product2);

        // Simula que el repositorio revuelve esta lista cuando se llama a findAll()
        when(productRepository.findAll()).thenReturn(listProducts);

        // When: se llama al método del servicio
        List<Product> result = productService.getAllProducts();

        // Then: se verifica que el resultado es el esperado
        assertEquals(2, result.size()); // Comprueba que la lista tiene dos elementos
        assertEquals(product1, result.get(0)); // Verifica que el primer elemento es product1
        assertEquals(product2, result.get(1)); // Verifica que el segundo elemento es product2
    }

}
