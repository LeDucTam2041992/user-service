pipeline {

  environment {
    dockerimagename = "user-service"
    dockerImage = ""
  }

  agent any
  tools {
    maven 'Maven 3.8.7'
  }

  stages {

    stage('Checkout Source') {
      steps {
        git 'https://github.com/LeDucTam2041992/user-service'
        sh 'mvn clean install -Dmaven.test.skip=true'
      }
    }

    stage('Build image') {
      steps{
        script {
          dockerImage = docker.build dockerimagename
        }
      }
    }

    stage('Pushing Image') {
      environment {
               registryCredential = 'dockerhub-credentials'
           }
      steps{
        script {
          docker.withRegistry( 'https://registry.hub.docker.com', registryCredential ) {
            dockerImage.push("latest")
          }
        }
      }
    }
  }
}