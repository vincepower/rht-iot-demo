# Commands to load this application and its route

Update GITHUBREPO to be your repo, the default would be https://github.com/vincepower/rht-iot-demo.git 

 
### Variables
```
GITHUBREPO=https://github.com/vincepower/rht-iot-demo.git
APPNAME=lab-4-web
```

### Commands
```
oc new-app fabric8/s2i-java:2.1~$GITHUBREPO --context-dir=$APPNAME --name=$APPNAME
oc expose svc/$APPNAME
```

