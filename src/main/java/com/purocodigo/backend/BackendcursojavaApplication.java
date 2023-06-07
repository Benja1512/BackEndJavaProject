package com.purocodigo.backend;

import com.purocodigo.backend.responses.UserRest;
import com.purocodigo.backend.security.AppProperties;
import com.purocodigo.backend.shared.dto.UserDto;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
@EnableJpaAuditing
public class BackendcursojavaApplication {

	public static void main(String[] args) {

		SpringApplication.run(BackendcursojavaApplication.class, args);
		System.out.print("funcionando");
	}

	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public SpringApplicationContext springApplicationContext() {
		return new SpringApplicationContext();
	}


	@Bean(name = "AppProperties")
	public AppProperties getAppProperties() {
		return new AppProperties();
	}

	@Bean
	public ModelMapper modelMapper(){
		ModelMapper mapper = new ModelMapper();

		mapper.typeMap(UserDto.class, UserRest.class).addMapping(m -> m.skip(UserRest::setPost));

		return mapper;
	}}
