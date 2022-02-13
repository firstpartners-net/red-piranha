RUNNING DOCKER IMAGES

Install Logs
	• C:\Users\paulf\AppData\Local\Docker 

Book notes
	• Docker compose of several images needed
		○ Script and notes in docker folder
		○ SCREENSHOT OF WHAT DOCKER PROCESSES LOOK LIKE IN DOCKER DESKTOP
		○ 
		○ Trouble shooting - run separately


	• 3 levels
		○ Run on Laptop
		○ Run on Cloud
		○ Watch videos
		○ Get your IT to support
	• Paid for professional use, but free for personal and education use
		○ Examples in book fall into this category
		○ Likely to either scale up using docker, or move to cloud , or run RP directly using Java
			§ Docker not needed in long term, we're only using it as a quick start
		○ https://docs.docker.com/subscription/#docker-desktop-license-agreement

Sources
	• https://www.baeldung.com/spring-native-intro
	• Spring Book
	• Build run docker on Github  https://blog.hildenco.com/2020/10/building-and-hosting-docker-images-on.html
	• Docker compose https://github.com/docker/labs/blob/master/developer-tools/java/chapters/ch05-compose.adoc

RUNNING WITHOUT BUILD
???
docker run -p 8080:8080 -p 8001:8001 -d --name jbpm-server-full jboss/jbpm-server-full:latesT

	

BUILDING DOCKER IMAGE USING SPRING


?? Build a docker container using spring application?
	• mvn spring-boot:build-image -Dmaven.test.skip=true
	• Needs docker daemon running
	• Make need to delete "target" directory to force refresh
		○ Check that any new samples are loaded
	• To run from docker command line
		○ docker run -p 7000:7000 redpiranha:2.0.1-SNAPSHOT







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
	

