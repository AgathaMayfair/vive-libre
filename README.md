# vive-libre

This is the code for token retriever project. If you want to work with it in your local machine. You just need to run it with the skeet15/auth-vivelibre docker image.

This project is containerized.

To use it, you need to pull this two docker images using docker pull command: 

docker pull agathamayfair/vive-libre:latest
docker pull skeet15/auth-vivelibre

Then, to connect both images use the docker-compose from this repo executing the following command inside the folder that contains the file:

docker-compose up

If you want to check if it works, go to your browser and checks if you are receiving a JSON with the token and date in: 

http://localhost:8081/get-token
