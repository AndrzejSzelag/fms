package pl.szelag.security;

import pl.szelag.model.Account;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.NoResultException;
import javax.security.enterprise.credential.Credential;
import javax.security.enterprise.credential.UsernamePasswordCredential;
import javax.security.enterprise.identitystore.CredentialValidationResult;
import javax.security.enterprise.identitystore.IdentityStore;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@ApplicationScoped
public class JpaIdentityStore implements IdentityStore {

    @Inject
    private SecurityEndpoint security;

    @Override
    public Set<String> getCallerGroups(CredentialValidationResult validationResult) {
        return IdentityStore.super.getCallerGroups(validationResult);
    }

    @Override
    public CredentialValidationResult validate(final Credential credential) {
        if (credential instanceof UsernamePasswordCredential) {

            var usernamePasswordCredential = (UsernamePasswordCredential) credential;

            try {
                Account account = security.findAccountWithAuthConditions(usernamePasswordCredential.getCaller(), usernamePasswordCredential.getPasswordAsString());
                return null != account ? new CredentialValidationResult(account.getLogin(), new HashSet<>(Collections.singletonList(account.getType()))) : CredentialValidationResult.INVALID_RESULT;
            } catch (NoResultException ex) {
                return CredentialValidationResult.INVALID_RESULT;
            }
        }
        return CredentialValidationResult.NOT_VALIDATED_RESULT;
    }
}
