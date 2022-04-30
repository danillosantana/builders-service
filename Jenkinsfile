pipeline {
    agent none

    environment {
		REGISTRY            = "sa-saopaulo-1.ocir.io/grpx5uwwhzxr/ibk/pefisa-ibk-account"
		yamlREGISTRY        = "sa-saopaulo-1.ocir.io%grpx5uwwhzxr%ibk%"
        PROJECT             = "pefisa-ibk-account"
    }
    options{
         buildDiscarder(logRotator(numToKeepStr: '5', artifactNumToKeepStr: '5'))
    }
     

    stages {  

        stage('Code: Checkout'){
            agent { node { label 'linux' } }
            steps{
                script{
                    echo "INFO: Checkout code from branch [ ${BRANCH_NAME} ]"
                    checkout scm
                }
            }
        }

        stage('Code: Compile and Test'){
            agent {
                kubernetes {
                    cloud 'local'
                    yaml '''
apiVersion: v1
kind: Pod
metadata:
  labels:
    app: maven
  namespace: jenkins
spec:
  containers:
    - command:
        - cat
      image: "sa-saopaulo-1.ocir.io/grpx5uwwhzxr/ibk/maven-nexus-pefisa:002a923"
      name: maven
      tty: true
      volumeMounts:
        - mountPath: /root/.m2
          name: jenkins-m2-java-cache
        - mountPath: /kaniko/.m2/
          name: docker-config
        - name: kaniko-secret
          mountPath: /kaniko/.docker/
  imagePullSecrets:
    - name: container-registry
  volumes:
    - name: jenkins-m2-java-cache
      persistentVolumeClaim:
        claimName: jenkins-m2-image-cache
    - name: docker-config
      secret:
        secretName: oke-registry
    - name: kaniko-secret
      secret:
       secretName: container-registry
       items:
       - key: .dockerconfigjson
         path: config.json
'''
                }
            } 
            steps{
                container('maven') { 
                    script {
                        echo "INFO: Compiling code"
                        sh 'mvn -U clean install package'
                    }                    
                }
            }
            post{
                success{
                    //junit '**/*-report.xml'
                    stash name: 'java-artifacts', includes: "**/target/*.jar"
                }
            }            
        }

        stage('Image: Build and Push'){
            when {
                beforeAgent true
                anyOf {
                    branch "develop"
                    branch "release/*"
                    branch "fix/*"
                    branch "master"
                }
            }
            agent {
                kubernetes {
                    cloud 'local'
                    yaml '''
apiVersion: v1
kind: Pod
metadata:
 labels:
   sidecar: kaniko
 namespace: jenkins
spec:
 containers:
 - name: kaniko
   image: gcr.io/kaniko-project/executor:debug-v0.16.0

   command:
   - /busybox/cat
   tty: true
   volumeMounts:
   - mountPath: /root/workspace/cache
     name: jenkins-kaniko-image-cache
   - name: kaniko-secret
     mountPath: /kaniko/.docker/
 imagePullSecrets:
    - name: container-registry
 volumes:
   - name: jenkins-kaniko-image-cache
     persistentVolumeClaim:
       claimName: jenkins-kaniko-image-cache
   - name: kaniko-secret
     secret:
       secretName: container-registry
       items:
       - key: .dockerconfigjson
         path: config.json
'''
                }
            } 
            environment{
                // Image tag
                IMAGE_TAG = GIT_COMMIT.take(7)
            }
            steps {
                container('kaniko') {
                    script{
                        slackSend (baseUrl: "https://platformbuilders.slack.com/services/hooks/jenkins-ci/", channel: 'pernambucanas-ibk-notifications', color: '#00FF00',
                                   message: "Job Iniciado ${env.JOB_NAME}  ${env.BUILD_NUMBER}\n More info at: ${env.BUILD_URL}",
                                   teamDomain: "platformbuilders", token: "p1ZY5mC3duqwH697V6rbGaFz", tokenCredentialId: "jenkins-slack-integration")

                        
                        def REPOSITORY = "${REGISTRY}:${IMAGE_TAG}"

                        echo "INFO: Building and pushing container image"
                
                        // java artifacts
                        unstash 'java-artifacts'

                        sh """
                        /kaniko/executor \
                        --dockerfile=./Dockerfile \
                        --context=`pwd` \
                        --cache=true \
                        --cache-dir=/root/workspace/cache \
                        --destination=${REPOSITORY} \
                        --verbosity=debug 
                        """
                    }
                }
            }
        }        

        stage('Kubernetes: Deploy'){
            when {
                beforeAgent true
                anyOf {
                    branch "develop"
                    branch "release/*"
                    branch "fix/*"
                    branch "master"
                }
            }
            agent {
                kubernetes {
                    cloud 'local'
                    yaml '''
apiVersion: v1
kind: Pod
metadata:
  labels:
    app: pbdeploy
  namespace: jenkins
spec:
  serviceAccount: jenkins
  containers:
  - name: pbdeploy
    image: sa-saopaulo-1.ocir.io/grpx5uwwhzxr/ibk/pbdeploy:v0.3.0
    command:
    - cat
    tty: true
  imagePullSecrets:
    - name: container-registry
'''
                }
            }  
            
            environment{
                // Image tag
                IMAGE_TAG = GIT_COMMIT.take(7)
            }                      
            steps{
                container('pbdeploy'){
                    script{
                        if (BRANCH_NAME == 'develop'){
                            sh """
                              sed -i 's/{version}/${IMAGE_TAG}/' ./k8s/develop/deployment.yaml

                              kubectl apply -f ./k8s/develop/configmap.yaml -n ibkpj-dev
                              kubectl apply -f ./k8s/develop/secret.yaml -n ibkpj-dev
                              kubectl apply -f ./k8s/develop/deployment.yaml -n ibkpj-dev
                              """
                        } else if (BRANCH_NAME.contains("fix/")){
                              sh """
                                sed -i 's/{version}/${IMAGE_TAG}/' ./k8s/homolog/deployment.yaml

                                kubectl apply -f ./k8s/homolog/configmap.yaml -n ibkpj-hml
                                kubectl apply -f ./k8s/homolog/secret.yaml -n ibkpj-hml
                                kubectl apply -f ./k8s/homolog/deployment.yaml -n ibkpj-hml
                                """
                        }else if (BRANCH_NAME.contains("release/")){
                            sh """
                              sed -i 's/{version}/${IMAGE_TAG}/' ./k8s/homolog/deployment.yaml

                              kubectl apply -f ./k8s/homolog/configmap.yaml -n ibkpj-hml
                              kubectl apply -f ./k8s/homolog/secret.yaml -n ibkpj-hml
                              kubectl apply -f ./k8s/homolog/deployment.yaml -n ibkpj-hml
                              """
                        }
                        else if (BRANCH_NAME.contains("master")){
                            sh """
                              sed -i 's/{version}/${IMAGE_TAG}/' ./k8s/production/deployment.yaml

                              kubectl apply -f ./k8s/production/configmap.yaml -n ibkpj
                              kubectl apply -f ./k8s/production/secret.yaml -n ibkpj
                              kubectl apply -f ./k8s/production/deployment.yaml -n ibkpj
                              """
                        }
                    }
                }
            }
        }// end stage deploy

    
    } //end stages
    
    post {
        always {
            node('linux') {
                script{
                    echo 'Commands always executed.'
                }
            }
        }
        success {
            node('linux') {
                script{
                    slackSend baseUrl: "https://platformbuilders.slack.com/services/hooks/jenkins-ci/", channel: 'pernambucanas-ibk-notifications', color: '#00FF00', message: "SUCCESSFUL: Job '${env.JOB_NAME} [${env.BUILD_NUMBER}]' (${env.BUILD_URL})", teamDomain: "platformbuilders", token: "p1ZY5mC3duqwH697V6rbGaFz", tokenCredentialId: "jenkins-slack-integration"
                    echo 'INFO: Pipeline finished successifully!'
                }
            }            
        }
        failure {
            node('linux') {
                script{
                    slackSend baseUrl: "https://platformbuilders.slack.com/services/hooks/jenkins-ci/", channel: 'pernambucanas-ibk-notifications', color: '#FF0000', message: "FAILED: Job '${env.JOB_NAME} [${env.BUILD_NUMBER}]' (${env.BUILD_URL})", teamDomain: "platformbuilders", token: "p1ZY5mC3duqwH697V6rbGaFz", tokenCredentialId: "jenkins-slack-integration"
                    echo 'Error: Pipeline finished with failure!'
                }
            }            
        }
        cleanup{
            node('linux') {
                script{
                    echo 'Cleaning up workspace...'
                    deleteDir()   
                }
            }
        }
      
    } // end post

} // end pipeline
