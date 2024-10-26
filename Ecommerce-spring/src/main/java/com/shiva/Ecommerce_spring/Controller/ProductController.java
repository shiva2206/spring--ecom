package com.shiva.Ecommerce_spring.Controller;


import com.shiva.Ecommerce_spring.Model.Product;
import com.shiva.Ecommerce_spring.Service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin()
public class ProductController {

    @Autowired
    private ProductService service;

    @RequestMapping("/")
    public String greet(){
        return "Hello World";
    }

    @GetMapping("/products")
    public ResponseEntity<List<Product>> getAllProducts(){
        return new ResponseEntity<>(service.getAllProducts(), HttpStatus.OK);
    }
    @GetMapping("/product/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable int id){
        Product product = service.getProductById(id);
        if(product==null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(product,HttpStatus.OK);
    }

    @PostMapping("/product")
    public ResponseEntity<?> addProduct(@RequestPart Product product, @RequestPart MultipartFile imageFile ){

        try{
            Product addedProduct = service.addProduct(product,imageFile);
            return new ResponseEntity<>(addedProduct,HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/product/{prodId}/image")
    public ResponseEntity<byte[]> getProductImage(@PathVariable int prodId){
        Product product = service.getProductById(prodId);
        byte[] imageData = product.getImageData();
        return ResponseEntity.ok()
                .contentType(MediaType.valueOf(product.getImageType()))
                .body(imageData);
    }


    @PutMapping("/product/{prodId}")
    public ResponseEntity<String> updateProduct(@PathVariable int prodId,
                                                 @RequestPart Product product,
                                                 @RequestPart MultipartFile imageFile){
        Product updatedProduct = null;
        try{
            updatedProduct =  service.updatedProduct(product,imageFile);
        }catch(Exception e){
            return new ResponseEntity<>("Failed to update",HttpStatus.NOT_FOUND);
        }
        if(updatedProduct==null) {
            return new ResponseEntity<>("Failed to update", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>("Successfully updated",HttpStatus.OK);

    }

    @DeleteMapping("/product/{prodId}")
    public ResponseEntity<String> deleteProduct(@PathVariable int prodId){
        String response = service.deleteProduct(prodId);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @GetMapping("/products/search")
    public ResponseEntity<List<Product>> searchProducts(@RequestParam String keyword){
        List<Product> products = service.searchProducts(keyword);
        return new ResponseEntity<>(products,HttpStatus.OK);
    }
}
