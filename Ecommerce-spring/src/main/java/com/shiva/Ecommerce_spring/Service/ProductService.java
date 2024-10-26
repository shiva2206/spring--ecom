package com.shiva.Ecommerce_spring.Service;

import com.shiva.Ecommerce_spring.Model.Product;
import com.shiva.Ecommerce_spring.Repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository repo;

    public List<Product> getAllProducts(){
        return repo.findAll();
    }

    public Product getProductById(int id){
        return repo.findById(id).orElse(null);
    }

    public Product addProduct(Product product, MultipartFile imagePartFile) throws IOException {

        product.setImageName(imagePartFile.getName());
        product.setImageType(imagePartFile.getContentType());
        product.setImageData(imagePartFile.getBytes());

        return repo.save(product);
    }

    public Product updatedProduct(Product product,MultipartFile imagePartFile) throws IOException {

        byte[] updatedimageData = imagePartFile.getBytes();

        product.setImageName(imagePartFile.getName());
        product.setImageType(imagePartFile.getContentType());
        product.setImageData(imagePartFile.getBytes());

        return repo.save(product);
    }

    public String deleteProduct(int prodId) {
        Product product = repo.findById(prodId).orElse(null);
        if(product==null) return "Failed to delete";
        repo.deleteById(prodId);
        return "Successfully deleted";
    }

    public List<Product> searchProducts(String keyword) {
        List<Product> products = repo.searchProduct(keyword);
        return products;
    }
}
