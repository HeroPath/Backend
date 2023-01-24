package com.gianca1994.aowebbackend.resources.npc;

import com.gianca1994.aowebbackend.exception.Conflict;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class NpcServiceTest {

    @Autowired
    private NpcRepository npcRepository;

    @Autowired
    private NpcService npcService;

    @BeforeEach
    void setUp() {
        npcRepository.deleteAll();
        npcRepository.save(
                new Npc(
                        "test", (short) 1,
                        1L, 1L,
                        1L, 1L,
                        1, 1,
                        1, 1,
                        1,
                        "test"
                )
        );
    }

    @Test
    void whenGetAllNpcs_thenReturnNpcs() {
        assertEquals(1, npcService.getAllNpcs().size());
    }

    @Test
    void givenNameNpc_whenGetNpc_thenReturnNpc() {
        assertEquals("test", npcService.getNpcByName("test").getName());
    }

    @Test
    void givenNameZone_whenGetNpcByZone_thenReturnListNpcsByZone() {
        assertEquals(1, npcService.filterNpcByZone("test").size());
    }

    @Test
    void givenNpcDTO_whenSaveNpc_thenReturnNpc() throws Conflict {
        NpcDTO npcDTO = new NpcDTO(
                "test2", (short) 1,
                1L, 1L,
                1L, 1L,
                1, 1,
                1, 1,
                1,
                "test"
        );

        npcService.saveNpc(npcDTO);
        assertEquals(2, npcRepository.findAll().size());
    }
}