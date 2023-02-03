# aoweb-backend

# Demo: https: https://aoweb.vercel.app/

<h1>Documentation</h1>


## Experience equation to level up from 1 to 300
```
5 * (1.5 ** (math.log10(x * 0.09 + 1) / math.log10(1.07)) + x)
```

## DockerCommands:
```
sudo docker images
sudo docker ps -a
sudo docker build --build-arg JAR_FILE=out/artifacts/aoweb_backend_jar/*.jar -t aoweb-backend .
sudo docker run -p 8080:8080 aoweb-backend
```