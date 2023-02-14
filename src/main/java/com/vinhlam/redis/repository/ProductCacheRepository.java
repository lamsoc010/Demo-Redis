package com.vinhlam.redis.repository;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import com.vinhlam.redis.entity.Product;

@Repository
public class ProductCacheRepository {

	public static final String HASH_KEY = "Product";
	
	@Autowired
	@Qualifier("redisTemplate") //Khi xảy ra có 2 bean thì dùng cái này để chỉ định là bean nào được DI
	private RedisTemplate template;
	
	public Product save(Product product) {
		template.opsForHash().put(HASH_KEY, product.getIdProduct(), product);
		
		
		long timeExpire = (long) Math.floor(Math.random() * 60 + 30);
//		template.expire(product.getIdProduct(), timeExpire , TimeUnit.SECONDS);
		System.out.println("Thời gian sống của" +product.getIdProduct()+" là: " + timeExpire);
		
//		Set thời gian sống của redis
		template.expire(HASH_KEY, 60, TimeUnit.SECONDS);
		

		// Xoá là xoá hết luôn k phải xoá theo id
		// Nó sẽ xoá được dữ liệu trong redis, nhưng dữ liệu được lưu ở Cache rồi thì hiện tại chưa xoá được
		// Tìm cách chỉ xoá theo id mà thôi
		
		return product;
	}
	
	public List<Product> getAll() {
		System.out.println("called getAll() in DB");
		List<Product> products2 = template.opsForHash().values(HASH_KEY);
		return products2;
	}
	
	public Product findProductById(int id) {
		
//		template.expire(id, 10, TimeUnit.SECONDS);
		return (Product) template.opsForHash().get(HASH_KEY, id);
	}
	
	public boolean deleteProduct(int idProduct) {
		try {
			System.out.println("called deleteProduct() in DB");
			Long l = template.opsForHash().delete(HASH_KEY, idProduct);
			if(l == 0) {
				return false;
			} else {
				return true;				
			}
		} catch (Exception e) {
			return false;
		}
	}
}
