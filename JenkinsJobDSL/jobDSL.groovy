job('ejemplo-DSL-desde-git'){
		scm {
        git('https://github.com/macloujulian/jenkins.job.parametrizado.git', 'main') { node -> // is hudson.plugins.git.GitSCM
            node / gitConfigName('egmartin83')
            node / gitConfigEmail('egmartin83@gmail.com')
        }
    }
		parameters{
			stringParam('nombre', defaultValue = 'Emiliano del Valle',description = 'Parametro de nombre')
			choiceParam('planeta',['Tierra','Venus','Mercurio','Saturno','Jupiter'])
			booleanParam('agente',false)
		}
		triggers{
			cron('H/7 * * * *')
			githubPush()
		}
		steps {
	    shell("bash jobscript.sh")
	  }
		publishers {
	    mailer('macloujulian@gmail.com', true, true)
	    slackNotifier {
	      notifyAborted(true)
	      notifyEveryFailure(true)
	      notifyNotBuilt(false)
	      notifyUnstable(false)
	      notifyBackToNormal(true)
	      notifySuccess(false)
	      notifyRepeatedFailure(false)
	      startNotification(false)
	      includeTestSummary(false)
	      includeCustomMessage(false)
	      customMessage(null)
	      sendAs(null)
	      commitInfoChoice('NONE')
	      teamDomain(null)
	      authToken(null)
	    }
	  }
}
