//package com.vinhlam.redis.controller;
//
//import java.util.List;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.DeleteMapping;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.vinhlam.redis.entity.Product;
//import com.vinhlam.redis.repository.ProductRepository2;
//
//@RestController
//@RequestMapping("/products")
//public class ProductController2 {
//	
//	@Autowired
//	private ProductRepository2 productRepository2;
//	
//	@PostMapping("/create")
//	public String create(@RequestBody Product product) {
//		productRepository2.save(product);
//		return "Create success";
//	}
//	
//	@GetMapping("/")
//	public List<Product> getAllProducts() {
//		List<Product> products = productRepository2.findAll();
//		return products;
//	} 
//	
//	@GetMapping("/{id}")
//	public Product getProductById(@PathVariable String id) {
//		Product p = productRepository2.findById(id).orElse(null);
//		return p;
//	}
//	
//	@DeleteMapping("/{id}")
//	public String deleteProductById(@PathVariable String id) {
//		productRepository2.deleteById(id);
//		return "Delete success";
//	}
//	
//	@PostMapping("/{id}")
//	public String updateProductById(@RequestBody Product product) {
//		productRepository2.save(product);
//		return "Update success";
//	}
//}
