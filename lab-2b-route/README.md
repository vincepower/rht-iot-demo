# Commands to load this application and its route

Update GITHUB-REPO to be your repo, the default would be https://github.com/vincepower/rht-iot-demo.git 

 
### Variables
`
GITHUB-REPO=https://github.com/vincepower/rht-iot-demo.git

APP-NAME=lab-2b-route  

`


### Commands
`
oc new-app fabric8/s2i-java:2.1~$GITHUB-REPO --context-dir=$APP-NAME --name=$APP-NAME

oc expose svc/$APP-NAME

`
 

