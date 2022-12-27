For book
	• Install docker https://docs.docker.com/desktop/windows/install/
	• Learning within free usage
	• Look at instructions carefully - may need wsl installed
	• Use VSCode , attach as Explorer view?
	
Images
	Older image: https://registry.hub.docker.com/r/jboss/business-central-workbench/#!
	New Image: https://quay.io/repository/kiegroup/business-central-workbench
	

Most likely sample to run: https://github.com/jboss-dockerfiles/business-central/tree/main/server

	docker run -p 8080:8080 -p 8001:8001 -d --name jbpm-server-full jboss/jbpm-server-full:latest
	
	
	Get Quay equivalent





Signing redhat quay.io
Download docker and ensure running

SCREENSHOT OF DOCKER PULLING DOWN JBPM WORKBENCH IMAGE

C: run —p 8089: 8089 —p 8001 : 8001 —d ——name jbpm—workbench quay. io/kiegroup/busin
—showcase : Latest
Unable to find image 'quay . locally
Latest: Pulling from

75f829a71a1c :
7b11+2U6b3d3:
b765648c2a58:
506affUa9c5a:
166bcd8df17c :
b7c68ea1a2cf :
2ebd7e334251 :

8d97bad97db0:
66d3aa0ff224:
909bfd380@9d :
8eb7232bd627:
f2d264bb2294:

Pull complete
Pull complete
Pull complete
Pull complete
Extracting
Downloading

71.3MB/210.8MB
145.9MB/235.3MB

Download
Download
Download
Download
Download
Download
Download

complete
complete
complete
complete
complete
complete
complete



SCREENSHOT OF IMAGE RUNNING IN DOCKER DESKTOP






Docker cli button

Logs 

Open in Windows explorer - 
\\wsl$\docker-desktop-data\version-pack-data\community\docker\overlay2




Vscode docker tutorial
https://docs.microsoft.com/en-us/visualstudio/docker/tutorials/docker-tutorial



Where to get the showcase from:
https://quay.io/repository/kiegroup/business-central-workbench-showcase

docker pull quay.io/kiegroup/business-central-workbench-showcase

Issues with image
https://github.com/jboss-dockerfiles/business-central/issues


Was able to get working - 
jbpm-workbench
quay.io/kiegroup/business-central-workbench-showcase:latest
RUNNING

As per this ..
https://github.com/jboss-dockerfiles/drools/tree/master/drools-wb/showcase


docker run -p 8080:8080 -p 8001:8001 -d --name drools-workbench jboss/drools-workbench-showcase:latest

From <https://github.com/jboss-dockerfiles/drools/tree/master/drools-wb/showcase> 







RUNNING KIE SERVER
docker run -p 8081:8080 -d --name kie-server quay.io/kiegroup/kie-server-showcase:latest
 (MAPPED TO 8081)
https://registry.hub.docker.com/r/jboss/kie-server/#!

Add users

Done in 
quay.io/kiegroup/kie-server-showcase:latest

Latest
https://registry.hub.docker.com/r/jboss/kie-server-showcase/#!

Actual command to run with link 
docker run -p 8180:8080 -d --name kie-server --link business-central-wb:kie-wb quay.io/kiegroup/kie-server-showcase:latest

From <https://registry.hub.docker.com/r/jboss/kie-server-showcase/#!> 

How to access docker files from windows
https://stackoverflow.com/questions/33848947/accessing-docker-container-files-from-windows


HOW TO DOWNLOAD IN BSUINESS CENTRAL
	• Via top right … artifacts

VSCODE
	• Plugin
	



From
	• https://github.com/jboss-dockerfiles/business-central
	jBPM Server Full distribution
	https://jbpm.org/learn/gettingStarted.html
	

	







