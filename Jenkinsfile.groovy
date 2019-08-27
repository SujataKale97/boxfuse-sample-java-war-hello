node("master"){
  stage ('Build') { 
    git credentialsId: 'ea4c3770-b2ed-4639-9ffc-cc3e586e454c', url: 'https://github.com/SujataKale97/boxfuse-sample-java-war-hello.git'
   bat '''
    docker build -t boxfuse-app .
    '''
  }
  stage ('Push Docker Image')
  {
    bat '''
      docker tag boxfuse-app:latest gcr.io/hello-world-241305/image-repo:boxfuse
      docker push gcr.io/hello-world-241305/image-repo:boxfuse
      '''
  }
}
