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

* IAM -> User ->add user -> check Programmatic access ->  access id and key
* next permission -> attach existing policies ( check You created before) -> next
* Create user
* note the access id and key or download

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
* Configure lambda function -> choose runtime java8
* Select code entry type ->upload from amazon s3


#### add environment value
 ```
 DestinationBucket        destination-bucket
 DestinationAccessId      xxxxxxxx
 DestinationAccessKey     xxxxxxxxxxxxxxx
 DestinationBucketRegion  ap-northeast-1
 ResourceBucketRegion     ap-northeast-1
 ResourceBucket           resource-bucket
```
* handler.BucketSyncHandler
* create custom role ->  allow 
* next->create function
* IAM -> role -> select role you created -> attach policy which you created before to role

### create resource bucket 
* S3 -> create bucket YOUR_RESOURCE_BUCKET
* Properties -> events-> Events->selest Object created (All)
* Send to lambda function

