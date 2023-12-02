package com.wot.services;

import com.amazonaws.services.s3.AmazonS3;

import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.util.IOUtils;
import com.wot.entitites.TaskFiles;
import com.wot.entitites.PendingTasks;
import com.wot.payloads.TaskStatusCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import java.util.*;

@Service
@CrossOrigin(origins = "*")
@Slf4j
public class FileHandlingService {

    @Autowired
    private AmazonS3 amazonS3Client;

    @Value("${aws.s3.bucket}")
    private String bucketName;


    @Autowired
    private TaskFilesService taskFilesService;

    @Autowired
    private ClientService clientService;

    @Autowired
    private TaskStatusService taskStatusService;

    @Autowired
    private PendingTasksService pendingTasksService;


    public byte[] downloadFile(String fileName) throws IOException {
        S3Object s3Object = amazonS3Client.getObject(bucketName, fileName);
        S3ObjectInputStream s3ObjectInputStream = s3Object.getObjectContent();
        return IOUtils.toByteArray(s3ObjectInputStream);
    }

    public PutObjectResult uploadFile(MultipartFile file, String fileIdentifier) {

        return amazonS3Client.putObject(new PutObjectRequest(bucketName, fileIdentifier, convertMultipartFileToFile(file)));
    }

    public String deleteFile(String fileName) {
        amazonS3Client.deleteObject(bucketName, fileName);
        return "deleted successfully";
    }

    private File convertMultipartFileToFile(MultipartFile file) {
        File convertedFile = new File(file.getOriginalFilename());
        try (FileOutputStream fileOutputStream = new FileOutputStream(convertedFile)) {
            fileOutputStream.write(file.getBytes());
        } catch (Exception e) {
            log.error("error converting file ", e);
            throw new RuntimeException(e);
        }
        return convertedFile;
    }


    public boolean saveClientTasks(MultipartFile[] files, String clientEmail, PendingTasks pendingTasks) throws IOException {


        if (files != null) {
            pendingTasks.setClient(clientService.getCurrentUser(clientEmail));
            pendingTasks.setQuery(" ");
            pendingTasks.setStatus(taskStatusService.getTaskStatusByStatus(TaskStatusCode.IN_REVIEW));
            pendingTasksService.saveIncomingTaskRequest(pendingTasks);

            for (MultipartFile myFile : files) {
                //Files.copy(myFile.getInputStream(), Paths.get(new ClassPathResource("/static/files").getFile() + File.separator + random + "@" + myFile.getOriginalFilename()), StandardCopyOption.REPLACE_EXISTING);
                final String random = UUID.randomUUID().toString();
                if (this.uploadFile(myFile, random + "@" + myFile.getOriginalFilename()) != null) {
                    TaskFiles taskFiles = new TaskFiles();
                    taskFiles.setId(random);
                    taskFiles.setName(random + "@" + myFile.getOriginalFilename());
                    taskFiles.setOriginalFileName(myFile.getOriginalFilename());
                    taskFiles.setType(myFile.getContentType());
                    taskFiles.setCreatedAt(new Date());
                    taskFiles.setPendingTasks(pendingTasks);
                    taskFilesService.saveFileDetails(taskFiles);

                }

            }

            return true;
        }

        return false;
    }


}
