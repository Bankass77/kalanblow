$(document).ready(function() {
    // Obtenir les données du formulaire
    const formElement = document.querySelector('#user-form');
    const formData = new FormData(formElement);

    // Utiliser la fonction genererPdf définie dans le fichier externe
    genererPdf(formData);
});

