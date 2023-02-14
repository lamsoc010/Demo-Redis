package com.vinhlam.redis.repository;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.bson.conversions.Bson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.InsertOneResult;
import com.vinhlam.redis.entity.Product;

@Repository
public class ProductRepository {
	
	@Autowired
	public MongoDatabase mongoDatabase;
	
	@Autowired
	private ProductCacheRepository productCacheRepository;
	
	public MongoCollection<Product> productCollection;
	
	@Autowired
	public void PriceTourService() {
		CodecRegistry cRegistry = CodecRegistries.fromRegistries(MongoClientSettings.getDefaultCodecRegistry(), 
				CodecRegistries.fromProviders(PojoCodecProvider.builder().automatic(true).build()));

		productCollection = mongoDatabase.getCollection("product", Product.class).withCodecRegistry(cRegistry);

	}
	
//	Create Product
	public Product create(Product product) {
		Product p = new Product();
		try {
			InsertOneResult ir = productCollection.insertOne(product);
			if(ir.getInsertedId() != null) {
				p = productCacheRepository.save(product);		
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return p;
	}
	

	public Product getProductById(int idProduct) {
		Product p = new Product();
		try {
//			Lấy dữ liệu trong redis
			p = productCacheRepository.findProductById(idProduct);
			
//			Nếu ở redis không có dữ liệu thì gọi đến db để lấy dữ liệu
			if(p == null) {
				System.out.println("Goi den db");
				List<Bson> pipeline = new ArrayList<>();
				Bson match = new Document("$match", new Document("idProduct", idProduct));
				pipeline.add(match);
				
				p = productCollection.aggregate(pipeline).first();
				
//				Sau khi lấy dữ liệu thì set lại giá trị cho redis
				productCacheRepository.save(p);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return p;
	}
	
	public void deleteProduct(int idProduct) {
		try {
//			Xoá dữ liệu ở DB
			Bson match = new Document("idProduct", idProduct);
			DeleteResult dr = productCollection.deleteOne(match);
			
//			Sau khi xoá dữ liệu ở DB thành công thì xoá dữ liệu ở redis
			if(dr.getDeletedCount() > 0) {
				productCacheRepository.deleteProduct(idProduct);			
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		
	}
	
	
	public List<Product> getAllProducts() {
		List<Product> products = new ArrayList<>();
		products = productCacheRepository.getAll();
		
		if(products.size() == 0 || products == null) {
			System.out.println("Query in database");
			productCollection.find().into(products);
		}
		
		return products;
	}
	
	
	public void updateProduct(Product product) {
//		productCollection.updateOne(null, null);
	}
	
	
	

}
