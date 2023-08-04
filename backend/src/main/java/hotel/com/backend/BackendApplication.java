package hotel.com.backend;

import org.modelmapper.ModelMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import hotel.com.backend.Models.Department;
import hotel.com.backend.Models.Task;
import hotel.com.backend.Models.Users;
import hotel.com.backend.Models.Enums.Role;
import hotel.com.backend.Repository.DepartmentRepos;
import hotel.com.backend.Repository.TaskRepos;
import hotel.com.backend.Repository.UserRepos;

@SpringBootApplication
public class BackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(BackendApplication.class, args);
	}

	@Bean
	CommandLineRunner commandLineRunner(UserRepos userRepos, DepartmentRepos departmentRepos, TaskRepos taskRepos) {
		return args -> {
			// Department department1 = new Department("Cleaning");
			// Department department2 = new Department("Catering");
			// Department department3 = new Department("Mantaining");
			// departmentRepos.save(department1);
			// departmentRepos.save(department2);
			// departmentRepos.save(department3);

			// Users quan = new Users("quan", new BCryptPasswordEncoder().encode("123456"), "quan", "doan", department1);
			// quan.getRoles().add(Role.USER);
			// Users khanh = new Users("khanh", new BCryptPasswordEncoder().encode("123456"), "khanh", "doan", department3);
			// khanh.getRoles().add(Role.USER);
			// Users duy = new Users("duy", new BCryptPasswordEncoder().encode("123456"), "duy", "doan", department2);
			// duy.getRoles().add(Role.USER);

			// userRepos.save(quan);
			// userRepos.save(khanh);
			// userRepos.save(duy);

			// Task task1 = new Task("cleaning bed", "303", "the sheet is too dirty", department1, quan);
			// taskRepos.save(task1);

			// Task task2 = new Task("cleaning toilet", "506", "the sink is too wet", department3, khanh);
			// taskRepos.save(task2);
		};
	}
}
