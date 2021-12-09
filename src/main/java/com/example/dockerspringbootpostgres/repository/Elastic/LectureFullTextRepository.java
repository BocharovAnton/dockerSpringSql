package com.example.dockerspringbootpostgres.repository.Elastic;

import com.example.dockerspringbootpostgres.Entity.Elastic.LectureFullText;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

@EnableElasticsearchRepositories
public interface LectureFullTextRepository  extends ElasticsearchRepository<LectureFullText, String> {
}
