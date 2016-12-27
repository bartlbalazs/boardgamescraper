package hu.bartl.service;

import hu.bartl.communication.BGGeekAccessor;
import hu.bartl.configuration.ConfigurationProvider;
import hu.bartl.model.bggeek.BoardGameDescription;
import hu.bartl.repository.BoardGameFlatDescription;
import hu.bartl.repository.DescriptionRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class BasicInfoServiceTest {

    private static final int CHUNK_SIZE = 5;
    private static final int TOTAL_ITEMS = CHUNK_SIZE * 2;
    private static final int TIME_OUT = 200;

    @Mock
    private ConfigurationProvider configurationProvider;

    @Mock
    private BGGeekAccessor bgGeekAccessor;

    @Mock
    private Function<BoardGameDescription, BoardGameFlatDescription> descriptionMapper;

    @Mock
    private DescriptionRepository descriptionRepository;

    private AtomicInteger descriptionIdCounter = new AtomicInteger();

    @InjectMocks
    private BasicInfoService basicInfoService;

    @Test
    public void scrapeAll() throws Exception {
        when(configurationProvider.getChunkSize()).thenReturn(CHUNK_SIZE);
        when(configurationProvider.getItemCount()).thenReturn(TOTAL_ITEMS);
        when(configurationProvider.getTimeout()).thenReturn(TIME_OUT);

        when(bgGeekAccessor.getBoardGameDescriptions(Mockito.anyInt(), Mockito.anyInt())).thenReturn(mockDescriptions());
        when(descriptionMapper.apply(any())).thenReturn(mock(BoardGameFlatDescription.class));

        basicInfoService.scrapeAll();

        verify(descriptionRepository, atLeast(TOTAL_ITEMS)).insert(any(BoardGameFlatDescription.class));
    }

    private ArrayList<BoardGameDescription> mockDescriptions() {

        ArrayList<BoardGameDescription> boardGameDescriptions = new ArrayList<>();
        for (int i = 0; i < CHUNK_SIZE; i++) {
            boardGameDescriptions.add(mock(BoardGameDescription.class));
        }
        return boardGameDescriptions;
    }
}
