package com.trilogyed.rsvp.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.trilogyed.rsvp.dao.RsvpDaoJdbcTemplateImpl;
import com.trilogyed.rsvp.model.Rsvp;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest
public class RsvpControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RsvpDaoJdbcTemplateImpl rsvpDaoJdbcTemplate;

    private ObjectMapper om = new ObjectMapper();

    @Test
    public void testPostingRsvp() throws Exception {
        Rsvp expectedRsvp = new Rsvp("Arturo - Controller Test", 4);
        expectedRsvp.setId(1);

        String expectedRsvpJson = om.writeValueAsString(expectedRsvp);

        Rsvp postedRsvp = new Rsvp("Arturo - Controller Test", 4);
        String postedRsvpJson = om.writeValueAsString(postedRsvp);

        Mockito.when(rsvpDaoJdbcTemplate.addRsvp(postedRsvp)).thenReturn(expectedRsvp);

        this.mockMvc.perform(MockMvcRequestBuilders.post("/rsvps")
                .content(postedRsvpJson)
                .contentType(MediaType.APPLICATION_JSON)).andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().json(expectedRsvpJson));

    }
}
