package info.lotharschulz.github.org.verifier.api.github

import org.kohsuke.github.GHOrganization

sealed class Organization {
    data class Success(val githubOrg: GHOrganization) : Organization()
    data class Failure(val error: String) : Organization()
}
