pipeline {
    agent any
    
    environment {
        JAVA_HOME = 'C:\\Program Files\\Java\\jdk-17'
        MAVEN_HOME = 'C:\\apache-maven-3.9.9'
        PATH = "${env.MAVEN_HOME}\\bin;${env.JAVA_HOME}\\bin;${env.PATH}"
        PROJECT_NAME = 'FideiNexus Jenkins Demo'
        SONAR_TOKEN = '436e29401a649bcfcc100950e2a031bf16160e03'
        SLACK_TOKEN = '3cJHvdlprm5WNqhpuffov4zh'
        SLACK_CHANNEL = '#nuevo-canal'
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
            environment {
                JAVA_HOME_BACKUP = "${env.JAVA_HOME}"
                JAVA_HOME = 'D:\\Program Files\\Java\\jdk-21'
                PATH = "D:\\Program Files\\Java\\jdk-21\\bin;${env.MAVEN_HOME}\\bin;${env.PATH}"
            }
            steps {
                echo '🔍 Running SonarQube analysis with Java 21...'
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
            
            script {
                try {
                    slackSend(
                        color: 'good',
                        message: "✅ *BUILD SUCCESS*\nProject: ${PROJECT_NAME}\nBuild: #${BUILD_NUMBER}\nStatus: SUCCESS",
                        channel: "${SLACK_CHANNEL}",
                        tokenCredentialId: 'slack-token'
                    )
                } catch (Exception e) {
                    echo "Slack notification failed: ${e.message}"
                }
            }
        }
        
        failure {
            echo '❌ Pipeline failed!'
            echo "Check console output for details"
            
            script {
                try {
                    slackSend(
                        color: 'danger',
                        message: "❌ *BUILD FAILED*\nProject: ${PROJECT_NAME}\nBuild: #${BUILD_NUMBER}\nStatus: FAILURE",
                        channel: "${SLACK_CHANNEL}",
                        tokenCredentialId: 'slack-token'
                    )
                } catch (Exception e) {
                    echo "Slack notification failed: ${e.message}"
                }
            }
        }
        
        always {
            echo '🧹 Cleaning up workspace...'
            cleanWs()
        }
    }
}
