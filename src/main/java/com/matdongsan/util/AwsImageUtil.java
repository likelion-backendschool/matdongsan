package com.matdongsan.util;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Slf4j
@Profile("prod")
@Component
public class AwsImageUtil implements ImageUtil{
    private AmazonS3 s3Client;

    @Value("${cloud.aws.credentials.access-key}")
    private String accessKey;

    @Value("${cloud.aws.credentials.secret-key}")
    private String secretKey;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    @Value("${cloud.aws.region.static}")
    private String region;

    @Value("${cloud.aws.s3.bucket.url}")
    private String url;

    @PostConstruct
    public void setS3Client() {
        AWSCredentials credentials = new BasicAWSCredentials(this.accessKey, this.secretKey);

        s3Client = AmazonS3ClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withRegion(this.region)
                .build();
    }

    @Override
    public String saveFiles(List<MultipartFile> files) {
        if(files == null) return "";
        StringBuffer sb = new StringBuffer();
        for (MultipartFile file : files) {
            String name = saveImage(file);
            if(StringUtils.hasText(name)) sb.append(name).append(",");
        }
        return sb.toString();
    }
    private String saveImage(MultipartFile file) {
        if(file == null || file.isEmpty()) return null;
        try {
            String fileName = file.getOriginalFilename();
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentLanguage(String.valueOf(file.getBytes().length));
            String generatedName = generateFileName(fileName);
            s3Client.putObject(new PutObjectRequest(bucket, "images/"+generatedName, file.getInputStream(), metadata)
                    .withCannedAcl(CannedAccessControlList.PublicRead));
            log.info("파일 저장 완료 = {}", generatedName);
            return generatedName;
        } catch (IOException e) {
            log.error("파일 저장 중 에러 발생",e);
            throw new RuntimeException(e);
        }
    }

    private String generateFileName(String fileName) {
        String uuid = UUID.randomUUID().toString();
        if(fileName == null) return uuid;
        int extPos = fileName.lastIndexOf(".");
        String ext = fileName.substring(extPos + 1);
        return uuid + "." + ext;
    }

    @Override
    public String getFullPath(String fileName) {
        return url + fileName;
    }
}
