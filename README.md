# How to Run Project

The jdk version of this project is 17.

## 1. Please Install Weaviate
```bash

docker docker run -p 8080:8080 -p 50051:50051 cr.weaviate.io/semitechnologies/weaviate:1.31.5

```
If you can't use Docker, install the binary file and run it.
```bash

git clone https://github.com/weaviate/weaviate.git
cd weaviate
tools/dev/run_dev_server.sh <configuration>
```
For configuration settings, please refer to this site:

https://docs.weaviate.io/contributor-guide/weaviate-core/setup#running-from-source

The default port for weaviate is 8080, but if you want to change it, you need to change the port in the `application.yml` file.


## 2. Openai api key
Please create a `.env` file inside the resources folder.

After that, please enter your openai api key in the format below.

```env
OPENAI_API_KEY=... 
```
## 3. Build
```bash

./gradlew bootJar
```

## 4. Run
```bash

nohup java -jar -Djavax.net.ssl.trustStore=/etc/ssl/certs/java/cacerts -Djavax.net.ssl.trustStorePassword=changeit  spring-ai-test/build/libs/ici-insight-0.0.1-SNAPSHOT.jar > log.out 2>&1 &
```