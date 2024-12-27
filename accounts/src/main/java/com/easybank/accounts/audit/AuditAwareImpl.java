package com.easybank.accounts.audit;

import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

import java.util.Optional;


@Component("auditAwareImpl") // reference name
public class AuditAwareImpl implements AuditorAware<String> {

    @Override
    public Optional getCurrentAuditor() {

        return Optional.of("ACCOUNT_MICROSERVICE");
    }
}
