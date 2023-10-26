package ml.kalanblow.gestiondesinscriptions.controller.web;

import ml.kalanblow.gestiondesinscriptions.enums.EditMode;
import ml.kalanblow.gestiondesinscriptions.service.EleveService;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class KalanblowEleveControllerTest {


       @Mock
    private EleveService eleveService;


       @BeforeEach
       void  setUp(){

           MockitoAnnotations.openMocks(this);
       }
        @Test
        public void test_listDesEleves() {
            // Arrange
            int pageNumber =2;
            int pageSize= 1;
            Pageable pageable=  PageRequest.of(pageNumber, pageSize, Sort.by("prenom"));
            KalanblowEleveController controller = new KalanblowEleveController(eleveService);

            // Act
            Model model = new ExtendedModelMap(); ;
            ModelAndView result = controller.listDesEleves(model, pageable);

            // Assert
            assertEquals("eleves/listeDesEleves", result.getViewName());
            assertNotNull(result.getModel().get("eleves"));
            assertNotNull(result.getModel().get("isFirstPage"));
        }

        // Should be able to add a new eleve with valid data
        @Test
        public void test_ajouterNouvelEleve() {
            // Arrange
            ModelAndView modelAndView = new ModelAndView();
            KalanblowEleveController controller = new KalanblowEleveController(eleveService);

            // Act
            ModelAndView result = controller.ajouterNouvelEleve(modelAndView);

            // Assert
            assertEquals("eleves/editerEleve", result.getViewName());
            assertNotNull(result.getModel().get("eleve"));
            assertNotNull(result.getModel().get("genders"));
            assertNotNull(result.getModel().get("rolesPossibles"));
            assertNotNull(result.getModel().get("possiblesMaritalStatus"));
            assertEquals(EditMode.CREATE, result.getModel().get("editMode"));
        }

        // Should be able to edit an existing eleve with valid data
        @Test
        public void test_editEleveForm() {
            // Arrange
            long id = 1;
            ModelAndView modelAndView = new ModelAndView();
            KalanblowEleveController controller = new KalanblowEleveController(eleveService);

            // Act
            ModelAndView result = controller.editEleveForm(id, modelAndView);

            // Assert
            assertEquals("eleves/editerEleve", result.getViewName());
            assertNotNull(result.getModel().get("eleve"));
            assertNotNull(result.getModel().get("genders"));
            assertNotNull(result.getModel().get("rolesPossibles"));
            assertNotNull(result.getModel().get("possiblesMaritalStatus"));
            assertEquals(EditMode.UPDATE, result.getModel().get("editMode"));
        }

        // Should handle the case when there are no eleves to display
        @Test
        public void test_listDesEleves_NoEleves() {
            // Arrange

            int pageNumber =2;
            int pageSize= 1;
            Pageable pageable=  PageRequest.of(pageNumber, pageSize, Sort.by("prenom"));

            KalanblowEleveController controller = new KalanblowEleveController(eleveService);

            // Act
            Model model = new ExtendedModelMap();
            ModelAndView result = controller.listDesEleves( model, pageable);

            // Assert
            assertEquals("eleves/listeDesEleves", result.getViewName());
            assertNotNull(result.getModel().get("eleves"));
            assertNotNull(result.getModel().get("isFirstPage"));
        }

        // Should handle the case when trying to edit a non-existing eleve
        @Test
        public void test_editEleveForm_NonExistingEleve() {
            // Arrange
            long id = 999;
            ModelAndView modelAndView = new ModelAndView();

            KalanblowEleveController controller = new KalanblowEleveController(eleveService);

            // Act
            ModelAndView result = controller.editEleveForm(id, modelAndView);

            // Assert
            assertEquals("eleves/editerEleve", result.getViewName());
            assertNotNull(result.getModel().get("eleve"));
            assertNotNull(result.getModel().get("genders"));
            assertNotNull(result.getModel().get("rolesPossibles"));
            assertNotNull(result.getModel().get("possiblesMaritalStatus"));
            assertEquals(EditMode.UPDATE, result.getModel().get("editMode"));
        }

        // Should handle the case when trying to delete a non-existing eleve
        @Test
        public void test_aSupprimerEleve_NonExistingEleve() {
            // Arrange
            long id = 999;

        }

}
