package com.example.dockerspringbootpostgres.ElasticRepository;

import com.example.dockerspringbootpostgres.Entity.LectureFullText;
import org.elasticsearch.common.unit.Fuzziness;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

import java.util.List;

import static org.elasticsearch.index.query.QueryBuilders.matchQuery;

@EnableElasticsearchRepositories
public interface LectureFullTextRepository  extends ElasticsearchRepository<LectureFullText, String> {
}
