## Get the code
```shell
git clone https://github.com/indigo-iam/iam.git
git fetch --tags
git checkout -b b1.8.2p2 v1.8.2p2
```

## Things to change
- Specify the image name mysql 
- Specify the volume mount for mysql
- force trust anchor update (by setting it as 1)
- setup SMTP server to listen for mails
- change profile from `mysql-test` to `prod`
- provide the key store location i.e. keystore.jwks to get one follow the instruction in this [link](https://indigo-iam.github.io/v/v1.8.2/docs/getting-started/jwk/)
> Make sure to have maven 3.6.x + and java 11+
```shell
git clone https://github.com/mitreid-connect/json-web-key-generator
mvn package
java -jar target/json-web-key-generator-0.9-SNAPSHOT-jar-with-dependencies.jar \
  -t RSA -s 1024 -S -i rsa1
```

### Optional settings.
- provide client key and secrets. ALso google if needed.
- some mysql settings. [refer here](https://indigo-iam.github.io/v/v1.8.2/docs/getting-started/mariadb/)


## Run and Use
- Follow the instruction from this [page](https://indigo-iam.github.io/v/v1.8.2/docs/getting-started/basic_setup/)

## Useful debug commands
```shell
docker run -i   --name iam-login-service   --net=iam -p 8080:8080   --env-file=./config   -v ./keystore.jks:/keystore.jks:ro   --restart unless-stopped   indigoiam/iam-login-service:latest
```