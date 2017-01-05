package com.example;

import com.example.config.PictureUploadProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;


@SpringBootApplication
@EnableConfigurationProperties({PictureUploadProperties.class})
public class TweetApp {

	public static void main(String[] args) {
		SpringApplication.run(TweetApp.class, args);
	}
}
