package edu.du.proj_g2e.fileUpload_AWS;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration // 이 클래스는 Spring에서 설정 클래스로 사용됨을 나타냅니다.
public class S3Config {

    @Value("${cloud.aws.credentials.accessKey}") // application.properties에서 accessKey 값을 주입받습니다.
    private String accessKey;

    @Value("${cloud.aws.credentials.secretKey}") // application.properties에서 secretKey 값을 주입받습니다.
    private String secretKey;

    @Value("${cloud.aws.region.static}") // application.properties에서 region 값을 주입받습니다.
    private String region;

    @Bean // AmazonS3 객체를 Spring Bean으로 등록합니다.
    public AmazonS3 amazonS3() {
        // AWS S3 접근에 필요한 자격 증명 생성
        AWSCredentials credentials = new BasicAWSCredentials(accessKey, secretKey);

        // AmazonS3 클라이언트 생성 및 지역 설정
        return AmazonS3ClientBuilder
                .standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withRegion(region)
                .build();
    }
}
