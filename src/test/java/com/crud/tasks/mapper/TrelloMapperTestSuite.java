package com.crud.tasks.mapper;

import com.crud.tasks.domain.*;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class TrelloMapperTestSuite {

    @Test
    public void mapToBoards() {

        //Given
        TrelloMapper trelloMapper = new TrelloMapper();

        List<TrelloListDto> trelloListDtoList = new ArrayList<>();
        List<TrelloBoardDto> trelloBoardDtoList = new ArrayList<>();
        trelloBoardDtoList.add(new TrelloBoardDto("1", "Tablica", trelloListDtoList));

        List<TrelloList> trelloListList = new ArrayList<>();
        List<TrelloBoard> trelloBoardList = new ArrayList<>();
        trelloBoardList.add(new TrelloBoard("1", "Tablica", trelloListList));

        //When
        List<TrelloBoard> testBoard = trelloMapper.mapToBoards(trelloBoardDtoList);

        //Then
        Assert.assertEquals(1, testBoard.size());
        Assert.assertEquals("1", testBoard.get(0).getId());
        Assert.assertEquals("Tablica", testBoard.get(0).getName());
        Assert.assertEquals(trelloBoardList.get(0).getLists(),testBoard.get(0).getLists());
    }

    @Test
    public void mapToBoardsDto() {

        //Given
        TrelloMapper trelloMapper = new TrelloMapper();

        List<TrelloListDto> trelloListDtoList = new ArrayList<>();
        List<TrelloBoardDto> trelloBoardDtoList = new ArrayList<>();
        trelloBoardDtoList.add(new TrelloBoardDto("1", "Tablica", trelloListDtoList));

        List<TrelloList> trelloListList = new ArrayList<>();
        List<TrelloBoard> trelloBoardList = new ArrayList<>();
        trelloBoardList.add(new TrelloBoard("1", "Tablica", trelloListList));

        //When
        List<TrelloBoardDto> testBoardDto = trelloMapper.mapToBoardsDto(trelloBoardList);

        //Then
        Assert.assertEquals(1, testBoardDto.size());
        Assert.assertEquals("1", testBoardDto.get(0).getId());
        Assert.assertEquals("Tablica", testBoardDto.get(0).getName());
        Assert.assertEquals(trelloBoardDtoList.get(0).getLists(),testBoardDto.get(0).getLists());
    }

    @Test
    public void mapToList() {

        //Given
        TrelloMapper trelloMapper = new TrelloMapper();

        List<TrelloListDto> trelloListDtoList = new ArrayList<>();
        trelloListDtoList.add(new TrelloListDto("1", "Lista", false));

        //When
        List<TrelloList> testList = trelloMapper.mapToList(trelloListDtoList);

        //Then
        Assert.assertEquals(1, testList.size());
        Assert.assertEquals("1", testList.get(0).getId());
        Assert.assertEquals("Lista", testList.get(0).getName());
        Assert.assertEquals(false,testList.get(0).isClosed());
    }

    @Test
    public void mapToListDto() {

        //Given
        TrelloMapper trelloMapper = new TrelloMapper();

        List<TrelloList> trelloListList = new ArrayList<>();
        trelloListList.add(new TrelloList("1", "Lista", false));

        //When
        List<TrelloListDto> testListDto = trelloMapper.mapToListDto(trelloListList);

        //Then
        Assert.assertEquals(1, testListDto.size());
        Assert.assertEquals("1", testListDto.get(0).getId());
        Assert.assertEquals("Lista", testListDto.get(0).getName());
        Assert.assertEquals(false,testListDto.get(0).isClosed());
    }

    @Test
    public void mapToCardDto() {

        //Given
        TrelloMapper trelloMapper = new TrelloMapper();

        TrelloCard trelloCard = new TrelloCard("Zakupy", "Owoce i warzywa", "pos", "cos");

        //When
        TrelloCardDto testCardDto = trelloMapper.mapToCardDto(trelloCard);

        //Then
        Assert.assertEquals("Zakupy", testCardDto.getName());
        Assert.assertEquals("Owoce i warzywa", testCardDto.getDescription());
        Assert.assertEquals("pos", testCardDto.getPos());
        Assert.assertEquals("cos", testCardDto.getListId());
    }

    @Test
    public void mapToCard() {
        //Given
        TrelloMapper trelloMapper = new TrelloMapper();

        TrelloCardDto trelloCardDto = new TrelloCardDto("Zakupy", "Owoce i warzywa", "pos", "cos");

        //When
        TrelloCard testCard = trelloMapper.mapToCard(trelloCardDto);

        //Then
        Assert.assertEquals("Zakupy", testCard.getName());
        Assert.assertEquals("Owoce i warzywa", testCard.getDescription());
        Assert.assertEquals("pos", testCard.getPos());
        Assert.assertEquals("cos", testCard.getListId());
    }
}