pipeline {
    agent any
    
    tools {
        maven 'Maven-3.9.9'
        jdk 'JDK-17'
    }
    
    environment {
        SONAR_TOKEN = credentials('sonarcloud-token')
        SLACK_WEBHOOK = credentials('slack-webhook-url')
        PROJECT_NAME = 'FideiNexus Jenkins Demo'
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
        
        stage('SonarQube Analysis') {
            steps {
                echo '📊 Running SonarQube analysis...'
                withSonarQubeEnv('SonarCloud') {
                    bat """
                        mvn sonar:sonar ^
                        -Dsonar.organization=dubansalas ^
                        -Dsonar.projectKey=DubanSalas_fideinexus-jenkins-demo ^
                        -Dsonar.host.url=https://sonarcloud.io ^
                        -Dsonar.token=%SONAR_TOKEN%
                    """
                }
            }
        }
        
        stage('Quality Gate') {
            steps {
                echo '🚦 Checking Quality Gate...'
                timeout(time: 5, unit: 'MINUTES') {
                    waitForQualityGate abortPipeline: true
                }
            }
        }
        
        stage('Package') {
            steps {
                echo '📦 Packaging application...'
                bat 'mvn package -DskipTests'
            }
        }
        
        stage('JMeter Load Tests') {
            steps {
                echo '⚡ Running JMeter load tests...'
                script {
                    // Escenario Verde (10 usuarios, 5 segundos)
                    bat """
                        jmeter -n -t jmeter/scenario-green.jmx ^
                        -l target/jmeter-green-results.jtl ^
                        -e -o target/jmeter-green-report
                    """
                    
                    // Escenario Amarillo (50 usuarios, 10 segundos)
                    bat """
                        jmeter -n -t jmeter/scenario-yellow.jmx ^
                        -l target/jmeter-yellow-results.jtl ^
                        -e -o target/jmeter-yellow-report
                    """
                    
                    // Escenario Rojo (100 usuarios, 20 segundos)
                    bat """
                        jmeter -n -t jmeter/scenario-red.jmx ^
                        -l target/jmeter-red-results.jtl ^
                        -e -o target/jmeter-red-report
                    """
                }
            }
            post {
                always {
                    perfReport(
                        sourceDataFiles: 'target/jmeter-*-results.jtl',
                        errorFailedThreshold: 10,
                        errorUnstableThreshold: 5
                    )
                }
            }
        }
        
        stage('Selenium Functional Tests') {
            steps {
                echo '🌐 Running Selenium functional tests...'
                script {
                    bat 'python selenium/test1-login.py'
                    bat 'python selenium/test2-request.py'
                }
            }
        }
    }
    
    post {
        success {
            echo '✅ Pipeline completed successfully!'
            slackSend(
                channel: '#jenkins-notifications',
                color: 'good',
                message: """
                    ✅ *BUILD SUCCESS*
                    Project: ${PROJECT_NAME}
                    Build: #${BUILD_NUMBER}
                    Duration: ${currentBuild.durationString}
                    Branch: ${GIT_BRANCH}
                    
                    📊 SonarQube: Quality Gate PASSED
                    🧪 Tests: All tests passed
                    ⚡ JMeter: Load tests completed
                    🌐 Selenium: Functional tests passed
                    
                    <${BUILD_URL}|View Build Details>
                """,
                webhookUrl: "${SLACK_WEBHOOK}"
            )
        }
        
        failure {
            echo '❌ Pipeline failed!'
            slackSend(
                channel: '#jenkins-notifications',
                color: 'danger',
                message: """
                    ❌ *BUILD FAILED*
                    Project: ${PROJECT_NAME}
                    Build: #${BUILD_NUMBER}
                    Duration: ${currentBuild.durationString}
                    Branch: ${GIT_BRANCH}
                    
                    Failed Stage: ${currentBuild.currentResult}
                    
                    <${BUILD_URL}console|View Console Output>
                """,
                webhookUrl: "${SLACK_WEBHOOK}"
            )
        }
        
        unstable {
            echo '⚠️ Pipeline is unstable!'
            slackSend(
                channel: '#jenkins-notifications',
                color: 'warning',
                message: """
                    ⚠️ *BUILD UNSTABLE*
                    Project: ${PROJECT_NAME}
                    Build: #${BUILD_NUMBER}
                    Duration: ${currentBuild.durationString}
                    Branch: ${GIT_BRANCH}
                    
                    Some tests or quality gates failed
                    
                    <${BUILD_URL}|View Build Details>
                """,
                webhookUrl: "${SLACK_WEBHOOK}"
            )
        }
        
        always {
            echo '🧹 Cleaning up workspace...'
            cleanWs()
        }
    }
}
