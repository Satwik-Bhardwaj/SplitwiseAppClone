//package com.satwik.splitwiseclone.controller;
//
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.satwik.splitwiseclone.persistence.dto.group.GroupDTO;
//import com.satwik.splitwiseclone.service.interfaces.GroupService;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//
//import java.nio.file.AccessDeniedException;
//
//import static org.mockito.Mockito.when;
//import static org.springframework.mock.http.server.reactive.MockServerHttpRequest.post;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@ExtendWith(MockitoExtension.class)
//public class GroupControllerTest {
//
//    @Mock
//    private GroupService groupService;
//
//    @InjectMocks
//    private GroupController groupController;
//
//    @Mock
//    private Authentication authentication;
//
//    private final ObjectMapper objectMapper = new ObjectMapper();
//
//    @Test
//    public void testCreateGroup() throws Exception {
//        SecurityContextHolder.getContext().setAuthentication(authentication);
//        when(authentication.getName()).thenReturn("1");
//
//        GroupDTO groupDTO = new GroupDTO();
//        groupDTO.setGroupId(1);
//        groupDTO.setGroupName("Group A");
//        groupDTO.setExpenses(null);
//        groupDTO.setOwner(null);
//        when(groupService.createGroup(groupDTO, 1)).thenReturn("Group successfully created");
//
//        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(groupController).build();
//
//        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/group/create")
//                        .contentType("application/json")
//                        .content(objectMapper.writeValueAsString(groupDTO)))
//                .andExpect(status().isOk())
//                .andExpect(content().string("Group successfully created"));
//    }
//
//    @Test
//    public void testDeleteGroup() throws Exception {
//        SecurityContextHolder.getContext().setAuthentication(authentication);
//        when(authentication.getName()).thenReturn("1");
//
//        int groupId = 1;
//        when(groupService.deleteGroupByGroupId(groupId, 1)).thenReturn("Group successfully deleted");
//
//        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(groupController).build();
//
//        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/group/delete")
//                        .param("groupId", String.valueOf(groupId)))
//                .andExpect(status().isOk())
//                .andExpect(content().string("Group successfully deleted"));
//    }
//
//}
