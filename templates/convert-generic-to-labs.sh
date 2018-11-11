#!/bin/bash

for APP in lab-2a-route lab-2a-web lab-2b-route lab-3-route lab-4-web
do
  sed -e "s/APPNAME/$APP/g" new-app-template.yaml > ../$APP/new-app.yaml
done

