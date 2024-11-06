package com.mongodb.model;

import java.util.Date;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;


@Document("emp")//emp collection Document
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString(callSuper = false)
public class MongoEmployee {

    @Id
    private Integer id;
    
    @Field("username")
    private String name;
    
    @Field
    private int age;
    
    @Field
    private Double salary;
    
    @Field
    private Date birthday;
    
    //The @ApiModelProperty annotation is used to annotate the properties of a Java class, indicating that this property is a Swagger model property.
    @ApiModelProperty(value = "Email", required = true)
    private String email;

    @ApiModelProperty(value = "nickname")
    private String userName;

    @ApiModelProperty(value = "password")
    private String password;

    @ApiModelProperty(value = "salt")
    private String salt;

    @ApiModelProperty(value = "validateCode")
    private String validateCode;

    @ApiModelProperty(value = "gender：1 male; 2 female 0:secret")
    private int gender = 0;

    @ApiModelProperty(value = "telephone")
    private Long phone = 0L;

    @ApiModelProperty(value = "imgUrl")
    private String imgUrl = "";

    @ApiModelProperty(value = "address")
    private String address = "";

    @ApiModelProperty(value = "registerTime")
    private Long registerTime = 0L;
}