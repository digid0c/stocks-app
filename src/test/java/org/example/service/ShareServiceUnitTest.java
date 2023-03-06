package org.example.service;

import org.example.persistence.entity.Share;
import org.example.persistence.repository.ShareRepository;
import org.example.service.mapper.ShareMapper;
import org.example.web.api.v1.model.ShareRegistrationModel;
import org.example.web.api.v1.model.ShareRegistrationModelList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ShareServiceUnitTest {

    private static final int AMOUNT_OF_SHARES = 3;

    @Mock
    private ShareRepository shareRepository;

    @Mock
    private ShareMapper shareMapper;

    @Captor
    private ArgumentCaptor<List<Share>> shareCaptor;

    @InjectMocks
    private ShareService tested;

    private ShareRegistrationModelList shareRegistrationModelList;

    @BeforeEach
    public void setUp() {
        shareRegistrationModelList = new ShareRegistrationModelList();
        shareRegistrationModelList.setShares(List.of(new ShareRegistrationModel(), new ShareRegistrationModel(),
                new ShareRegistrationModel()));
    }

    @Test
    public void shouldSuccessfullyRegisterNewShares() {
        // given
        when(shareMapper.toEntity(any(ShareRegistrationModel.class))).thenReturn(new Share());

        // when
        tested.register(shareRegistrationModelList);

        // then
        verify(shareMapper, times(AMOUNT_OF_SHARES)).toEntity(any(ShareRegistrationModel.class));
        verify(shareRepository).saveAll(shareCaptor.capture());

        List<Share> stocks = shareCaptor.getValue();
        assertThat(stocks).isNotNull();
        assertThat(stocks).hasSize(AMOUNT_OF_SHARES);
    }
}
