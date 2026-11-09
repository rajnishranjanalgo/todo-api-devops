package com.example.todo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
@SpringBootTest @AutoConfigureMockMvc
class TodoApplicationTests {
    @Autowired MockMvc mvc;
    @Test void health() throws Exception { mvc.perform(get("/health")).andExpect(status().isOk()).andExpect(jsonPath("$.status").value("ok")); }
    @Test void createAndList() throws Exception {
        mvc.perform(post("/todos").contentType(MediaType.APPLICATION_JSON).content("{\"title\":\"DevOps task\"}")).andExpect(status().isCreated()).andExpect(jsonPath("$.title").value("DevOps task"));
        mvc.perform(get("/todos")).andExpect(status().isOk()).andExpect(jsonPath("$[0].title").value("DevOps task"));
    }
    @Test void rejectsEmptyTitle() throws Exception { mvc.perform(post("/todos").contentType(MediaType.APPLICATION_JSON).content("{}")).andExpect(status().isBadRequest()); }
}
