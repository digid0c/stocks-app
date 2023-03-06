package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.persistence.repository.ShareRepository;
import org.example.service.mapper.ShareMapper;
import org.example.web.api.v1.model.ShareRegistrationModelList;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static java.util.stream.Collectors.toList;

@Service
@Transactional
@RequiredArgsConstructor
public class ShareService {

    private final ShareRepository shareRepository;
    private final ShareMapper shareMapper;

    public void register(ShareRegistrationModelList modelList) {
        shareRepository.saveAll(modelList.getShares()
                .stream()
                .map(shareMapper::toEntity)
                .collect(toList()));
    }
}
