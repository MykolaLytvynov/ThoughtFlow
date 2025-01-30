package ua.mykola.thoughtflow.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import ua.mykola.thoughtflow.document.Post;
import ua.mykola.thoughtflow.model.Topic;

import java.util.List;

@Repository
public interface PostRepository extends MongoRepository<Post, String> {
    Page<Post> findByTopicIn(List<Topic> topics, Pageable pageable);
}
