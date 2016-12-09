package handler;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import com.amazonaws.services.lambda.runtime.events.S3Event;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;

public class BucketSyncHandler implements RequestHandler<S3Event, Object> {
	static final AmazonS3 resourceS3;
	static final AmazonS3 destinationS3;
	static final Region resourceRegion;
	static final Region destinationRegion;
	static final String resourceBucket;
	static final String destinationBucket;
	
    static {
    	resourceS3 = new AmazonS3Client();
    	
    	BasicAWSCredentials credentials =
    			new BasicAWSCredentials(
    					System.getenv("DestinationAccessId"),
    					System.getenv("DestinationAccessKey"));
    	destinationBucket = System.getenv("DestinationBucket");
    	
    	destinationS3 = new AmazonS3Client(credentials);
    	
    	destinationRegion = Region.getRegion(Regions.fromName(
    			System.getenv("DestinationBucketRegion")));
    	resourceRegion = Region.getRegion(Regions.fromName(
    			System.getenv("ResourceBucketRegion")));
    	resourceBucket = System.getenv("ResourceBucket");
    }
    
	@Override
    public Object handleRequest(S3Event input, Context context) {
        context.getLogger().log("Input: " + input);
        
        String json = filter(input);
        
        JSONObject object = new JSONObject(json);
        List<String> keys = new ArrayList<>();
        JSONArray array = object.optJSONArray("Records");
        
        for (int i = 0; i < array.length(); i++) {
        	keys.add(array
        			.optJSONObject(i)
            		.optJSONObject("s3")
            		.optJSONObject("object")
            		.optString("key"));
        }
        
        for (int i = 0; i < keys.size(); i++) {
        	 destinationS3.putObject(
          		   destinationBucket, 
          		   keys.get(i), 
          		   resourceS3.getObject(resourceBucket, keys.get(i)).getObjectContent(),
          		   new ObjectMetadata());
        }
       
        context.getLogger().log("success");
        
        return null;
    }

	private String filter(S3Event input) {
		return input.toJson().replace("\\s+", "-");
	}

}
