package com.seplag.acervo.config;

import io.minio.MinioClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MinioConfig {

    @Bean
    MinioClient minioInternalClient(
            @Value("${minio.internal-url}") String url,
            @Value("${minio.user}") String user,
            @Value("${minio.password}") String password
    ) {
        return MinioClient.builder()
                .endpoint(url)
                .credentials(user, password)
                .build();
    }

    @Bean
    MinioClient minioPublicClientAssinador(
            @Value("${minio.public-url}") String url,
            @Value("${minio.user}") String user,
            @Value("${minio.password}") String password
    ) {
        return MinioClient.builder()
                .endpoint(url)
                .credentials(user, password)
                .build();
    }
}
