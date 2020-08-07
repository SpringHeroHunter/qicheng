package com.springboot.springboot.controller;

import com.springboot.springboot.entity.BlogModel;
import com.springboot.springboot.service.BlogRepository;
import com.springboot.springboot.utils.ResultVO;
import com.springboot.springboot.utils.ResultVOUtil;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;


@RestController
@RequestMapping("/es")
@Slf4j
public class EsController {

    @Autowired
    private BlogRepository blogRepository;

    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    @PostMapping("add")
    public ResultVO save(BlogModel blogModel){
        blogRepository.save(blogModel);
        return ResultVOUtil.success();
    }

    @GetMapping("query/{id}")
    public ResultVO query(@PathVariable String id){
        Optional<BlogModel> repositoryById = blogRepository.findById(id);
        if (repositoryById.isPresent()) {
            BlogModel blogModel = repositoryById.get();
            return ResultVOUtil.success(blogModel.toString());
        }
        return ResultVOUtil.success();
    }

    @GetMapping("queryAll")
    public ResultVO queryAll(){
        Iterable<BlogModel> all = blogRepository.findAll();
        ArrayList<BlogModel> list = new ArrayList<>();
        all.forEach(list::add);
        return ResultVOUtil.success(list);
    }


    @GetMapping("queryAllPage")
    public ResultVO queryAllPage(String page, String size){
        HashMap<String, Object> map = new HashMap<>();
        Pageable pageable = PageRequest.of(Integer.parseInt(page), Integer.parseInt(size));
        NativeSearchQuery build = new NativeSearchQueryBuilder().withPageable(pageable).build();
        Page<BlogModel> search = blogRepository.search(build);
        List<BlogModel> list = search.toList().stream().collect(Collectors.toList());
        map.put("total",search.getTotalElements());
        map.put("totalPages",search.getTotalPages());
        map.put("data",list);
        return ResultVOUtil.success(map);
    }

    @GetMapping("update")
    public ResultVO update(BlogModel blogModel){
        String id = blogModel.getId();
        blogRepository.save(blogModel);
        return ResultVOUtil.success();
    }

    @GetMapping("delete/{id}")
    public ResultVO delete(@PathVariable String id){
        blogRepository.deleteById(id);
        return ResultVOUtil.success();
    }

    @GetMapping("deleteAll")
    public ResultVO deleteAll(){
        blogRepository.deleteAll();
        return ResultVOUtil.success();
    }



    @GetMapping("searth/titel")
    public ResultVO searth(String keyWord){
        List<BlogModel> like = blogRepository.findByTitelLike(keyWord);
        return ResultVOUtil.success(like.toString());
    }




    @GetMapping("searthlike")
    public ResultVO searthlike(String keyWord){
        SearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery(queryStringQuery(keyWord)).build();
        List<BlogModel> list = elasticsearchTemplate.queryForList(searchQuery, BlogModel.class);
        return ResultVOUtil.success(list);
    }

    @GetMapping("full")
    public ResultVO full(String keyWord,int page,int size){
        // 构造分页类
        Pageable pagerable = PageRequest.of(page, size);
        // 构造NativeSearchQueryBuilder
        NativeSearchQueryBuilder searchQueryBuilder = new NativeSearchQueryBuilder().withPageable(pagerable);
        searchQueryBuilder.withQuery(QueryBuilders.queryStringQuery(keyWord));
        // 搜索条件入口 elasticsearchTemplate 会使用它进行搜索
        SearchQuery searchQuery = searchQueryBuilder.build();
        Page<BlogModel> blogModels = elasticsearchTemplate.queryForPage(searchQuery, BlogModel.class);
        return ResultVOUtil.success(blogModels);

    }















}
