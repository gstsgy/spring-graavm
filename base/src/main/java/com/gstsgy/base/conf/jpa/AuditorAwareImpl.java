package com.gstsgy.base.conf.jpa;

import com.gstsgy.base.utils.WebUtils;
import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component("auditorAware")
public class AuditorAwareImpl implements AuditorAware<Long> {
    @Override
    public Optional<Long> getCurrentAuditor() {
        return Optional.of(WebUtils.getUserId());
    }
}
