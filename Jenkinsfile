pipeline {
  agent any
  stages {
    stage('Install') {
      steps { sh 'python3 -m venv .venv && .venv/bin/pip install -r requirements-dev.txt' }
    }
    stage('Test') {
      steps { sh '.venv/bin/python -m pytest -q' }
    }
    stage('Build artifact') {
      steps { sh 'mkdir -p dist && tar -czf dist/todo-api-${BUILD_NUMBER}.tar.gz app.py requirements.txt' }
    }
  }
  post {
    success { archiveArtifacts artifacts: 'dist/*.tar.gz', fingerprint: true }
  }
}
