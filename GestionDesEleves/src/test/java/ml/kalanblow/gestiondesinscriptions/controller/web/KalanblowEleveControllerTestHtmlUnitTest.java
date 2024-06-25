/*
package ml.kalanblow.gestiondesinscriptions.controller.web;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.*;
import ml.kalanblow.gestiondesinscriptions.model.UserName;
import ml.kalanblow.gestiondesinscriptions.service.EleveService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.test.context.support.WithMockUser;

import java.io.IOException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@WebMvcTest(KalanblowEleveController.class)
class KalanblowEleveControllerTestHtmlUnitTest {
    @Autowired
    private WebClient webClient;
    @MockBean
    private EleveService eleveService;

    public static final String USERNAME_USER = "alanna.sparrow@hey.com";
    public static final String USERNAME_ADMIN = "testadmin@example.com";

    @BeforeEach
    void setup()  {
        webClient.getOptions().setCssEnabled(false);
        webClient.getOptions().setJavaScriptEnabled(false);
    }

    @Test
    @WithMockUser(USERNAME_ADMIN)
    void devrait_recuperer_un_admin_lorsque_admin() throws IOException {

        when(eleveService.obtenirListeElevePage(any(Pageable.class)))
                .thenReturn(new PageImpl<>(List.of(Eleves.ajouterUnEleve(new UserName("Abdoulaye", "Traoré")),
                        Eleves.ajouterUnEleve(new UserName("Kazdi", "DiallO")),
                        Eleves.ajouterUnEleve(new UserName("Fousseni", "Sissoko")),
                        Eleves.ajouterUnEleve(new UserName("Lamissa", "Bengaly")))));

        HtmlPage page = webClient.getPage("http://localhost:8080/kalanden");
        DomNodeList<DomElement> h1Headers = page.getElementsByTagName("h1");
        assertThat(h1Headers).hasSize(1).element(0).extracting(DomElement::asNormalizedText).isEqualTo("Eleves");
        HtmlTable elevetHtmlTable = page.getHtmlElementById("users-table");
        List<HtmlTableRow> rows = elevetHtmlTable.getRows();
        HtmlTableRow row = rows.get(0);
        assertThat(row.getCell(0).getTextContent()).isEqualTo("Etablissement DE L'Elève");
        assertThat(row.getCell(1).getTextContent()).isEqualTo("NOM DE FAMILLE");
        assertThat(row.getCell(2).getTextContent()).isEqualTo("GENRE");
        assertThat(row.getCell(3).getTextContent()).isEqualTo("STATUS MARITAL");
        assertThat(row.getCell(4).getTextContent()).isEqualTo("DATE DE NAISSANCE");
        assertThat(row.getCell(5).getTextContent()).isEqualTo("ADRESSE E-MAIL");
        assertThat(row.getCell(6).getTextContent()).isEqualTo("NUMERO DE TELEPHONE");
        assertThat(row.getCell(7).getTextContent()).isEqualTo("ADRESSE PRINCIPALE");

        HtmlTableRow row1 = rows.get(1); //<.>
        assertThat(row1.getCell(0).asNormalizedText()).isEqualTo("Alexandre Masson");
        assertThat(row1.getCell(1).asNormalizedText()).isEqualTo("Abdoulaye Traoré");
        assertThat(row1.getCell(2).asNormalizedText()).isEqualTo("FEMALE");
        assertThat(row1.getCell(3).asNormalizedText()).isEqualTo("2012-01-26");
        assertThat(row1.getCell(4).asNormalizedText()).isEqualTo("clara.roux@hotmail.fr");
        assertThat(row1.getCell(3).asNormalizedText()).isEqualTo("2012-01-26");
        assertThat(row1.getCell(4).asNormalizedText()).isEqualTo("0022373203431");
        assertThat(row1.getCell(4).asNormalizedText()).isEqualTo("29 rue des Sablons 75016 Paris");

    }
}
*/
