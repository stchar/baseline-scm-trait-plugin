package io.jenkins.plugins.scm.baseline;

import hudson.Extension;
import jenkins.plugins.git.traits.GitSCMExtensionTrait;
import jenkins.plugins.git.traits.GitSCMExtensionTraitDescriptor;
import jenkins.scm.api.trait.SCMSourceTrait;
import org.jenkinsci.Symbol;
import org.kohsuke.stapler.DataBoundConstructor;

import edu.umd.cs.findbugs.annotations.CheckForNull;

import jenkins.plugins.git.GitSCMBuilder;
import jenkins.plugins.git.GitSCMSourceContext;
import jenkins.scm.api.trait.SCMSourceContext;
import jenkins.scm.api.trait.SCMBuilder;

/**
 * Exposes {@link GitBaselineExtension} as a {@link SCMSourceTrait}.
 */
public class GitBaselineTrait extends GitSCMExtensionTrait<GitBaselineExtension>{


    @DataBoundConstructor
    public GitBaselineTrait(@CheckForNull GitBaselineExtension extension) {
        super(extension == null ? new GitBaselineExtension() : extension);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void decorateContext(SCMSourceContext<?, ?> context) {
        GitSCMSourceContext ctx = ((GitSCMSourceContext) context);
        if(ctx.wantBranches()) {
            ctx.withRefSpec("+refs/heads/*:refs/remotes/origin/*");
        }
        if(!ctx.wantTags()) {
            ctx.withRefSpec("+refs/tags/*:refs/tags/*");
        }
        super.decorateContext(context);

    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void decorateBuilder(SCMBuilder<?, ?> builder) {
        super.decorateBuilder(builder);
    }


    @Extension
    @Symbol("addBaselineEnv")
    public static class DescriptorImpl extends GitSCMExtensionTraitDescriptor {
        /**
         * {@inheritDoc}
         */
        @Override
        public String getDisplayName() {
            return "Add git baseline environment variable";
        }
    }
}

