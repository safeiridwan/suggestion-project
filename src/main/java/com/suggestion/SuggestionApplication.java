package com.suggestion;

import com.suggestion.service.FileService;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SuggestionApplication {
	private final FileService fileService;

	public SuggestionApplication(FileService fileService) {
		this.fileService = fileService;
	}

	public static void main(String[] args) {
		SpringApplication.run(SuggestionApplication.class, args);
	}

	@Bean
	public ApplicationRunner init() {
		return args -> fileService.readFilesOnStartup();
	}

}
