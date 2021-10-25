package springweb.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicSessionCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.securitytoken.AWSSecurityTokenService;
import com.amazonaws.services.securitytoken.AWSSecurityTokenServiceClientBuilder;
import com.amazonaws.services.securitytoken.model.AssumeRoleRequest;
import com.amazonaws.services.securitytoken.model.AssumeRoleResult;
import com.amazonaws.services.securitytoken.model.Credentials;

@Configuration
public class StorageConfig {

	private String region = "ap-south-1";
	private String roleARN="arn:aws:iam::491852517787:role/AssumethisRole";
	private String roleSessionName="Session_1";
	
	@Bean
	public AmazonS3 s3Client() {
			
		AWSSecurityTokenService stsClient = AWSSecurityTokenServiceClientBuilder.standard().build();
		AssumeRoleRequest roleRequest = new AssumeRoleRequest().withRoleArn(roleARN)
				.withRoleSessionName(roleSessionName).withDurationSeconds(3600);
		AssumeRoleResult assumeResult = stsClient.assumeRole(roleRequest);
		Credentials temporaryCredentials = assumeResult.getCredentials();
		

		AWSCredentials credentials = new BasicSessionCredentials(temporaryCredentials.getAccessKeyId() , temporaryCredentials.getSecretAccessKey(), temporaryCredentials.getSessionToken());
		return AmazonS3ClientBuilder.standard().withCredentials(new AWSStaticCredentialsProvider(credentials))
				.withRegion(region).build();
		

	}
	
	
}
