package com.springboot.springboot.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.format.annotation.DateTimeFormat;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

@Data
@Accessors(chain = true)
@Document(indexName = "blog",type = "java")
public class BlogModel implements Serializable {
    @Id
    private String id;
    private String titel;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    private Date time;
}
