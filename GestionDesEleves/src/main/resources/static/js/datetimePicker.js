$(document).ready(function() {
    var picker = $('duet-date-picker');

    if (picker.length > 0) {
        picker.each(function() {
            $(this).dateAdapter({
                parse: function(value, createDate) {
                    try {
                        var fromFormat = luxon.DateTime.fromFormat(value, 'yyyy-LL-dd');
                        if (fromFormat.isValid) {
                            return createDate(fromFormat.year, fromFormat.month, fromFormat.day);
                        } else {
                            console.log('fromFormat not valid');
                        }
                    } catch (e) {
                        console.log(e);
                    }
                },
                format: function(date) {
                    var DateTime = luxon.DateTime;
                    return DateTime.fromJSDate(date)
                        .setLocale('[[${#strings.replace(#locale, '_', '-')}]]')
                .toFormat('d LLLL yyyy');
                }
            });
        });
    }
});
