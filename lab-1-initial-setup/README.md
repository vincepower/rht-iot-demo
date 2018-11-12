# Commands to load this application and its route

To process the ConfigMap that is required for this lab there are a couple steps.

## Step 1

You will need your assigned User ID (password is openshift) and to make note of the project ID you are assigned.

```
$ oc login https://master.ademo-cc3e.openshiftworkshop.com
The server uses a certificate signed by an unknown authority.
You can bypass the certificate check, but any data you send to the server could be intercepted by others.
Use insecure connections? (y/n): y

Authentication required for https://master.ademo-cc3e.openshiftworkshop.com:443 (openshift)
Username: user3
Password:
Login successful.

You have one project on this server: "iot-demo-ademo-cc3e-user3"

Using project "iot-demo-ademo-cc3e-user3".

```

## Step 2

Gather the External port, in this example it is 1883. Whatever is in that place is the number you want.

```
$ oc get service ec-broker-mqtt
NAME             TYPE       CLUSTER-IP      EXTERNAL-IP   PORT(S)          AGE
ec-broker-mqtt   NodePort   172.30.60.189   <none>        1883:31886/TCP   7h
```


## Step 3

Process the initial-template.yaml file with the variables you have discovered

### Commands

Loading the initial ConfigMap and common s2i ImageStream
```
oc process -f initial-template.yaml \
    -p MQTTPORT=1883 \
    -p PROJECTID=iot-demo-ademo-cc3e-user3 \
    -p USERID=user3 \
    > initial-setup.yaml
```

## Step 4

Create the ConfigMap

### Commands

Loading the initial ConfigMap and common s2i ImageStream
```
oc create -f initial-setup.yaml
```

Updating the ConfigMap or ImageStream
```
oc replace -f initial-setup.yaml
```

