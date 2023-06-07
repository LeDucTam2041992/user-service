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
//     stage('Cancel Previous Builds') {
//       steps {
//         script {
//           cancelPreviousBuilds()
//         }
//       }
//     }

    stage('Checkout Source') {
      steps {
        git 'https://github.com/LeDucTam2041992/user-service'
//         checkout scmGit(branches: [[name: '*/master']], extensions: [], userRemoteConfigs: [[credentialsId: 'github-credentials', url: 'https://github.com/LeDucTam2041992/user-service']])
      }
    }

    stage('Build source') {
      steps {
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

  post {
    cleanup {
      deleteDir()
    }
  }
}

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