package com.tubager.mongo.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import com.mongodb.ServerAddress;

@Configuration
@EnableMongoRepositories
public class MongoDbConfig extends AbstractMongoConfiguration {
	
	@Value("${mongo.host}")
	private String host;

	@Value("${mongo.port}")
	private String port;
	 
	@Value("${mongo.db}")
	private String databaseName;
	
	@Bean
	public GridFsTemplate gridFsTemplate() throws Exception {
		return new GridFsTemplate(mongoDbFactory(), mappingMongoConverter());
	}

	@Override
	protected String getDatabaseName() {
		return databaseName;
	}

	@Override
	public Mongo mongo() throws Exception {
//		List<ServerAddress> seeds = new ArrayList<ServerAddress>();
//		System.out.println(host);
//		System.out.println(port);
//		seeds.add(new ServerAddress(host, Integer.parseInt(port)));
		return new MongoClient(host, Integer.parseInt(port));
	}
	
	@Override
	protected String getMappingBasePackage() {
		return "com.tubager.mongo.bean";
	}

}
