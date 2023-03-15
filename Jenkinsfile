pipeline {
  agent any

  stages {

    stage('show content') {
           steps {
             sh 'ls -al'
             sh 'pwd'
           }
         }
    stage('Run tests') {
          steps {
            sh 'ls -al'
            sh 'docker compose up test'
          }
        }

    stage('Build') {
      steps {
        sh 'docker compose up build'
      }
    }

    stage('Build Docker image') {
      steps {
        sh 'docker build -t mx_blog .'
      }
    }

  }
}

