package com.yildirim.springbootrestapi.service.implement;

import com.yildirim.springbootrestapi.entity.Category;
import com.yildirim.springbootrestapi.entity.Post;
import com.yildirim.springbootrestapi.entity.User;
import com.yildirim.springbootrestapi.exception.ResourceNotFoundException;
import com.yildirim.springbootrestapi.payload.PostDto;
import com.yildirim.springbootrestapi.repository.CategoryRepository;
import com.yildirim.springbootrestapi.repository.PostRepository;
import com.yildirim.springbootrestapi.repository.UserRepository;
import com.yildirim.springbootrestapi.response.PostResponse;
import com.yildirim.springbootrestapi.service.PostService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final ModelMapper modelMapper;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;
    @Override
    public PostDto createPost(PostDto postDto) {
        Post post = mapToEntity(postDto);
        Category category = categoryRepository.findById(postDto.getCategoryId())
                .orElseThrow(()->
                        new ResourceNotFoundException("Category",
                                "Id",
                                postDto.getCategoryId()
                        ));
        User user = userRepository.findByEmail(authanticatedUsername()).orElseThrow(()-> new UsernameNotFoundException("User not found"));
        post.setCategory(category);
        post.setUser(user);
        Post responsePost = postRepository.save(post);
        PostDto postResponse = mapToDTO(responsePost);
        return postResponse;
    }

    @Override
    public PostResponse getAllPosts(int pageNo, int pageSize, String sort, String sortDir) {
        Sort sortBy = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sort)
                :Sort.by(sort).descending();
        Pageable pageable = PageRequest.of(pageNo,pageSize,sortBy);
        Page<Post> posts = postRepository.findAll(pageable);
        List<Post> listOfPosts = posts.getContent();
        List<PostDto> listOfPostDto = listOfPosts
                .stream()
                .map(post ->
                        mapToDTO(post))
                .collect(Collectors.toList());

        PostResponse postResponse =
                new PostResponse();
        postResponse.
                setLisOfPostDto(listOfPostDto);
        postResponse.
                setPageNo(posts.getNumber());
        postResponse.
                setPageSize(posts.getSize());
        postResponse.
                setTotalElements(posts.getTotalElements());
        postResponse.
                setTotalPages(postResponse.getTotalPages());
        postResponse.
                setLast(posts.isLast());

        return postResponse;
    }

    @Override
    public PostDto findPostById(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(()->
                        new ResourceNotFoundException("Post", "id", postId));
        PostDto postDto = mapToDTO(post);
        return postDto;
    }

    @Override
    public PostDto updatePost(PostDto postDto, Long postId) {
        Post responsePost = postRepository.findById(postId).
                orElseThrow(()->
                        new ResourceNotFoundException("Post", "id", postId));
        responsePost.
                setTitle(postDto.getTitle());
        responsePost.
                setDescription(postDto.getDescription());
        responsePost.
                setContent(postDto.getContent());
        Category category = categoryRepository.findById(postDto.getCategoryId())
                .orElseThrow(()->
                        new ResourceNotFoundException("Category",
                                "Id",
                                postDto.getCategoryId()
                        ));
        responsePost.setCategory(category);
        postRepository.save(responsePost);
        PostDto responsePostDto = mapToDTO(responsePost);

        return responsePostDto;
    }

    @Override
    public String deletePostById(Long postId) {
        postRepository.deleteById(postId);
        return "Successfully Deleted.";
    }

    @Override
    public List<PostDto> getPostsByCategory(Long categoryId) {
        List<Post> posts = postRepository.findByCategoryId(categoryId);
        List<PostDto> postDtos = posts
                .stream()
                .map(post -> mapToDTO(post))
                .collect(Collectors.toList());
        return postDtos;
    }

    private PostDto mapToDTO(Post post){
        PostDto postDto = modelMapper.map(post,PostDto.class);
        return postDto;
    }

    private Post mapToEntity(PostDto postDto){
        Post post = modelMapper.map(postDto, Post.class);
        return post;
    }

    private String authanticatedUsername(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String username = userDetails.getUsername();
        return username;
    }
}
