package com.mongodb;

public class MongoDemo {
	
	//连接单点
	//MongoClient mongoClient = MongoClients.create("mongodb://192.168.56.10:27017");
	//连接副本集
	MongoClient mongoClient = MongoClients.create("mongodb://root:root@192.168.56.10:28017,192.168.56.10:28018,192.168.56.10:28019/test?authSource=admin&replicaSet=rs0");
	//连接分片集群  节点：mongos
	//MongoClient mongoClient = MongoClients.create("mongodb://192.168.56.10:27017,192.168.56.11:27017,192.168.56.12:27017");

	//getDatabase
	MongoDatabase database = mongoClient.getDatabase("test");
	//getCollection
	MongoCollection<Document> collection = database.getCollection("emp");

	System.out.println("emp文档数："+collection.countDocuments());

}
