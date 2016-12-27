package hu.bartl.communication;

import hu.bartl.model.bggeek.BoardGameDescription;
import hu.bartl.model.bggeek.BoardGameDescriptionContainer;
import hu.bartl.service.IdService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class BGGeekAccessorTest {

    @Mock
    private IdService idService;

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    @Autowired
    private BGGeekAccessor bgGeekAccessor;

    @Test
    public void getBoardGameDescriptions() throws Exception {

        when(idService.getNextIdsString(1,5)).thenReturn("1,2,3,4,6");
        when(restTemplate.getForObject(anyString(), any(), anyString())).thenReturn(getDescriptions(1,2,3,4,5,6));

        List<BoardGameDescription> boardGameDescriptions = bgGeekAccessor.getBoardGameDescriptions(1, 5);
        assertContainsDescription(boardGameDescriptions, 1);
        assertContainsDescription(boardGameDescriptions, 2);
        assertContainsDescription(boardGameDescriptions, 3);
        assertContainsDescription(boardGameDescriptions, 4);
        assertContainsDescription(boardGameDescriptions, 6);
    }

    private BoardGameDescriptionContainer getDescriptions(int... bggId) {
        BoardGameDescriptionContainer result = new BoardGameDescriptionContainer();
        ArrayList<BoardGameDescription> boardGameDescriptions = new ArrayList<>();

        for (int i = 0; i < bggId.length; i++) {
            BoardGameDescription description = new BoardGameDescription();
            description.setId(bggId[i]);
            boardGameDescriptions.add(description);
        }

        result.setBoardGameDescriptions(boardGameDescriptions);
        return result;
    }

    private void assertContainsDescription(List<BoardGameDescription> boardGameDescriptions, int id) {
        assertTrue(boardGameDescriptions.stream().filter(d -> d.getId() == id).findFirst().isPresent());
    }
}
