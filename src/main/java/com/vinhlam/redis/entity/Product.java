package com.vinhlam.redis.entity;

import java.io.Serializable;

import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.codecs.pojo.annotations.BsonProperty;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.redis.core.RedisHash;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

@RedisHash(value = "Product")
@Document(collection = "product")
public class Product implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -3085543082238127348L;
	
	@BsonProperty("_id")
	@BsonId
	private ObjectId id;
	
	private int idProduct;
	private String name;
	private int quantity;
	private long price;
}
