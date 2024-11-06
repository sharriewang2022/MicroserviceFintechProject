package com.mongodb.service.impl;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.management.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Sort;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.MongoTransactionManager;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.GroupOperation;
import org.springframework.data.mongodb.core.aggregation.MatchOperation;
import org.springframework.data.mongodb.core.aggregation.ProjectionOperation;
import org.springframework.data.mongodb.core.aggregation.SortOperation;
import org.springframework.data.mongodb.core.aggregation.TypedAggregation;
import org.springframework.data.mongodb.core.aggregation.VariableOperators.Map;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Update;

import com.mongodb.ReadConcern;
import com.mongodb.ReadPreference;
import com.mongodb.TransactionOptions;
import com.mongodb.WriteConcern;
import com.mongodb.client.ClientSession;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import com.mongodb.client.result.UpdateResult;
import com.mongodb.model.MongoEmployee;

public class MongoEmployeeTemplate {
	
	@Autowired
	MongoTemplate mongoTemplate;
	
	public void getCollection() {

	    boolean exists = mongoTemplate.collectionExists("emp");
	    if (exists) {
	        mongoTemplate.dropCollection("emp");
	    }
	    mongoTemplate.createCollection("emp");
	}

 
	
	public void  insertEmployee() {

		MongoEmployee employee = new MongoEmployee(1, "n0", 30, 10000.00, new Date());

	    // save:  _id
	    //mongoTemplate.save(employee);
	    // insert： _id
	    mongoTemplate.insert(employee);

	    List<MongoEmployee> list = Arrays.asList(
	            new MongoEmployee(2, "n1", 21, 5000.00, new Date()),
	            new MongoEmployee(3, "n2", 26, 8000.00, new Date()),
	            new MongoEmployee(4, "n3", 22, 8000.00, new Date()),
	            new MongoEmployee(5, "n4", 28, 6000.00, new Date()),
	            new MongoEmployee(6, "n5", 24, 7000.00, new Date()),
	            new MongoEmployee(7, "n6", 28, 12000.00, new Date()));
	    
	    //insert multiple data
	    mongoTemplate.insert(list, MongoEmployee.class);
	}
	
	public void documentFind() {
	    System.out.println("==========query all documents===========");
	    //query all datas
	    List<MongoEmployee> list = mongoTemplate.findAll(MongoEmployee.class);
	    list.forEach(System.out::println);

	    System.out.println("==========query by ID===========");
	    //query by ID
	    MongoEmployee e = mongoTemplate.findById(1, MongoEmployee.class);
	    System.out.println(e);

	    System.out.println("==========findOne find the first row===========");
	    //return the first row
	    MongoEmployee one = mongoTemplate.findOne(new Query(), MongoEmployee.class);
	    System.out.println(one);

	    System.out.println("==========conditional query===========");
	    //new Query() no condition
	    
	    //Query employees with a salary greater than or equal to 8000
	    //Query query = new Query(Criteria.where("salary").gte(8000));
	    
	    //Query employees with salaries greater than 4000 and less than 10000
	    //Query query = new Query(Criteria.where("salary").gt(4000).lt(10000));
	    
	    //Regular query (fuzzy query) Java regex does not need //
	    //Query query = new Query(Criteria.where("name").regex("chong"));

	    //and or multi-condition query
	    Criteria criteria = new Criteria();
	    
	    //and query employees whose age is greater than 25 and salary is greater than 8000
	    //criteria.andOperator(Criteria.where("age").gt(25),Criteria.where("salary").gt(8000));
	    
	    //or query employees whose name is name3 or salary is greater than 8000
	    criteria.orOperator(Criteria.where("name").is(" name3"), Criteria.where("salary").gt(5000));
	    Query query = new Query(criteria);

	    //sort 
	    //query.with(Sort.by(Sort.Order.desc("salary")));


	    //skip limit pagination skip is used to specify the number of records to skip
	    //while limit is used to restrict the number of results returned.
	    query.with(Sort.by(Sort.Order.desc("salary")))
	            .skip(0)  //Specify the number of records to skip
	            .limit(4);  //Number of records displayed per page


	    //query result
	    List<MongoEmployee> employees = mongoTemplate.find(
	            query, MongoEmployee.class);
	    employees.forEach(System.out::println);
	} 
	
	public void dataFindByJson() {

	    //Using JSON string format for query
		
	    //Equivalence query
	    //String json = "{name:' name3'}";
		
	    //Multi-condition query
	    String json = "{$or:[{age:{$gt:25}},{salary:{$gte:8000}}]}";
	    Query query = new BasicQuery(json);

	    //query result
	    List<MongoEmployee> employees = mongoTemplate.find(
	            query, MongoEmployee.class);
	    employees.forEach(System.out::println);
	}

	public void testUpdate() {

	    //conditional query 
	    Query query = new Query(Criteria.where("salary").gte(15000));

	    System.out.println("========== before change===========");
	    List<MongoEmployee> employees = mongoTemplate.find(query, MongoEmployee.class);
	    employees.forEach(System.out::println);

	    Update update = new Update();
	    // set  change attribute
	    update.set("salary", 13000);

	    //updateFirst() Only change the first record that meets the conditions.
	    //UpdateResult updateResult = mongoTemplate.updateFirst(query, update, Employee.class);
	    //updateMulti()  Change all records that meet the criteria.
	    //UpdateResult updateResult = mongoTemplate.updateMulti(query, update, Employee.class);

	    //upsert() If there are no matching records, then insert the data.
	    //update.setOnInsert("id",11);  //指定_id
	    UpdateResult updateResult = mongoTemplate.upsert(query, update, MongoEmployee.class);

	    // get Number of modified records
	    System.out.println(updateResult.getModifiedCount());
	    // get Number of matching records
	    System.out.println(updateResult.getMatchedCount());


	    System.out.println("==========after change===========");
	    employees = mongoTemplate.find(query, MongoEmployee.class);
	    employees.forEach(System.out::println);
	}

	public void testDelete() {

	    // delete all data
	    //mongoTemplate.remove(new Query(),Employee.class);

	    //conditional delete
	    Query query = new Query(Criteria.where("salary").gte(10000));
	    mongoTemplate.remove(query, Employee.class);

	}

	//Taking the example of Aggregation Pipeline Example 2
	//Get states with a population exceeding 10 million
	db.zips.aggregate( [
	   { $group: { _id: "$state", totalPop: { $sum: "$pop" } } },
	   { $match: { totalPop: { $gt: 10*1000*1000 } } }
	] )

	 
	public void test(){
	    //$group
	    GroupOperation groupOperation = Aggregation
	            .group("state").sum("pop").as("totalPop");

	    //$match
	    MatchOperation matchOperation = Aggregation.match(
	            Criteria.where("totalPop").gte(10*1000*1000));

	    //Combine each aggregation step in order
	    TypedAggregation<Zips> typedAggregation = Aggregation.newAggregation(
	            Zips.class, groupOperation, matchOperation);

	    //Perform aggregation operations. If you don't use a Map, you can also use a custom entity class to receive data
	    AggregationResults<Map> aggregationResults = mongoTemplate
	            .aggregate(typedAggregation, Map.class);
	    //Retrieve the final result
	    List<Map> mappedResults = aggregationResults.getMappedResults();
	    for(Map map:mappedResults){
	        System.out.println(map);
	    }
	}

	//Get the average urban population of each state
	db.zips.aggregate( [
	   { $group: { _id: { state: "$state", city: "$city" }, cityPop: { $sum: "$pop" } } },
	   { $group: { _id: "$_id.state", avgCityPop: { $avg: "$cityPop" } } },
	   { $sort:{avgCityPop:-1}}
	] )

	 
	public void test2(){
	    //$group
	    GroupOperation groupOperation = Aggregation
	            .group("state","city").sum("pop").as("cityPop");
	    //$group
	    GroupOperation groupOperation2 = Aggregation
	            .group("_id.state").avg("cityPop").as("avgCityPop");
	    //$sort
	    SortOperation sortOperation = Aggregation
	            .sort(Sort.Direction.DESC,"avgCityPop");

	    //Combine each aggregation step in order
	    TypedAggregation<Zips> typedAggregation = Aggregation.newAggregation(
	            Zips.class, groupOperation, groupOperation2,sortOperation);

	    //Perform aggregation operations. If you don't use a Map, you can also use a custom entity class to receive data
	    AggregationResults<Map> aggregationResults = mongoTemplate
	            .aggregate(typedAggregation, Map.class);
	    //Retrieve the final result
	    List<Map> mappedResults = aggregationResults.getMappedResults();
	    for(Map map:mappedResults){
	        System.out.println(map);
	    }
	}

	//Get the largest and smallest cities by state
	db.zips.aggregate( [
	   { $group:
	      {
	        _id: { state: "$state", city: "$city" },
	        pop: { $sum: "$pop" }
	      }
	   },
	   { $sort: { pop: 1 } },
	   { $group:
	      {
	        _id : "$_id.state",
	        biggestCity:  { $last: "$_id.city" },
	        biggestPop:   { $last: "$pop" },
	        smallestCity: { $first: "$_id.city" },
	        smallestPop:  { $first: "$pop" }
	      }
	   },
	  { $project:
	    { _id: 0,
	      state: "$_id",
	      biggestCity:  { name: "$biggestCity",  pop: "$biggestPop" },
	      smallestCity: { name: "$smallestCity", pop: "$smallestPop" }
	    }
	  },
	   { $sort: { state: 1 } }
	] )



	 
	public void test3(){
	    //$group
	    GroupOperation groupOperation = Aggregation
	            .group("state","city").sum("pop").as("pop");

	    //$sort
	    SortOperation sortOperation = Aggregation
	            .sort(Sort.Direction.ASC,"pop");

	    //$group
	    GroupOperation groupOperation2 = Aggregation
	            .group("_id.state")
	            .last("_id.city").as("biggestCity")
	            .last("pop").as("biggestPop")
	            .first("_id.city").as("smallestCity")
	            .first("pop").as("smallestPop");

	    //$project
	    ProjectionOperation projectionOperation = Aggregation
	            .project("state","biggestCity","smallestCity")
	            .and("_id").as("state")
	            .andExpression(
	                    "{ name: \"$biggestCity\",  pop: \"$biggestPop\" }")
	            .as("biggestCity")
	            .andExpression(
	                    "{ name: \"$smallestCity\", pop: \"$smallestPop\" }"
	            ).as("smallestCity")
	            .andExclude("_id");

	    //$sort
	    SortOperation sortOperation2 = Aggregation
	            .sort(Sort.Direction.ASC,"state");


	    // Combine each aggregation step in order
	    TypedAggregation<Zips> typedAggregation = Aggregation.newAggregation(
	            Zips.class, groupOperation, sortOperation, groupOperation2,
	            projectionOperation,sortOperation2);

	    //Perform aggregation operations. If you don't use a Map, you can also use a custom entity class to receive data.
	    AggregationResults<Map> aggregationResults = mongoTemplate
	            .aggregate(typedAggregation, Map.class);
	    //Retrieve the final result
	    List<Map> mappedResults = aggregationResults.getMappedResults();
	    for(Map map:mappedResults){
	        System.out.println(map);
	    }

	}
 
	/**
	 * transactions API
	 * https://docs.mongodb.com/upcoming/core/tranlocsactions/
	 */	 
	public void updateEmployeeInfo() {
	    //Connect to the replica Collection
	    MongoClient client = MongoClients.create("mongodb://root:root@localhost:28017,localhost:28018,localhost:28019/test?authSource=admin&replicaSet=rs0");

	    MongoCollection<Document> emp = client.getDatabase("test").getCollection("emp");
	    MongoCollection<Document> events = client.getDatabase("test").getCollection("events");
	    //transaction configuration
	    TransactionOptions txnOptions = TransactionOptions.builder()
	            .readPreference(ReadPreference.primary())
	            .readConcern(ReadConcern.MAJORITY)
	            .writeConcern(WriteConcern.MAJORITY)
	            .build();
	    try (ClientSession clientSession = client.startSession()) {
	        //start transaction
	        clientSession.startTransaction(txnOptions);

	        try {
	            emp.updateOne(clientSession,
	                    Filters.eq("username", " name3"),
	                    Updates.set("status", "inactive"));

	            int i=1/0;

	            events.insertOne(clientSession,
	                    new Document("username", " name3").append("status", new Document("new", "inactive").append("old", "Active")));

	            //commit Transaction
	            clientSession.commitTransaction();

	        }catch (Exception e){
	            e.printStackTrace();
	            //rollback Transaction
	            clientSession.abortTransaction();
	        }
	    }
	}
	
	@Bean
	MongoTransactionManager transactionManager(MongoDatabaseFactory factory){
	//transaction configuration
	TransactionOptions txnOptions = TransactionOptions.builder()
	        .readPreference(ReadPreference.primary())
	        .readConcern(ReadConcern.MAJORITY)
	        .writeConcern(WriteConcern.MAJORITY)
	        .build();
	    return new MongoTransactionManager(factory);
	}

}
