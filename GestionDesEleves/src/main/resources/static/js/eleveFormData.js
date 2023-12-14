// script-separe.js

// Vous devez inclure jQuery avant ce script si vous utilisez $() pour jQuery
$(document).ready(function() {
    // Appeler la fonction eleveFormData() définie dans le fichier externe
    const formDataInstance = eleveFormData();

    formDataInstance.init();
});
