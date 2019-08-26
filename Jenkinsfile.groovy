node("master"){
  stage ('Build') { 
   bat ''' mvn clean package '''
  }
}
