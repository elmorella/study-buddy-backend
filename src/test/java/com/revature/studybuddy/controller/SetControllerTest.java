package com.revature.studybuddy.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.studybuddy.entity.Set;
import com.revature.studybuddy.service.SetService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

@WebMvcTest(SetController.class)
class SetControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper mapper;
    @MockBean
    private SetService setService;
    @Test
    public void testGetAllSet() throws Exception {
        List<Set> setList = new ArrayList<>();
        setList.add(new Set(1L,"First","First description"));
        setList.add(new Set(2L, "Second", "Second description"));
        Mockito.when(setService.getAllSets()).thenReturn(setList);

        String url ="/set/all";


        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(url))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                .andReturn();
        String actualJsonResponse = mvcResult.getResponse().getContentAsString();
        String expectedJsonResponse = mapper.writeValueAsString(setList);

        assertEquals(actualJsonResponse,expectedJsonResponse);

    }

    @Test
    void getSetById() throws Exception {
        Set set = new Set(1L,"First","First description");
        Mockito.when(setService.getSetById(set.getSetId())).thenReturn(set);

        String url ="/set/" + set.getSetId();

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(url))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                .andReturn();
        String actualJsonResponse = mvcResult.getResponse().getContentAsString();
        String expectedJsonResponse = mapper.writeValueAsString(set);

        assertEquals(actualJsonResponse,expectedJsonResponse);

    }

    @Test
    void addSet() throws Exception {
        Set newSet = new Set();
        newSet.setTitle("New");
        newSet.setDescription("If has id, was saved");
        Set savedSet =  new Set(1L,"New","If has id, was saved");
        Mockito.when(setService.addSet(newSet)).thenReturn(savedSet);

        String url ="/set/add";

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post(url)
                .contentType("application/json")
                .content(mapper.writeValueAsString(newSet)))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                .andReturn();
        String actualJsonResponse = mvcResult.getResponse().getContentAsString();
        String expectedJsonResponse = mapper.writeValueAsString(savedSet);

        assertEquals(actualJsonResponse,expectedJsonResponse);
    }
}