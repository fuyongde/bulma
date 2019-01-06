pipeline {
  agent any
  options {
    buildDiscarder logRotator(artifactDaysToKeepStr: '', artifactNumToKeepStr: '', daysToKeepStr: '3', numToKeepStr: '5')
  }
  stages {
    stage('Build') {
      tools {
        maven 'maven'
        jdk 'JDK8'
      }
      steps {
        sh 'mvn clean install -f bulma-parent/pom.xml'
      }
    }
  }
}