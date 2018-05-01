package com.crud.tasks.trello.validator;

import com.crud.tasks.domain.TrelloBoard;
import com.crud.tasks.domain.TrelloList;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;


@RunWith(SpringRunner.class)
@SpringBootTest
public class TrelloValidatorTestSuite {

    @InjectMocks
    private TrelloValidator trelloValidator;

    //private static Logger logger = LoggerFactory.getLogger(TrelloValidator.class);

    @Test
    public void validateTrelloBoards() {

        //Given

        TrelloList trelloList1 = new TrelloList("123", "kotek", false);
        TrelloList trelloList2 = new TrelloList("456", "kot nowy", false);
        List<TrelloList> trelloListList = new ArrayList<>();
        trelloListList.add(trelloList1);
        trelloListList.add(trelloList2);

        TrelloBoard trelloBoard1 = new TrelloBoard("777", "test", trelloListList);
        TrelloBoard trelloBoard2 = new TrelloBoard("888", "kot", trelloListList);
        List<TrelloBoard> trelloBoardList = new ArrayList<>();
        trelloBoardList.add(trelloBoard1);
        trelloBoardList.add(trelloBoard2);

        //When
        List<TrelloBoard> test = trelloValidator.validateTrelloBoards(trelloBoardList);

        //Then
        assertEquals(2, trelloBoardList.size());
        assertEquals(1, test.size());
    }
}