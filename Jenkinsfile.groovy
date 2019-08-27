node("master"){
  stage ('Build') { 
    git credentialsId: 'ea4c3770-b2ed-4639-9ffc-cc3e586e454c', url: 'https://github.com/SujataKale97/boxfuse-sample-java-war-hello.git'
   bat '''
    docker build -t boxfuse-app .
    '''
  }
  stage ('Push Docker Image')
  {
    withCredentials([[$class: 'FileBinding',credentialsId: '09140495-c54b-4f11-8ce7-4fcc177b052d', variable: 'PUSHIMAGE']]) {
      bat '''
      gcloud auth activate-service-account --key-file=%PUSHIMAGE%
      gcloud config set project hello-world-241305
      docker-credential-gcr configure-docker
      docker tag boxfuse-app:latest gcr.io/hello-world-241305/Image-Repo:${BUILD_TIMESTAMP}
      docker push gcr.io/hello-world-241305/Image-Repo:${BUILD_TIMESTAMP}
      '''
    }
  }
}
