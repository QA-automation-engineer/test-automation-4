node('master') {

	def mvn = '/var/jenkins_home/tools/hudson.tasks.Maven_MavenInstallation/maven_3.5.4/bin/mvn'
	def seleniumUrl = '172.17.0.3'

	stage('checkout') {
		checkout changelog: false, poll: false, scm: [$class: 'GitSCM', branches: [[name: '*/master']], doGenerateSubmoduleConfigurations: false, extensions: [], submoduleCfg: [], userRemoteConfigs: [[credentialsId: '3a3783bf-b1b5-4f0a-bc71-174ab141f449', url: 'git@github.com:QA-automation-engineer/test-automation-4.git']]]
	}

	stage('Cross-browser testing') {
		parallel "Chrome": {
			def buildDir = 'target-chrome'
			sh "${mvn} clean test -Dtest=lesson13.LoginTest -Dbrowser=Chrome -Dselenium.url=${seleniumUrl} -DbuildDirectory=${buildDir} -Dmaven.test.failure.ignore=true"
			junit "${buildDir}/surefire-reports/*.xml"
		}, "Firefox": {
			def buildDir = 'target-firefox'
			sh "${mvn} clean test -Dtest=lesson13.LoginTest -Dbrowser=Firefox -Dselenium.url=${seleniumUrl} -DbuildDirectory=${buildDir} -Dmaven.test.failure.ignore=true"
			junit "${buildDir}/surefire-reports/*.xml"
		}
	}
}