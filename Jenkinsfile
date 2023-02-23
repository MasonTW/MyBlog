pipeline {
  agent any

  stages {
    stage('Clone source code') {
      steps {
        git 'https://github.com/MasonTW/MyBlog.git'
      }
    }

    stage('Build') {
      steps {
        sh './gradlew clean build --no-daemon'
      }
    }

    stage('Run tests') {
      steps {
        sh './gradlew test --no-daemon'
      }
    }

    stage('Build Docker image') {
      steps {
        sh 'docker build -t your-image-name .'
      }
    }

    stage('Push to AWS ECR') {
      steps {
        withCredentials([awsEcr(credentialsId: 'aws-ecr-credentials', region: 'us-west-2', registryIds: ['123456789012'])]) {
          sh 'aws ecr get-login-password --region us-west-2 | docker login --username AWS --password-stdin aws_account_id.dkr.ecr.us-west-2.amazonaws.com'
          sh 'docker tag your-image-name:latest aws_account_id.dkr.ecr.us-west-2.amazonaws.com/your-repository-name:latest'
          sh 'docker push aws_account_id.dkr.ecr.us-west-2.amazonaws.com/your-repository-name:latest'
        }
      }
    }
  }
}

