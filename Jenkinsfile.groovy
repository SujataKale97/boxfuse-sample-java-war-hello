node("master"){
 stage ('Build') { 
    git credentialsId: 'ea4c3770-b2ed-4639-9ffc-cc3e586e454c', url: 'https://github.com/SujataKale97/boxfuse-sample-java-war-hello.git'
   bat '''
     
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
  echo #Deployment file  > busybox-deployment1.yaml
SETLOCAL ENABLEDELAYEDEXPANSION
for /f "delims=" %%a in (busybox-deployment.yaml) do (
    SET s=%%a
    SET s=!s:image_tag=%BUILD_NUMBER%!
    echo !s! >> busybox-deployment1.yaml
)
move /Y busybox-deployment1.yaml busybox-deployment.yaml
'''
/*    bat '''
    gcloud container clusters get-credentials standard-cluster-1 --zone us-central1-a --project hello-world-241305 '''
    bat '''
    kubectl delete deployment -l app=boxfuse
    kubectl create -f busybox-deployment.yaml
    '''*/
  }
}
