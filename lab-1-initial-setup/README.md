# Commands to load this application and its route

Edit the initial-setup-<user#>.yaml file and replace ROUTE-DOMAIN (example: iot-demo-371b.apps.rhpds310.openshift.opentlc.com) before processing the file.

If you pick the appropriate user file it will already have the username and MQTT port correct. The domain always needs to be updated for each new workshop.
 
### Commands

Loading the initial ConfigMap and common s2i ImageStream
```
oc create -f initial-setup-<user#>.yaml
```
 
Updating the ConfigMap or ImageStream
```
oc replace -f initial-setup-<user#>.yaml
```

