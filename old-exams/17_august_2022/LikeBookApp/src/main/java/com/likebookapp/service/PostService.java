package com.likebookapp.service;

import com.likebookapp.model.dto.CreatePostDTO;
import com.likebookapp.model.entity.Mood;
import com.likebookapp.model.entity.Post;
import com.likebookapp.model.entity.User;
import com.likebookapp.repository.MoodRepository;
import com.likebookapp.repository.PostRepository;
import com.likebookapp.repository.UserRepository;
import com.likebookapp.util.UserSession;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
public class PostService {

    private final PostRepository postRepository;
    private final UserSession userSession;
    private final MoodRepository moodRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    public PostService(PostRepository postRepository, UserSession userSession, MoodRepository moodRepository, UserRepository userRepository, ModelMapper modelMapper) {
        this.postRepository = postRepository;
        this.userSession = userSession;
        this.moodRepository = moodRepository;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    public boolean create(CreatePostDTO createPostDTO) {
        Optional<Post> byContent = this.postRepository.findByContent(createPostDTO.getContent());

        if (byContent.isPresent()) {
            return false;
        }

        Optional<Mood> mood = this.moodRepository.findByName(createPostDTO.getMood());
        Optional<User> user = findUserById(this.userSession.getId());

        Post post = new Post();

        post.setContent(createPostDTO.getContent());
        post.setMood(mood.get());
        post.setUser(user.get());

        this.postRepository.save(post);

        return true;
    }

    private Optional<User> findUserById(long id) {
        Optional<User> user = this.userRepository.findById(id);
        return user;
    }

    public Set<Post> getUserPosts(long id) {

        Set<Post> allPostsByUserId = this.postRepository.findAllByUserId(id);

        return allPostsByUserId;
    }

    public Set<Post> getOtherUsersPosts(long id) {

        Set<Post> allPostsByOtherUsers = this.postRepository.findAllByUserIdNotLike(id);

        return allPostsByOtherUsers;
    }

    public void likePostWithId(Long id, long loggedUserId) {
        Optional<Post> post = this.postRepository.findById(id);

        Optional<User> user = this.userRepository.findById(loggedUserId);
        post.get().getLikedUsers().add(user.get());

        this.postRepository.save(post.get());
    }

    public void removePostById(Long id) {
        this.postRepository.deleteById(id);
    }
}
