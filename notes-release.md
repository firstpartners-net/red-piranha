# RELEASE PREP

These notes outline what is need to move from source coe to full release

## GITHUB RELEASE
* Run build and all tests - mvn spring-boot:run
* search and update version number (all files)
** ??
* tag version number
** ??
* Release on github 
** (?? more info)
* Build image in docker (below)
* push image (as version number and latest)
** ?? more info on how to do this in docker hub

## BUILD IMAGE IN SPRING

* Navigate to code directory
* mvn spring-boot:build-image -Dmaven.test.skip=true

## Notes
* Needs docker daemon running
* Make need to delete "target" directory to force refresh
  ○ Check that any new samples are loaded
* To run from docker command line
  ○ docker run -p 7000:7000 redpiranha:2.2.1-SNAPSHOT

PUSHING BUILD TO DOCKERHUB
* docker login
** e.g. docker login -u paulbrowne -p <password>
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


BUILDING DOCKER IMAGE USING SPRING

 Build a docker container using spring application?
    • mvn spring-boot:build-image -Dmaven.test.skip=true
    • Needs docker daemon running
    • Make need to delete "target" directory to force refresh
        ○ Check that any new samples are loaded
    • To run from docker command line
        ○ docker run -p 7000:7000 redpiranha:2.0.1-SNAPSHOT
Or
docker run -p 7000:7000 paulbrowne/redpiranha:latest

	• Note - no paulbrowne
	• ?? Where docker stores images




SCREENSHOT OF CONFIGURATION THAT APPEARS WHEN BUILDING IMAGE
The configuration used in this image


In container, maps to : /workspace/BOOT-INF/classes/examples$
    • Can use Bash within Spring generated image
    



Docker Volumes
    • Can be found in docker
    • Windows Explorer
        ○ \\wsl$\docker-desktop-data\version-pack-data\community\docker\volumes
    • Note that images persist localy 
SCREENSHOT OF DOCKER VOLUES IN THE DESKTOP



PUSHING BUILD TO DOCKERHUB
    • docker login
    • docker images
    • docker tag 902ab60e9998 paulbrowne/redpiranha:latest
    • docker push paulbrowne/redpiranha:latest

Other useful commands
	docker image rm -f image_id


DOCKER COMPOSE
	• Go intro docker directory
	• docker-compose up -d 
	• -d gives logging follow on



    
HOW TO MODIFY JBOSS DOCKER IMAGE
https://linuxhandbook.com/modifying-docker-image/


STILL VALID
RUNNING WITHOUT BUILD
???
docker run -p 8080:8080 -p 8001:8001 -d --name jbpm-server-full jboss/jbpm-server-full:latesT
    


