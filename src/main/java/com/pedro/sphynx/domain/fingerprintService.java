package com.pedro.sphynx.domain;

import java.util.List;

import com.machinezoo.sourceafis.FingerprintMatcher;
import com.machinezoo.sourceafis.FingerprintTemplate;

import com.pedro.sphynx.infrastructure.entities.Consumer;
import com.pedro.sphynx.infrastructure.repository.ConsumerRepository;

public class fingerprintService {

    private ConsumerRepository consumerRepository;

    public byte[] matchFingerprint(byte[] template) {
        List<Consumer> allConsumers = consumerRepository.findAll();

        List<byte[]> storedFingerprints = allConsumers.stream().map(Consumer::getFingerprint).toList();

        FingerprintTemplate probe = new FingerprintTemplate(template);
        FingerprintMatcher matcher = new FingerprintMatcher(probe);

        for (byte[] storedFingerprint : storedFingerprints) {
            FingerprintTemplate candidate = new FingerprintTemplate(storedFingerprint);
            double score = matcher.match(candidate);
            if (score > 100) {
                return storedFingerprint;
            }
        }

        return null;
    }

}
