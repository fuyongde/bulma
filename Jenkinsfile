pipeline {
  agent any
  stages {
    stage('Build') {
      steps {
        sh 'mvn clean install -f bulma-parent/pom.xml'
      }
    }
  }
}