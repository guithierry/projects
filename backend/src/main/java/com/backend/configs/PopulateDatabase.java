package com.backend.configs;

import java.util.Optional;
import java.util.stream.Stream;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.backend.entities.Comment;
import com.backend.entities.Project;
import com.backend.entities.Todo;
import com.backend.entities.User;
import com.backend.repositories.CommentRepository;
import com.backend.repositories.ProjectRepository;
import com.backend.repositories.TodoRepository;
import com.backend.repositories.UserRepository;

@Configuration
public class PopulateDatabase {

	private UserRepository userRepository;
	private ProjectRepository projectRepository;
	private TodoRepository todoRepository;
	private CommentRepository commentRepository;

	public PopulateDatabase(UserRepository userRepository, ProjectRepository projectRepository,
			TodoRepository todoRepository, CommentRepository commentRepository) {
		this.userRepository = userRepository;
		this.projectRepository = projectRepository;
		this.todoRepository = todoRepository;
		this.commentRepository = commentRepository;
	}
	
	@Bean
	public void run() {
		Optional<User> email1 = this.userRepository.findByEmail("guilherme.thierry99@email.com");
		Optional<User> email2 = this.userRepository.findByEmail("test@email.com");
		
		if (email1.isEmpty() && email2.isEmpty()) {
			
			User user1 = new User();
			user1.setName("Guilherme");
			user1.setEmail("guilherme.thierry99@email.com");

			User user2 = new User();
			user2.setName("Test User");
			user2.setEmail("test@email.com");

			Todo todo = new Todo();
			todo.setName("Login");
			todo.setDescription("UI to login on app");

			Todo todo2 = new Todo();
			todo2.setName("Dashboard");
			todo2.setDescription("Dashboard to list something");

			Project project = new Project();
			project.setName("Mobile App");
			project.setDescription("Some description to project");
			
			project.setOwner(user1);
			user1.getOwnerProjects().add(project);
			
			project.getUsers().addAll(Stream.of(user1, user2).toList());

			todo.setProject(project);
			todo2.setProject(project);

			Comment comment = new Comment();
			comment.setDescription("Lets go!!");
			comment.setUser(user1);
			comment.setProject(project);

			Comment comment2 = new Comment();
			comment2.setDescription("Lets work");
			comment2.setUser(user2);
			comment2.setProject(project);

			this.userRepository.saveAll(Stream.of(user1, user2).toList());
			this.projectRepository.save(project);
			this.todoRepository.saveAll(Stream.of(todo, todo2).toList());
			this.commentRepository.saveAll(Stream.of(comment, comment2).toList());
		}
	}
}










