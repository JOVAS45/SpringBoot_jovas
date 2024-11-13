package com.alibou.security;

import com.alibou.security.auth.AuthenticationService;
import com.alibou.security.auth.RegisterRequest;
import com.alibou.security.user.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import static com.alibou.security.user.Role.ADMIN;
import static com.alibou.security.user.Role.MANAGER;

@EnableJpaRepositories(basePackages = {
		"com.alibou.security",
		"com.alibou.security.user",
		"com.alibou.security.bitacora",
		"com.alibou.security.book",
		"com.alibou.security.calendarioFicha",
		"com.alibou.security.especialidad",
		"com.alibou.security.fichaDisponible",
		"com.alibou.security.horario",
		"com.alibou.security.medico",
		"com.alibou.security.notificacion",
		"com.alibou.security.paciente",
		"com.alibou.security.token"
})
@EntityScan(basePackages = {
		"com.alibou.security",
		"com.alibou.security.user",
		"com.alibou.security.bitacora",
		"com.alibou.security.book",
		"com.alibou.security.calendarioFicha",
		"com.alibou.security.especialidad",
		"com.alibou.security.fichaDisponible",
		"com.alibou.security.horario",
		"com.alibou.security.medico",
		"com.alibou.security.notificacion",
		"com.alibou.security.paciente",
		"com.alibou.security.token"
})
@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
public class SecurityApplication {

	public static void main(String[] args) {
		SpringApplication.run(SecurityApplication.class, args);
	}

	@Bean
	public CommandLineRunner commandLineRunner(
			AuthenticationService service,
			UserRepository userRepository
	) {
		return args -> {
			// Verificar y crear admin solo si no existe
			if (!userRepository.existsByEmail("admin@mail.com")) {
				var admin = RegisterRequest.builder()
						.firstname("Admin")
						.lastname("Admin")
						.email("admin@mail.com")
						.password("password")
						.role(ADMIN)
						.build();
				var adminToken = service.register(admin).getAccessToken();
				System.out.println("Admin token: " + adminToken);
			}

			// Verificar y crear manager solo si no existe
			if (!userRepository.existsByEmail("manager@mail.com")) {
				var manager = RegisterRequest.builder()
						.firstname("Admin")
						.lastname("Admin")
						.email("manager@mail.com")
						.password("password")
						.role(MANAGER)
						.build();
				var managerToken = service.register(manager).getAccessToken();
				System.out.println("Manager token: " + managerToken);
			}
		};
	}
}