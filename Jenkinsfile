pipeline {
    agent any
    
    environment {
        JAVA_HOME = 'C:\\Program Files\\Java\\jdk-17'
        MAVEN_HOME = 'C:\\apache-maven-3.9.9'
        PATH = "${env.MAVEN_HOME}\\bin;${env.JAVA_HOME}\\bin;${env.PATH}"
        PROJECT_NAME = 'FideiNexus Jenkins Demo'
        SONAR_TOKEN = '436e29401a649bcfcc100950e2a031bf16160e03'
    }
    
    stages {
        stage('Checkout') {
            steps {
                echo '📥 Cloning repository...'
                checkout scm
            }
        }
        
        stage('Build') {
            steps {
                echo '🔨 Building project...'
                bat 'mvn clean compile'
            }
        }
        
        stage('Unit Tests') {
            steps {
                echo '🧪 Running unit tests...'
                bat 'mvn test'
            }
            post {
                always {
                    junit '**/target/surefire-reports/*.xml'
                    jacoco(
                        execPattern: '**/target/jacoco.exec',
                        classPattern: '**/target/classes',
                        sourcePattern: '**/src/main/java'
                    )
                }
            }
        }
        
        stage('Package') {
            steps {
                echo '📦 Packaging application...'
                bat 'mvn package -DskipTests'
            }
        }
        
        stage('SonarQube Analysis') {
            steps {
                echo '🔍 Running SonarQube analysis...'
                bat """
                    mvn sonar:sonar ^
                    -Dsonar.token=%SONAR_TOKEN% ^
                    -Dsonar.host.url=https://sonarcloud.io ^
                    -Dsonar.organization=dubansalas ^
                    -Dsonar.projectKey=DubanSalas_fideinexus-jenkins-demo
                """
            }
        }
    }
    
    post {
        success {
            echo '✅ Pipeline completed successfully!'
            echo "Project: ${PROJECT_NAME}"
            echo "Build: #${BUILD_NUMBER}"
        }
        
        failure {
            echo '❌ Pipeline failed!'
            echo "Check console output for details"
        }
        
        always {
            echo '🧹 Cleaning up workspace...'
            cleanWs()
        }
    }
}
