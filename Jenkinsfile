// Jenkins pipeline for my QA projects: the API tests (day5) and the UI tests (day6).
// The agent is a Linux machine with Java 21, Maven and Chrome installed, so I use sh.
pipeline {
    agent any

    stages {
        stage('Checkout') {
            steps {
                // gets the exact commit that started this build
                checkout scm
            }
        }

        stage('Build') {
            steps {
                // cleans and compiles both projects. Clean removes old reports, so a skipped stage cannot publish the results of an earlier build.
                sh 'mvn -f day5/toolshop-api-tests/pom.xml clean compile'
                sh 'mvn -f day6/toolshop-ui-tests/pom.xml clean compile'
            }
        }

        stage('API Tests') {
            steps {
                sh 'mvn -f day5/toolshop-api-tests/pom.xml test'
            }
        }

        stage('UI Tests (headless)') {
            steps {
                // the CI server has no screen, so Chrome runs without a window (-Dheadless=true, see DriverFactory)
                sh 'mvn -f day6/toolshop-ui-tests/pom.xml test -Dheadless=true'
            }
        }
    }

    post {
        always {
            // publishes the results of both projects, also when a test failed
            junit '**/target/surefire-reports/*.xml'
        }
    }
}
