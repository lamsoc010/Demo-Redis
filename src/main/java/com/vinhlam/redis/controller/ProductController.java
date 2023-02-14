package com.vinhlam.redis.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vinhlam.redis.entity.Product;
import com.vinhlam.redis.repository.ProductCacheRepository;
import com.vinhlam.redis.repository.ProductRepository;

@RestController
@RequestMapping("/product")
public class ProductController {

	@Autowired
	private ProductCacheRepository productCacheRepository;
	
	@Autowired
	private ProductRepository productRepository;
	
	
//	API create Product
	@PostMapping("/create") 
//	@CachePut(value = "Product", key = "#product.id") 
	public String createProduct(@RequestBody Product product) {
		Product p = productRepository.create(product);
		if(p == null) {
			return "Insert faild";
		} else {
			return "Insert success";
		}
		
	}
	
	@GetMapping("/getAll") 
//	@CachePut( value = "Product")
	public List<Product> getAllProduct() {
		List<Product> listProduct = productRepository.getAllProducts();
		return listProduct;
	}
	
//	Nhược điểm: CHỉ trả về được đối tượng Serializable thôi chứ k trả về kiểu ResponseEntity đồ như API được, mà cũng đúng thôi
//	https://stackoverflow.com/questions/34984786/spring-web-responseentity-cant-serialization: Cách khắc phục có thể tham khảo
	@GetMapping("/get/{id}") 
//	@Cacheable(key = "#id", value = "Product")
	public Product getProduct(@PathVariable int id) {
//		return productCacheRepository.findProductById(id);
		return productRepository.getProductById(id);
	}
	
	@GetMapping("/delete/{id}") 
//	@CacheEvict(value = "Product", key = "#id", allEntries = true)
	public void deleteProduct(@PathVariable int id) {
//		boolean checkDelete = productCacheRepository.deleteProduct(id);
//		
//		if(!checkDelete) {
//			return "Delete Product faild";
//		} else {
//			return "Delete Product success";
//		}
		
		productRepository.deleteProduct(id);
	}
	
//	@CacheEvict(value = "Product", allEntries = true)
//	@Scheduled(fixedDelay = 60000)
//	public void emptyProductCache() {
//		System.out.println("Xoá cache");
//	}
	
//	public void setExpiration(String key, long expiration) {
//		redisTemplate.execute((RedisConnection connection) -> {
//	        connection.expire(key.getBytes(), expiration);
//	        return null;
//	    });
//	}
	
}
