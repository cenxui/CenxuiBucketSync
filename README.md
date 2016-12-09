# LambdaBucketSync
Sychronize different accounts buckets with AWS Lambda Function.
## Destination account
### create policy 
* IAM-> Policies -> create policy -> select create your own policy 
* add name in console and copy and paste to console.
* rename YOUR_DESTINATION_BUCKET as your bucket name.
```
{
    "Version": "2012-10-17",
    "Statement": [
        {
            "Sid": "Stmt1481199953000",
            "Effect": "Allow",
            "Action": [
                "s3:ListBucket"
            ],
            "Resource": [
                "arn:aws:s3:::YOUR_DESTINATION_BUCKET"
            ]
        },
        {
            "Sid": "Stmt1481199988000",
            "Effect": "Allow",
            "Action": [
                "s3:PutObject"
            ],
            "Resource": [
                "arn:aws:s3:::YOUR_DESTINATION_BUCKET/*"
            ]
        }
    ]
}
```
###create user
* IAM -> User -> add user -> add access id and key (note it)
* add permission -> add policy (You created before)
### create bucket
* S3 -> create bucket YOUR_DESTINATION_BUCKET

##Resouce account
* IAM-> Policies -> create policy -> select create your own policy 
* add name in console and copy
* rename YOUR_RESOURCE_BUCKET as your bucket name.
```
{
    "Version": "2012-10-17",
    "Statement": [
        {
            "Sid": "Stmt1481200423000",
            "Effect": "Allow",
            "Action": [
                "s3:ListBucket"
            ],
            "Resource": [
                "arn:aws:s3:::YOUR_RESOURCE_BUCKET"
            ]
        },
        {
            "Sid": "Stmt1481200455000",
            "Effect": "Allow",
            "Action": [
                "s3:GetObject"
            ],
            "Resource": [
                "arn:aws:s3:::YOUR_RESOURCE_BUCKET/*"
            ]
        }
    ]
}
```
### create Lambda function
* Service -> Compute -> LambdaFunction -> create lambda function 
* create role -> add policy you created before.
* add name and create role -> upload code (this code in jar or zip)

### add environment value

* DestinationBucket		destination-bucket
* DestinationAccessId		xxxxxxxx
* DestinationAccessKey 	xxxxxxxxxxxxxxx
* DestinationBucketRegion	region (ap-northeast-1)
* ResourceBucketRegion   	region (ap-northeast-1)
* ResourceBucket 		resource-bucket

### create resource bucket 
* S3 -> create bucket YOUR_RESOURCE_BUCKET
* Add event trigger Lambday function and set all put event 
