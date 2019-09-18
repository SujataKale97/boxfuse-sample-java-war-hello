node("master"){
 stage ('Build') 
 { 
    git credentialsId: 'ea4c3770-b2ed-4639-9ffc-cc3e586e454c', url: 'https://github.com/SujataKale97/boxfuse-sample-java-war-hello.git'
    bat 'mvn clean package '
 }
 stage('Sonar Quality Gate')
 {
   //Sonar Step
   bat 'mvn sonar:sonar -Dsonar.projectName=Maven-WebApp -Dsonar.host.url=http://localhost:9000'
 }
 stage ('Push Docker Image')
 {
    bat 'docker build -t boxfuse-app . '  //Build an image using Dockerfile
  
    //Push image to image repository
    bat '''
      docker tag boxfuse-app:latest gcr.io/hello-world-241305/image-repo:%BUILD_NUMBER%
      docker push gcr.io/hello-world-241305/image-repo:%BUILD_NUMBER%
     '''
 }
 stage ('Deployment')
 {
  //Replacing docker latest image in deployment file
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
  
   //Starting kubernetes cluster
    bat 'gcloud container clusters get-credentials standard-cluster-1 --zone us-central1-a --project hello-world-241305 '
  
    //Deploying the latest application
    bat '''
      kubectl delete deployment -l app=boxfuse
      kubectl create -f busybox-deployment.yaml
    '''
  }
}
