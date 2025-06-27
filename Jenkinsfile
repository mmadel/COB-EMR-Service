pipeline {
    agent any

    environment {
        APP_NAME = "emr"
        DEPLOY_DIR = "/opt/emr"
        SERVICE_NAME = "emr"
        HEALTH_CHECK_URL = "http://localhost:8070/emr/api/actuator/health" // remote service URL
        REMOTE_USER = "root"
        REMOTE_HOST = "161.97.126.192" // use container name, IP, or hostname
        REMOTE_SSH_PORT = "22"   // change to 2222 if needed
    }

    stages {
        stage('Build') {
            steps {
                sh 'mvn clean package -DskipTests'
            }
        }

stages {
        stage('Deploy') {
            steps {
                sshagent (credentials: ['test-env-user']) {
                    script {
                        echo "🚚 Preparing JAR for deployment..."
                        def jarName = sh(script: "ls target/*.jar | head -n 1", returnStdout: true).trim()
                        if (!jarName) {
                            error "❌ No JAR file found in target directory!"
                        }

                        echo "📦 Copying ${jarName} to ${REMOTE_HOST}:${DEPLOY_DIR}/${APP_NAME}.jar"
                        sh """
                            scp -P ${REMOTE_SSH_PORT} ${jarName} ${REMOTE_USER}@${REMOTE_HOST}:${DEPLOY_DIR}/${APP_NAME}.jar
                        """

                        echo "🔄 Restarting ${SERVICE_NAME} on remote host..."
                        sh """
                            ssh -o StrictHostKeyChecking=no -p ${REMOTE_SSH_PORT} ${REMOTE_USER}@${REMOTE_HOST} '
                                set -e
                                sudo systemctl restart ${SERVICE_NAME}
                            '
                        """
                    }
                }
            }
        }

        stage('Sanity Check') {
            steps {
                sshagent (credentials: ['test-env-user']) {
                    script {
                        echo "🔍 Performing health check on ${SERVICE_NAME}..."

                        def response = sh(
                            script: """
                                ssh -o StrictHostKeyChecking=no -p ${REMOTE_SSH_PORT} ${REMOTE_USER}@${REMOTE_HOST} '
                                    echo "⏳ Waiting for service to become healthy..."
                                    for i in {1..15}; do
                                        STATUS=\$(curl -s -o /dev/null -w "%{http_code}" ${HEALTH_CHECK_URL})
                                        echo "Attempt \$i: HTTP status \$STATUS"
                                        if [ "\$STATUS" = "200" ]; then
                                            echo "✅ Service is healthy."
                                            exit 0
                                        fi
                                        sleep 2
                                    done
                                    echo "❌ Service failed to become healthy within 30 seconds."
                                    exit 1
                                '
                            """,
                            returnStdout: true
                        ).trim()

                        echo "🧪 Health Check Output:\n${response}"
                    }
                }
            }
         }
        }
    }
}
