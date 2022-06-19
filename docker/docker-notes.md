RELEASE PREP
* Run build and all tests - mvn spring-boot:run
* search and update version number (all files)
* tag version number
* Release on github 
** (more info)
* Build image in docker (below)
* push image (as version number and latest)

BUILD IMAGE IN SPRING

* Navigate to code directory
* mvn spring-boot:build-image -Dmaven.test.skip=true

Notes
* Needs docker daemon running
* Make need to delete "target" directory to force refresh
  ○ Check that any new samples are loaded
* To run from docker command line
  ○ docker run -p 7000:7000 redpiranha:2.2.1-SNAPSHOT

PUSHING BUILD TO DOCKERHUB
* docker login
** or docker login -u paulbrowne -p <password>
* docker images
* docker tag xxxxx-image-id paulbrowne/redpiranha:latest     		 #without this tag then it doesn't matchup to repository
* docker tag xxxxx-image-id paulbrowne/redpiranha:2.2.1-SNAPSHOT     
* docker push paulbrowne/redpiranha
* docker push paulbrowne/redpiranha:redpiranha:2.2.1-SNAPSHOT		 # Looks like we need to tell dockerhub about the version tag

TO TEST DOCKER HUB IMAGE
* check images are on dockerhub first
* remove local images
** (more info https://www.tutorialspoint.com/how-does-one-remove-a-docker-image)
** useful:  docker image prune --all
* Run docker compose using config file in this directory

RUNNING DOCKER IMAGES

Install Logs
* C:\Users\paulf\AppData\Local\Docker

Sources
* <https://www.baeldung.com/spring-native-intro>
* Spring Book
* Build run docker on Github  <https://blog.hildenco.com/2020/10/building-and-hosting-docker-images-on.html>
* Docker compose <https://github.com/docker/labs/blob/master/developer-tools/java/chapters/ch05-compose.adoc>

RUNNING WITHOUT BUILD
docker run -p 8080:8080 -p 8001:8001 -d --name jbpm-server-full jboss/jbpm-server-full:latesT


 
Docker Volumes
* Can be found in docker
* Windows Explorer
  ○ \\wsl$\docker-desktop-data\version-pack-data\community\docker\volumes
* Note that images persist localy

HOW TO MODIFY JBOSS DOCKER IMAGE
<https://linuxhandbook.com/modifying-docker-image/>
