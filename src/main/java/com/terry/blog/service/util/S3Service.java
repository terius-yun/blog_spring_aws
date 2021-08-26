package com.terry.blog.service.util;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.util.IOUtils;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.ByteArrayInputStream;
import java.io.IOException;

@Service
@NoArgsConstructor
public class S3Service {

    private static final Logger logger = LoggerFactory.getLogger(S3Service.class);

    private AmazonS3 s3Client;

    @Value("${cloud.aws.credentials.accessKey}")
    private String accessKey;

    @Value("${cloud.aws.credentials.secretKey}")
    private String secretKey;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    @Value("${cloud.aws.region.static}")
    private String region;

    @PostConstruct
    public void setS3Client() {


        AWSCredentials credentials = new BasicAWSCredentials(this.accessKey, this.secretKey);

        s3Client = AmazonS3ClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withRegion(this.region)
                .build();
    }

    public String upload(MultipartFile file) {
        logger.info("fileName = "+ file.getOriginalFilename());
        String fileName = file.getOriginalFilename();

        try {

            ObjectMetadata objMeta = new ObjectMetadata();

            byte[] bytes = IOUtils.toByteArray(file.getInputStream());
            objMeta.setContentLength(bytes.length);

            ByteArrayInputStream byteArrayIs = new ByteArrayInputStream(bytes);
            PutObjectRequest putObjReq = new PutObjectRequest(bucket, fileName, byteArrayIs, objMeta);
            s3Client.putObject(putObjReq);
//            s3Client.putObject(new PutObjectRequest(bucket, fileName, byteArrayIs, objMeta)
//                    .withCannedAcl(CannedAccessControlList.PublicRead));
        }catch (IOException e){
            e.getStackTrace();
        }
        logger.info("s3Client.getUrl(bucket, fileName).toString(); = " + s3Client.getUrl(bucket, fileName).toString());
        return s3Client.getUrl(bucket, fileName).toString();
    }
}