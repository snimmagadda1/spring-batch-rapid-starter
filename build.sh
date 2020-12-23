#!/bin/sh

mvn clean package

echo "Spring batch build completed"
echo "Build & push"
echo "$DOCKER_PASSWORD" | docker login -u "$DOCKER_USERNAME" --password-stdin
docker build . -t snimmagadda/stacke-batch-mysql:"$TRAVIS_BUILD_NUMBER"
docker tag snimmagadda/spring-batch-rapid-starter:"$TRAVIS_BUILD_NUMBER" snimmagadda/spring-batch-rapid-starter:latest
docker push snimmagadda/spring-batch-rapid-starter:"$TRAVIS_BUILD_NUMBER" && docker push snimmagadda/spring-batch-rapid-starter:latest