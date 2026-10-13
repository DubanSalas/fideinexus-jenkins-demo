pipeline {
    agent any
    
    tools {
        maven 'Maven-3.9.9'
        jdk 'JDK-17'
    }
    
    environment {
        PROJECT_NAME = 'FideiNexus Jenkins Demo'
        SONAR_TOKEN = credentials('sonar-token')
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
                bat 'mvn test'
                echo 'Tests completed'
            }
            post {
                always {
                    junit '**/target/surefire-reports/*.xml'
                    jacoco execPattern: '**/target/jacoco.exec'
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
                    def scenarios = ['green', 'yellow', 'red']
                    scenarios.each { scenario ->
                        echo "Running JMeter scenario: ${scenario}"
                        bat """
                            jmeter -n -t jmeter\\scenario-${scenario}.jmx ^
                            -l results\\${scenario}-results.jtl ^
                            -e -o results\\${scenario}-report
                        """
                        echo "Scenario ${scenario} completed"
                    }
                }
            }
            post {
                always {
                    perfReport sourceDataFiles: 'results/*-results.jtl'
                }
            }
        }
        
        stage('Selenium Functional Tests') {
            steps {
                echo 'Running Selenium functional tests...'
                script {
                    bat '''
                        python selenium\\test1-login.py
                        python selenium\\test2-request.py
                    '''
                }
                echo 'Selenium tests completed'
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
                    Project: ${PROJECT_NAME}
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
                    Project: ${PROJECT_NAME}
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
            slackSend(
                color: 'warning',
                message: """
                    *BUILD UNSTABLE*
                    Project: ${PROJECT_NAME}
                    Build: #${BUILD_NUMBER}
                    Status: UNSTABLE
                    
                    <${BUILD_URL}|View Build>
                """,
                teamDomain: 'jenkinsfideinexus',
                channel: '#nuevo-canal',
                tokenCredentialId: 'slack-token'
            )
        }
    }
}
