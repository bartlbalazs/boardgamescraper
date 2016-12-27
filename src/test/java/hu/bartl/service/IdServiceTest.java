package hu.bartl.service;

import hu.bartl.repository.BoardGameFlatDescription;
import hu.bartl.repository.DescriptionRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class IdServiceTest {

    public static final int MAX_TESTED_ID = 10;
    @Mock
    private DescriptionRepository descriptionRepository;

    @Mock
    private BoardGameFlatDescription description;

    @InjectMocks
    @Autowired
    private IdService idService;

    @Test
    public void getNextIdsString() throws Exception {
        mockDescriptionRepository(MAX_TESTED_ID, 2,3);
        String nextIdsString = idService.getNextIdsString(1, 5);
        assertEquals("1,4,5,6,7", nextIdsString);
    }

    private void mockDescriptionRepository(int maxIds, Integer... idsPresent) {
        List<Integer> pretentIds = Arrays.asList(idsPresent);
        for (int i = 0; i <= maxIds; i++) {
            if (pretentIds.contains(i)) {
                when(descriptionRepository.findByBggId(i)).thenReturn(Optional.of(description));
            } else {
                when(descriptionRepository.findByBggId(i)).thenReturn(Optional.empty());
            }
        }
    }
}