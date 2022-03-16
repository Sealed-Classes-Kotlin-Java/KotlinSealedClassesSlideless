package info.lotharschulz.github.org.verifier.api.github.organization;

import org.kohsuke.github.GHOrganization;

public record GitHubOrganizationSuccess (GHOrganization ghOrganization) implements GitHubOrganization{
}
