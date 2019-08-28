node("master"){
 stage ('Build') { 
    git credentialsId: 'ea4c3770-b2ed-4639-9ffc-cc3e586e454c', url: 'https://github.com/SujataKale97/boxfuse-sample-java-war-hello.git'
   bat '''
      mvn clean package
     // docker build -t boxfuse-app .
    '''
  }/*
 stage ('Push Docker Image')
  {
    bat '''
      docker tag boxfuse-app:latest gcr.io/hello-world-241305/image-repo:%BUILD_NUMBER%
      docker push gcr.io/hello-world-241305/image-repo:%BUILD_NUMBER%
      '''
  }*/
  stage ('Deployment')
  {
  bat '''
  (for /f "tokens=1,* delims=]" %%A in ('"type busybox-deployment.yaml|find /n /v """') do (

     set "line=image: gcr.io/hello-world-241305/image-repo:image_tag"
    if defined line (
        call set "line=echo.image: gcr.io/hello-world-241305/image-repo:image_tag:=%%BUILD_NUMBER%%"
        for /f "delims=" %%X in ('"echo."%%line%%""') do %%~X
    ) ELSE echo.

)) >busybox-deployment1.yaml
'''
/*    bat '''
    gcloud container clusters get-credentials standard-cluster-1 --zone us-central1-a --project hello-world-241305 '''
    bat '''
    kubectl delete deployment -l app=boxfuse
    kubectl create -f busybox-deployment.yaml
    '''*/
  }
}
