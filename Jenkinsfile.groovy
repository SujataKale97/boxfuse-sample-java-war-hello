node("master"){
  stage ('Build') { 
    git credentialsId: 'ea4c3770-b2ed-4639-9ffc-cc3e586e454c'
    docker build -t boxfuse-app .
  }
}
