package info.lotharschulz.github.org.verifier.api.github

import org.kohsuke.github.GHOrganization

sealed interface Org
sealed class Organization {
    data class Success(val githubOrg: GHOrganization) : Organization(), Org
    data class Failure(val error: String) : Organization(), Org
}
