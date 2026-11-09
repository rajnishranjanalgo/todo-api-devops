pipeline {
  agent any
  stages {
    stage('Test') { steps { sh 'mvn -B test' } }
    stage('Build') { steps { sh 'mvn -B package -DskipTests' } }
  }
  post { success { archiveArtifacts artifacts: 'target/todo-api.jar', fingerprint: true } }
}
