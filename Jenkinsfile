pipeline {
    agent any
    
    environment {
        PROJECT_NAME = 'FideiNexus Jenkins Demo'
    }
    
    stages {
        stage('Test Slack Notification') {
            steps {
                echo '🔔 Testing Slack notification...'
                echo 'Build completed successfully!'
            }
        }
    }
    
    post {
        success {
            echo '✅ Pipeline completed successfully!'
            slackSend(
                color: 'good',
                message: "✅ *BUILD SUCCESS*\nProject: ${PROJECT_NAME}\nBuild: #${BUILD_NUMBER}\nStatus: SUCCESS",
                teamDomain: 'jenkinsfideinexus',
                channel: '#nuevo-canal',
                tokenCredentialId: 'slack-token'
            )
        }
        
        failure {
            echo '❌ Pipeline failed!'
            slackSend(
                color: 'danger',
                message: "❌ *BUILD FAILED*\nProject: ${PROJECT_NAME}\nBuild: #${BUILD_NUMBER}\nStatus: FAILURE",
                teamDomain: 'jenkinsfideinexus',
                channel: '#nuevo-canal',
                tokenCredentialId: 'slack-token'
            )
        }
    }
}
