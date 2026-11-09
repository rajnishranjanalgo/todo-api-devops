pipeline {
  agent any
  environment {
    IMAGE_NAME = "todo-api"
    IMAGE_TAG = "${env.GIT_COMMIT ? env.GIT_COMMIT.take(7) : env.BUILD_NUMBER}"
  }
  stages {
    stage('Install') {
      steps { sh 'python3 -m pip install -r requirements-dev.txt' }
    }
    stage('Test') {
      steps { sh 'python3 -m pytest -q' }
    }
    stage('Build artifact') {
      steps { sh 'mkdir -p dist && tar -czf dist/todo-api-${BUILD_NUMBER}.tar.gz app.py requirements.txt' }
    }
    stage('Build image') {
      steps { sh 'docker build -t ${IMAGE_NAME}:${IMAGE_TAG} .' }
    }
  }
  post {
    success { archiveArtifacts artifacts: 'dist/*.tar.gz', fingerprint: true }
  }
}
