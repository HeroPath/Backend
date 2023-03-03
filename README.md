# Hero Path - Backend

<h1>Documentation</h1>

## Experience equation to level up from 1 to 300
```
5 * (1.5 ** (math.log10(x * 0.09 + 1) / math.log10(1.0703)) + x)
```

## DockerCommands:
```
sudo docker images
sudo docker ps -a
sudo docker build --build-arg JAR_FILE=out/artifacts/heropath_backend_jar/*.jar -t heropath-backend .
sudo docker run -p 8080:8080 heropath-backend
```