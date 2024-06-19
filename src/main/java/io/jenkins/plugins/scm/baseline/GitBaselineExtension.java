package io.jenkins.plugins.scm.baseline;

import hudson.Extension;
import hudson.model.Run;
import hudson.model.TaskListener;
import hudson.plugins.git.GitException;
import hudson.plugins.git.GitSCM;
import hudson.plugins.git.extensions.GitSCMExtension;
import hudson.plugins.git.extensions.GitSCMExtensionDescriptor;
import java.io.IOException;
import java.util.Map;

import org.jenkinsci.Symbol;
import org.jenkinsci.plugins.gitclient.GitClient;
import org.jenkinsci.plugins.gitclient.CliGitAPIImpl;
import org.kohsuke.stapler.DataBoundConstructor;
import org.kohsuke.stapler.DataBoundSetter;


public class GitBaselineExtension extends GitSCMExtension {
    private String baseline;

    @DataBoundConstructor
    public GitBaselineExtension() {
    }

     /**
     * {@inheritDoc}
     */
    @Override
    public void onCheckoutCompleted(GitSCM scm, Run<?, ?> build, GitClient git, TaskListener listener) throws IOException, InterruptedException, GitException {
        listener.getLogger().println("Adding baseline environment variable");
        // when it runs on a remote agent, and not at jenkins server
        // then the git could be an instanceof org.jenkinsci.plugins.gitclient.RemoteGitImpl
        // this class is not pulblic and despite that both RemoteGitImpl and CliGitAPIImpl
        // are from the same package and inherit same base class then could be casted to each other
        //this.baseline = ((CliGitAPIImpl) git).launchCommand("describe", "--tags","--abbrev=0", "HEAD");
        this.baseline = git.describe("HEAD");
        build.addAction(new GitBaselineAction(this.baseline.trim()));
    }

    public String getBaseline() {
        return this.baseline;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        return o instanceof GitBaselineExtension;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return GitBaselineExtension.class.hashCode();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "addBaselineEnv{}";
    }


    @Extension
    @Symbol("addBaselineEnv")
    public static class DescriptorImpl extends GitSCMExtensionDescriptor {
        /**
         * {@inheritDoc}
         */
        @Override
        public String getDisplayName() {
            return "Add git baseline environment variable";
        }
    }
}
