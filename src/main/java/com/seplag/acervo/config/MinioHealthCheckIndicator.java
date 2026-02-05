package com.seplag.acervo.config;

import io.minio.BucketExistsArgs;
import io.minio.MinioClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

@Component("minio")
public class MinioHealthCheckIndicator implements HealthIndicator {

    private final MinioClient minioInternalClient;
    private final String bucket;

    public MinioHealthCheckIndicator(
            MinioClient minioInternalClient,
            @Value("${minio.bucket}") String bucket
    ) {
        this.minioInternalClient = minioInternalClient;
        this.bucket = bucket;
    }

    @Override
    public Health health() {
        try {
            boolean exists = minioInternalClient.bucketExists(
                    BucketExistsArgs.builder().bucket(bucket).build()
            );

            if (!exists) {
                return Health.down()
                        .withDetail("bucket", bucket)
                        .withDetail("reason", "bucket_not_found")
                        .build();
            }

            return Health.up()
                    .withDetail("bucket", bucket)
                    .build();
        } catch (Exception e) {
            return Health.down(e)
                    .withDetail("bucket", bucket)
                    .build();
        }
    }
}
