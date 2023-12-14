package ml.kalanblow.gestiondesinscriptions.controller.web;


import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.properties.TextAlignment;
import lombok.extern.slf4j.Slf4j;
import ml.kalanblow.gestiondesinscriptions.exception.EntityType;
import ml.kalanblow.gestiondesinscriptions.exception.ExceptionType;
import ml.kalanblow.gestiondesinscriptions.exception.KaladewnManagementException;
import ml.kalanblow.gestiondesinscriptions.response.CreateEleveFormData;
import ml.kalanblow.gestiondesinscriptions.util.ImageUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URI;


@Slf4j
@RestController
@RequestMapping("/kalanden/pdf")
public class PDFController {

    @PostMapping("/generate-pdf")
    @ResponseBody
    public byte[] genererPDF(@RequestBody CreateEleveFormData createEleveFormData) {
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            try (PdfWriter writer = new PdfWriter(baos)) {
                try (PdfDocument pdf = new PdfDocument(writer)) {
                    try (Document document = new Document(pdf)) {
                        // Ajout des paragraphes avec les données de l'eleve
                        document.add(new Paragraph("Etablissement: " + createEleveFormData.getEtablissement()));

                        // Ajoutez une image au document
                        if (createEleveFormData.getAvatarFile() != null) {
                            try {
                                // Redimensionnez l'image avant de l'inclure dans le PDF
                                byte[] resizedImage = ImageUtils.resizeImage(
                                        createEleveFormData.getAvatarFile().getBytes(), 35, 45); // Taille pour passeport
                                Image image = new Image(ImageDataFactory.create(resizedImage));

                                // Alignez l'image au centre de la page
                                image.setTextAlignment(TextAlignment.JUSTIFIED);
                                document.add(image);
                            } catch (IOException e) {
                                throw new KaladewnManagementException().throwException(EntityType.ELEVE, ExceptionType.ENTITY_EXCEPTION,"Erreur lors de la lecture de l'image", e.getMessage());
                            }
                        }

                        document.add(new Paragraph("Nom: " + createEleveFormData.getNomDeFamille()));
                        document.add(new Paragraph("Prenom: " + createEleveFormData.getPrenom()));
                        document.add(new Paragraph("Genre: " + createEleveFormData.getGender()));
                        document.add(new Paragraph("Status Marital: " + createEleveFormData.getMaritalStatus()));
                        document.add(new Paragraph("Date de naissance: " + createEleveFormData.getDateDeNaissance()));
                        document.add(new Paragraph("Age: " + createEleveFormData.getAge()));
                        document.add(new Paragraph("INE: " + createEleveFormData.getIneNumber()));
                        document.add(new Paragraph("Adresse: " + createEleveFormData.getAddress()));
                        document.add(new Paragraph("Telephone: " + createEleveFormData.getPhoneNumber()));
                        document.add(new Paragraph("Email: " + createEleveFormData.getEmail()));
                        document.add(new Paragraph("Mere: " + createEleveFormData.getMere()));
                        document.add(new Paragraph("Pere: " + createEleveFormData.getPere()));

                        log.info("Données du formulaire: " + createEleveFormData);
                    }
                }
            }
            byte[] pdfBytes = baos.toByteArray();

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDispositionFormData("attachment", "inscription.pdf");
            headers.setLocation(new URI("http://localhost:8000/kalanden/pdf/generate-pdf"));

            return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK).getBody();
        } catch (Exception e) {
            // gestion des exceptions
            throw new KaladewnManagementException().throwException(EntityType.ELEVE, ExceptionType.ENTITY_EXCEPTION,"Erreur lors de la lecture de l'image", e.getMessage());

        }

    }
}

