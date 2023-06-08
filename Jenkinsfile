pipeline {

  environment {
    dockerRegistry = "https://registry.hub.docker.com"
    dockerimagename = "tamleduc/user-service"
    dockerImage = ""
  }

  agent any
  tools {
    maven 'Maven_3_8_7'
    dockerTool 'docker_20_10_22'
  }

  stages {
    stage('Cancel Previous Builds') {
      steps {
        script {
          cancelPreviousBuilds()
        }
      }
    }

    stage('Checkout Source') {
      steps {
//         git 'https://github.com/LeDucTam2041992/user-service'
        checkout scmGit(branches: [[name: '*/master']], extensions: [], userRemoteConfigs: [[credentialsId: 'github-credentials', url: 'https://github.com/LeDucTam2041992/user-service']])
      }
    }

    stage('Build source') {
      steps {
        sh (script: 'mvn clean install -Dmaven.test.skip=true', returnStdout: true)
      }
    }

    stage('Build image') {
      steps{
        script {
          def DOCKER_IMAGE = dockerimagename + ':latest'
          dockerImage = docker.build("$DOCKER_IMAGE")
        }
      }
    }

    stage('Pushing Image') {
      environment {
               registryCredential = 'dockerhub-credentials'
           }
      steps{
        script {
          docker.withRegistry( dockerRegistry, registryCredential ) {
            dockerImage.push()
          }
        }
      }
    }

    stage('Remove local image') {
      steps{
        script {
          def imageTag = 'registry.hub.docker.com/' + dockerimagename
          sh (script: "docker image rm $dockerimagename", returnStdout: true)
          sh (script: "docker image rm $imageTag", returnStdout: true)
        }
      }
    }
  }
}

/*
(Note)
Script approvals in jenkins:
method hudson.model.Executor interrupt hudson.model.Result jenkins.model.CauseOfInterruption[]
method hudson.model.Job getBuilds
method hudson.model.Run getExecutor
method hudson.model.Run getNumber
method hudson.model.Run isBuilding
method jenkins.model.Jenkins getItemByFullName java.lang.String
new jenkins.model.CauseOfInterruption$UserInterruption java.lang.String
staticMethod jenkins.model.Jenkins getInstance
staticMethod org.codehaus.groovy.runtime.DefaultGroovyMethods toInteger java.lang.Number
*/
@NonCPS //https://medium.com/@vedranvucetic/run-only-last-triggered-job-for-branch-in-descriptive-jenkins-pipeline-a07bb84ae075
def cancelPreviousBuilds() {
    def jobName = env.JOB_NAME
    def buildNumber = env.BUILD_NUMBER.toInteger()
    /* Get job name */
    def currentJob = Jenkins.instance.getItemByFullName(jobName)

    /* Iterating over the builds for specific job */
    for (def build : currentJob.builds) {
        def exec = build.getExecutor()
        /* If there is a build that is currently running and it's not current build */
        if (build.isBuilding() && build.number.toInteger() != buildNumber && exec != null) {
            /* Then stop it */
            exec.interrupt(
                    Result.ABORTED,
                    new CauseOfInterruption.UserInterruption("Aborted by #${currentBuild.number}")
            )
            println("Aborted previously running build #${build.number}")
        }
    }
}