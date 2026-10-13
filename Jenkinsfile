pipeline {
    agent any
    
    options {
        timeout(time: 30, unit: 'MINUTES')
        timestamps()
        buildDiscarder(logRotator(numToKeepStr: '5'))
    }
    
    tools {
        maven 'Maven-3.9.9'
        jdk 'JDK-17'
    }
    
    environment {
        PROJECT_NAME = 'FideiNexus Jenkins Demo'
        SONAR_TOKEN = credentials('sonarcloud-token')
        JAVA_HOME = 'C:\\Program Files\\Java\\jdk-17'
        PATH = "${JAVA_HOME}\\bin;C:\\apache-maven-3.9.9\\bin;${env.PATH}"
    }
    
    stages {
        stage('Checkout') {
            steps {
                echo 'Cloning repository from GitHub...'
                git branch: 'main',
                    url: 'https://github.com/DubanSalas/fideinexus-jenkins-demo.git'
                echo 'Repository cloned successfully'
            }
        }
        
        stage('Build') {
            steps {
                echo 'Building project with Maven...'
                bat 'mvn clean compile'
                echo 'Build completed successfully'
            }
        }
        
        stage('Unit Tests') {
            steps {
                echo 'Running unit tests...'
                script {
                    try {
                        bat 'mvn test'
                        echo 'Tests completed successfully'
                    } catch (Exception e) {
                        echo "Tests failed: ${e.message}"
                        echo "Continuing pipeline for demo purposes..."
                        currentBuild.result = 'UNSTABLE'
                    }
                }
            }
            post {
                always {
                    script {
                        try {
                            junit allowEmptyResults: true, testResults: '**/target/surefire-reports/*.xml'
                        } catch (Exception e) {
                            echo "JUnit report generation failed: ${e.message}"
                        }
                    }
                }
            }
        }
        
        stage('SonarQube Analysis') {
            steps {
                echo 'Running SonarQube analysis...'
                withSonarQubeEnv('SonarCloud') {
                    bat """
                        mvn sonar:sonar ^
                        -Dsonar.projectKey=DubanSalas_fideinexus-jenkins-demo ^
                        -Dsonar.organization=dubansalas ^
                        -Dsonar.host.url=https://sonarcloud.io ^
                        -Dsonar.token=${SONAR_TOKEN}
                    """
                }
                echo 'SonarQube analysis completed'
            }
        }
        
        stage('JMeter Load Tests') {
            steps {
                echo 'Running JMeter load tests...'
                script {
                    try {
                        def scenarios = ['green', 'yellow', 'red']
                        scenarios.each { scenario ->
                            echo "Running JMeter scenario: ${scenario}"
                            timeout(time: 5, unit: 'MINUTES') {
                                bat """
                                    jmeter -n -t jmeter\\scenario-${scenario}.jmx ^
                                    -l results\\${scenario}-results.jtl ^
                                    -e -o results\\${scenario}-report
                                """
                            }
                            echo "Scenario ${scenario} completed"
                        }
                    } catch (Exception e) {
                        echo "JMeter tests failed or timed out: ${e.message}"
                        echo "Continuing pipeline execution..."
                        currentBuild.result = 'UNSTABLE'
                    }
                }
            }
        }
        
        stage('Selenium Functional Tests') {
            steps {
                echo 'Running Selenium functional tests...'
                script {
                    try {
                        timeout(time: 3, unit: 'MINUTES') {
                            bat '''
                                python selenium\\test1-login.py
                                python selenium\\test2-request.py
                            '''
                        }
                        echo 'Selenium tests completed'
                    } catch (Exception e) {
                        echo "Selenium tests failed or timed out: ${e.message}"
                        echo "This is expected if the application is not running on localhost:4200"
                        currentBuild.result = 'UNSTABLE'
                    }
                }
            }
        }
    }
    
    post {
        success {
            echo 'Pipeline completed successfully!'
            slackSend(
                color: 'good',
                message: """
                    *BUILD SUCCESS*
                    Project: FideiNexus Jenkins Demo
                    Build: #${BUILD_NUMBER}
                    Status: SUCCESS
                    Duration: ${currentBuild.durationString}
                    
                    - Build completed
                    - Tests passed
                    - SonarQube analysis done
                    - JMeter tests executed
                    - Selenium tests passed
                    
                    <${BUILD_URL}|View Build>
                """,
                teamDomain: 'jenkinsfideinexus',
                channel: '#nuevo-canal',
                tokenCredentialId: 'slack-token'
            )
        }
        
        failure {
            echo 'Pipeline failed!'
            slackSend(
                color: 'danger',
                message: """
                    *BUILD FAILED*
                    Project: FideiNexus Jenkins Demo
                    Build: #${BUILD_NUMBER}
                    Status: FAILURE
                    Duration: ${currentBuild.durationString}
                    
                    <${BUILD_URL}console|View Console Output>
                """,
                teamDomain: 'jenkinsfideinexus',
                channel: '#nuevo-canal',
                tokenCredentialId: 'slack-token'
            )
        }
        
        unstable {
            echo 'Pipeline completed with warnings'
            slackSend(
                color: 'warning',
                message: """
                    *BUILD UNSTABLE* ⚠️
                    Project: FideiNexus Jenkins Demo
                    Build: #${BUILD_NUMBER}
                    Status: UNSTABLE
                    Duration: ${currentBuild.durationString}
                    
                    ✅ Build compiled successfully
                    ⚠️ Some tests failed or were skipped
                    ✅ Code quality analysis completed
                    ℹ️ Pipeline continued for demo purposes
                    
                    <${BUILD_URL}|View Build Details>
                """,
                teamDomain: 'jenkinsfideinexus',
                channel: '#nuevo-canal',
                tokenCredentialId: 'slack-token'
            )
        }
    }
}
