node("master"){
  stage ('Build') { 
    mvn clean package
  }
}
