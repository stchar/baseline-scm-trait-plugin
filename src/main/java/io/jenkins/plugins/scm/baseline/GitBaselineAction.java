package io.jenkins.plugins.scm.baseline;

import hudson.EnvVars;
import hudson.model.EnvironmentContributingAction;
import hudson.model.Run;
import hudson.model.TaskListener;

import edu.umd.cs.findbugs.annotations.NonNull;

public class GitBaselineAction implements EnvironmentContributingAction {
    private final String baseline;

    public GitBaselineAction(String baseline) {
        this.baseline = baseline;
    }

    @Override
    public void buildEnvironment(@NonNull Run<?,?> run, @NonNull EnvVars env) {
        env.put("GIT_BASELINE", this.baseline);
    }

    @Override
    public String getIconFileName() {
        return null;
    }

    @Override
    public String getDisplayName() {
        return "GitBaselineAction";
    }

    @Override
    public String getUrlName() {
        return null;
    }
}