pipeline {
    parameters {
        string(defaultValue: 'Spaces-1', description: '', name: 'SpaceId', trim: true)
        string(defaultValue: 'RandomQuotes', description: '', name: 'ProjectName', trim: true)
        string(defaultValue: 'Dev', description: '', name: 'EnvironmentName', trim: true)
        string(defaultValue: 'Master', description: '', name: 'ServerId', trim: true)
    }
    tools {
        maven 'Maven 3'
        jdk 'Java'
    }
    environment {
        JAVA_HOME = "${tool('Java')}"
    }
    agent any
    stages {
        stage('Checkout git repo') {
            steps {
                git branch: 'master', url: 'https://github.com/OctopusSamples/RandomQuotes-Java.git'
            }
        }
        stage ('Initialize') {
            steps {
                sh '''
                    echo "PATH = ${PATH}"
                    echo "M2_HOME = ${M2_HOME}"
                '''
            }
        }
        stage('build') {
            steps {
                sh(script: "mvn versions:set -DnewVersion=1.0.${BUILD_NUMBER}", returnStdout: true)
                sh(script: "mvn package", returnStdout: true)
            }
        }
        stage('deploy') {
            steps {
                //octopusPack additionalArgs: '', includePaths: "${env.WORKSPACE}/target/randomquotes.1.0.${BUILD_NUMBER}.jar", outputPath: "${env.WORKSPACE}", overwriteExisting: false, packageFormat: 'zip', packageId: 'randomquotes', packageVersion: "1.0.${BUILD_NUMBER}", sourcePath: '', toolId: 'Default', verboseLogging: false
                octopusPushPackage additionalArgs: '', overwriteMode: 'FailIfExists', packagePaths: "${env.WORKSPACE}/target/randomquotes.1.0.${BUILD_NUMBER}.jar", serverId: "${ServerId}", spaceId: "${SpaceId}", toolId: 'Default', verboseLogging: false
                //octopusPushBuildInformation additionalArgs: '', commentParser: 'GitHub', overwriteMode: 'FailIfExists', packageId: 'randomquotes', packageVersion: "1.0.${BUILD_NUMBER}", serverId: "${ServerId}", spaceId: "${SpaceId}", toolId: 'Default', verboseLogging: false
                //octopusCreateRelease additionalArgs: '', cancelOnTimeout: false, channel: '', defaultPackageVersion: '', deployThisRelease: true, deploymentTimeout: '', environment: "${EnvironmentName}", jenkinsUrlLinkback: false, project: "${ProjectName}", releaseNotes: false, releaseNotesFile: '', releaseVersion: "1.0.${BUILD_NUMBER}", serverId: "${ServerId}", spaceId: "${SpaceId}", tenant: '', tenantTag: '', toolId: 'Default', verboseLogging: false, waitForDeployment: false
                //octopusDeployRelease additionalArgs: '', cancelOnTimeout: false, deploymentTimeout: '', environment: "${EnvironmentName}", project: "${ProjectName}", releaseVersion: "1.0.${BUILD_NUMBER}", serverId: "${ServerId}", spaceId: "${SpaceId}", tenant: '', tenantTag: '', toolId: 'Default', variables: '', verboseLogging: false, waitForDeployment: false
            }
        }
    }
}