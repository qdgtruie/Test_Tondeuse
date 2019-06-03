#!/bin/bash

#set -e

#echo building image for GCP registry
docker build -t eu.gcr.io/${PROJECT_NAME_PRD}/${DOCKER_IMAGE_NAME} .


echo $GCLOUD_SERVICE_KEY_PRD | base64 --decode -i > ${HOME}/gcloud-service-key.json
gcloud auth activate-service-account --key-file ${HOME}/gcloud-service-key.json

echo preparing GCP context
gcloud --quiet config set project $PROJECT_NAME_PRD
gcloud --quiet config set container/cluster $CLUSTER_NAME_PRD
gcloud --quiet config set compute/zone ${CLOUDSDK_COMPUTE_ZONE}
gcloud --quiet container clusters get-credentials $CLUSTER_NAME_PRD

echo about to deploy to eu.gcr.io/${PROJECT_NAME_PRD}/${DOCKER_IMAGE_NAME}
gcloud docker -- push eu.gcr.io/${PROJECT_NAME_PRD}/${DOCKER_IMAGE_NAME}

yes | gcloud container images add-tag eu.gcr.io/${PROJECT_NAME_PRD}/${DOCKER_IMAGE_NAME} eu.gcr.io/${PROJECT_NAME_PRD}/${DOCKER_IMAGE_NAME}:latest

kubectl config view
kubectl config current-context

kubectl set image deployment/${KUBE_DEPLOYMENT_NAME} ${KUBE_DEPLOYMENT_CONTAINER_NAME}=eu.gcr.io/${PROJECT_NAME_PRD}/${DOCKER_IMAGE_NAME}:latest
