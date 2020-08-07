package com.springboot.springboot.service;

import com.springboot.springboot.entity.BlogModel;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface BlogRepository extends ElasticsearchRepository<BlogModel,String> {
    List<BlogModel> findByTitelLike(String keyWork);
}
