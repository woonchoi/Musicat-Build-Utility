pipeline {
    agent any
    
    environment {
        BUILD_BRANCH = "${env.gitlabBranch == 'dev-back'}"
    }
    stages {
        stage('Clone Repository') {
            when {
                expression { return BUILD_BRANCH == "true" }
            }
            steps {
                echo "Branch : ${env.gitlabBranch}"
                echo "Clone repository"
                git branch: "${env.gitlabBranch}", url: "<your repository url>", credentialsId: '<your credentials ID>'
            }
        }
        stage("Set environment") {
            when {
                expression { return BUILD_BRANCH == "true" }
            }
            steps {
                echo "Copy require file to backend folder"
                sh 'cp /var/jenkins_home/util/backend/docker-compose.yml .'
                sh 'cp /var/jenkins_home/util/backend/application.yml ./backend/src/main/resources/application.yml'
                sh 'cp /var/jenkins_home/util/backend/Dockerfile ./backend/'
            }
        }
        stage('Docker down') {
            when {
                expression { return BUILD_BRANCH == "true" }
            }
            steps {
                echo "Docker compose down"
                sh "sudo docker-compose -f docker-compose.yml down --rmi all"
            }
        }
        stage('Docker build') {
            when {
                expression { return BUILD_BRANCH == "true" }
            }
            steps {
                echo "docker compose build"
                sh "sudo docker-compose -f docker-compose.yml build --no-cache"
            }
            post {
                success {
                    echo "Success to build"
                }
                failure {
                    echo "Docker build failed. clear unused file"
                    sh "sudo docker system prune -f"
                    error 'pipeline aborted'
                }
            }
        }
        stage('Docker up') {
            when {
                expression { return BUILD_BRANCH == "true" }
            }
            steps {
                echo "docker compose up"
                sh "sudo docker-compose -f docker-compose.yml up -d"
            }
            post {
                success {
                    echo "Success to run docker-compose"
                }
                failure {
                    echo "Docker run failed. clear unused file"
                    sh "sudo docker system prune -f"
                    error 'pipeline aborted'
                }
            }
        }
        stage('Docker clear') {
            steps{
                sh "docker system prune -f"
            }
        }
    }
}