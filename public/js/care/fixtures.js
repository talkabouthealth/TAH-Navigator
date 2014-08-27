var data = [
  [
    'Mary Jones',
    'Breast Cancer<br />Stage 2<br />ER+, HER2+',
    '4/12/2014',
    '1st appointment - Radiation',
    'Don Dizon',
    '9',
    '4/12/2014',
    'Child Care, Housing, Work, Breathing, Constipation',
    'Pain -8<br />Fatigue - 7<br />Nausea - 8',
    '4/12/2014',
    'None'
  ],
  [
    'Barbara Smith',
    'Breast Cancer<br />Stage 3<br />ER+, HER2+',
    '5/15/2014',
    'Treatment<br />Decision',
    'Don Dizon',
    '3',
    '5/12/2014',
    'N/A',
    'Fatigue - 6',
    '5/15/2014',
    'None'
  ],
  [
    'Jen Kallman',
    'Breast Cancer<br />Stage 3<br />ER+, HER2+',
    '5/4/2014',
    'Chemotherapy',
    'Don Dizon',
    '3',
    '5/20/2014',
    'N/A',
    'Fatigue - 6<br />Nausea - 8',
    '5/22/2014',
    'None'
  ],

];

(function($){
  $(document).ready(function(){
    var table = $('table');

    table.find('tbody tr').remove();

    $(data).each(function(i, d) {
      var tr = $('<tr />');
      $(d).each(function(j, el) {
        tr.append('<td>'+el+'</td>');
      });
      table.append(tr);

    });

    

  });
})(jQuery);
