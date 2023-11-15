package ml.kalanblow.gestiondesinscriptions.controller.web;

import ml.kalanblow.gestiondesinscriptions.service.EleveService;
import ml.kalanblow.gestiondesinscriptions.service.impl.StubUserDetailsService;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(KalanblowEleveController.class)
@Import(TestConfig.class)
//@Disabled
class KalanblowUserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EleveService eleveService;

    public static final String SERVER = "localhost";
    public static final int PORT = 8000;
    public static final String HOST = String.format("http://%s:%d/", SERVER, PORT);

    @Test
    @DisplayName("test Redirect To Login When Not Authenticated")
    @Disabled
    void testGetElevesRedirectToLoginWhenNotAuthenticated() throws Exception {
        mockMvc.perform(get(HOST+"/kalanden/eleves/listeDesEleves"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl(HOST+"/login"));

        Mockito.verifyNoMoreInteractions(eleveService);
    }

    @Test
    @WithUserDetails(StubUserDetailsService.USERNAME_USER)
    void testGetUsersAsUser() throws Exception {
        when(eleveService.obtenirListeElevePage(any(Pageable.class)))
                .thenReturn(Page.empty());

        mockMvc.perform(get(HOST + "/kalanden"))
                .andDo(print())
                .andExpect(status().isOk());
    }

}
