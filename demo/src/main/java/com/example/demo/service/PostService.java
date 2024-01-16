package com.example.demo.service;


import com.example.demo.model.PostEntity;
import com.example.demo.persistence.PostRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class PostService {
    @Autowired
    private PostRepository repository;

    public String testService(){
        //TodoEntity 생성
        PostEntity entity = PostEntity.builder().title("My first todo item").build();

        //TodoEntity 저장
        repository.save(entity);

        //TodoEntity 검색
        PostEntity savedEntity = repository.findById(entity.getId()).get();
        return savedEntity.getTitle();
    }

    public List<PostEntity> create(final PostEntity entity){
        validate(entity);
        repository.save(entity);
        log.info("Entity Id : {} is saved.", entity.getId());

        return repository.findByUserId(entity.getUserId());

    }

    private static void validate(PostEntity entity) {
        if (entity ==null){
            log.warn("Entity cananot be null");
            throw new RuntimeException("Entity cananot be null");
        }
        if(entity.getUserId()==null){
            log.warn("Unknown user");
            throw new RuntimeException("Unknown user");
        }
    }

    public List<PostEntity> retrieve(final String userId){
        return repository.findByUserId(userId);
        //레포에 위임하는 격
    }

    public List<PostEntity> update(final PostEntity entity){
        validate(entity);

        final Optional<PostEntity> original = repository.findById(entity.getId());

        original.ifPresent(todo -> {
            todo.setTitle(entity.getTitle());
            todo.setDone(entity.isDone());

            repository.save(todo);
        });
        return retrieve(entity.getUserId());
    }

    public List<PostEntity> delete(final PostEntity entity) {
        validate(entity);

        try{
            repository.delete(entity);
        }    catch (Exception e){
            log.error("error deleting entity", entity.getId(),e);
            throw new RuntimeException("error deleting entity"+entity.getId());
        }
        return retrieve(entity.getUserId());
    }

}
