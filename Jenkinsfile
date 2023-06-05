pipeline {

  environment {
    dockerimagename = "tamleduc/user-service"
    dockerImage = ""
  }

  agent any
  tools {
    maven 'Maven_3_8_7'
    dockerTool 'docker_20_10_22'
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