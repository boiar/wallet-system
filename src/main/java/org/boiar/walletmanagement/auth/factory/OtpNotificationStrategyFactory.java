package org.boiar.walletmanagement.auth.factory;

import org.boiar.walletmanagement.auth.enums.OtpTypeEnum;
import org.boiar.walletmanagement.auth.strategy.OtpNotificationStrategy;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class OtpNotificationStrategyFactory {

    private final Map<OtpTypeEnum, OtpNotificationStrategy> strategies;

    public OtpNotificationStrategyFactory(
            List<OtpNotificationStrategy> strategyList
    ) {
        this.strategies = strategyList.stream()
                .collect(Collectors.toMap(
                        OtpNotificationStrategy::getType,
                        Function.identity()
                ));
    }

    public OtpNotificationStrategy getStrategy(OtpTypeEnum type) {
        OtpNotificationStrategy strategy = strategies.get(type);

        if (strategy == null) {
            throw new IllegalStateException(
                    "Unsupported OTP type: " + type
            );
        }

        return strategy;
    }
}
