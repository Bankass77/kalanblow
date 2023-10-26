$(document).ready(function() {
    var selecteur = $('duet-date-picker');

    if (selecteur.length > 0) {
        selecteur.each(function() {
            $(this).dateAdapter({
                parse: function(valeur, créerDate) {
                    try {
                        let àPartirDeFormat = luxon.DateTime.fromFormat(valeur, 'yyyy-LL-dd');
                        if (àPartirDeFormat.isValid) {
                            return créerDate(àPartirDeFormat.year, àPartirDeFormat.month, àPartirDeFormat.day);
                        } else {
                            console.log('àPartirDeFormat non valide');
                        }
                    } catch (e) {
                        console.log(e);
                    }
                },
                format: function(date) {
                    var DateTime = luxon.DateTime;
                    return DateTime.fromJSDate(date)
                        .setLocale('[[${#strings.replace(#locale, '_', '-')}]]').toFormat('d LLLL yyyy');
                }
            });
        });
    }
});

