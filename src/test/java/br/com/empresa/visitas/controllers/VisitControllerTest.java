package br.com.empresa.visitas.controllers;

import br.com.empresa.visitas.contracts.VisitService;
import br.com.empresa.visitas.dtos.VisitCreateRequest;
import br.com.empresa.visitas.dtos.VisitResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import java.time.LocalDateTime;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(VisitController.class)
@Import(br.com.empresa.visitas.configurations.SecurityConfig.class)
class VisitControllerTest {
    @Autowired MockMvc mvc;
    @Autowired ObjectMapper objectMapper;
    @MockBean VisitService service;
    @Test @WithMockUser(username="supervisor") void postValidoRetorna201() throws Exception {
        VisitCreateRequest request = new VisitCreateRequest("client-1", "Loja", null, null, null, null, 0, "{}", 0, "{}", null);
        VisitResponse response = new VisitResponse(1L, "client-1", "Loja", null, null, null, null, 0, "{}", 0, "{}", null, "supervisor", LocalDateTime.now());
        when(service.criar(any(VisitCreateRequest.class), eq("supervisor"))).thenReturn(response);
        mvc.perform(post("/api/visitas").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isCreated()).andExpect(jsonPath("$.id").value(1)).andExpect(jsonPath("$.criadoPor").value("supervisor"));
    }
    @Test @WithMockUser void postSemNomeRetorna400() throws Exception {
        String json = "{\"clientId\":\"client-2\",\"nomeFantasia\":\" \"}";
        mvc.perform(post("/api/visitas").contentType(MediaType.APPLICATION_JSON).content(json))
            .andExpect(status().isBadRequest()).andExpect(jsonPath("$.fields.nomeFantasia").exists());
        verifyNoInteractions(service);
    }
}
