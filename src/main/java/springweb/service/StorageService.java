package springweb.service;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.util.IOUtils;


@Service
public class StorageService {

	@Autowired
	private AmazonS3 s3Client;
	
	
	public byte[] downloadFile(String filename) {
		
        S3Object s3Object = s3Client.getObject("my-first-s3-bucket-one", filename);
        S3ObjectInputStream inputStream = s3Object.getObjectContent();
        
        try {
        	byte [] content = IOUtils.toByteArray(inputStream);
        	return content;
        } catch (IOException e) {
        	e.printStackTrace();
        }
		return null;
		
	}

}
