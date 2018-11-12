# Commands to load this application and its route

Have a local clone of the git repo to work with makes these commands work better. Both require an local OpenShift client that is logged in as done in lab-1.

This will use the branch you specific in the new-app.yaml file, not what the local repo is set to.

## Option 1: Use the included new-app.yaml script

Edit the script and update the Git Repo (default https://github.com/vincepower/rht-iot-demo.git) and branch (default master) as required.

### Commands
``` oc create -f new-app.yaml ```

## Option 2: Use maven and fabric8

This will use whatever branch you're current local environment is set to.

### Commands
``` mvn fabric8:deploy ```
or if you want logs directly in the console you are running the command:
``` mvn fabric8:run ```

