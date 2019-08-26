node("master"){
  stage ('Build') { 
    docker build -t boxfuse-app .
  }
}
