(function($){
  var populateFalseData = false;

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
  [
  'Long Note',
  'Long Note',
  '5/4/2014',
  'Chemotherapy',
  'Don Dizon',
  '3',
  '5/20/2014',
  'Bacon ipsum dolor sit amet meatloaf brisket swine, andouille biltong corned beef rump ground round tri-tip salami pork porchetta beef ribs. <br /><br />Chuck andouille sirloin, swine spare ribs ball tip pork loin ham hock frankfurter ground round cow chicken filet mignon corned beef salami. Kielbasa capicola andouille strip steak, pork frankfurter ball tip turducken shank. Ribeye hamburger porchetta, shankle capicola t-bone strip steak spare ribs chuck filet mignon. Beef rump sausage cow bacon turkey venison brisket ball tip tongue shankle shank shoulder. Kevin kielbasa doner porchetta prosciutto ribeye. Landjaeger ribeye venison pork brisket t-bone tongue strip steak bresaola spare ribs. Pork belly pork loin biltong turkey, kielbasa pig prosciutto kevin brisket beef. Pork jerky tongue pancetta, chicken ground round porchetta. Beef ball tip chuck chicken jowl prosciutto pastrami salami ham hock spare ribs. Venison cow pancetta, tenderloin hamburger pig sirloin shankle sausage t-bone turducken tail porchetta kielbasa. Turkey ground round bresaola, swine prosciutto pork belly ball tip pancetta short loin kielbasa jerky meatloaf sausage leberkas. Pork chop porchetta t-bone meatloaf leberkas jerky frankfurter beef corned beef kielbasa. Kevin rump pork bacon jerky chicken, turducken doner tongue ham venison spare ribs leberkas beef hamburger. Ground round doner ball tip beef ribs tongue frankfurter pancetta pork loin capicola short loin hamburger kielbasa chuck bacon. Frankfurter cow jerky pork belly, prosciutto sausage short ribs leberkas landjaeger. Strip steak flank sirloin biltong frankfurter tail jowl bresaola salami corned beef beef pastrami pork tenderloin. Hamburger frankfurter doner shankle, leberkas cow meatball shoulder swine fatback short loin drumstick kielbasa. Cow short ribs strip steak kielbasa bacon tri-tip capicola sirloin. Pancetta t-bone spare ribs, biltong meatloaf tail bresaola rump swine sirloin turkey shank brisket sausage hamburger. Chuck pork belly pork pork loin doner filet mignon landjaeger, andouille pastrami meatball meatloaf. Fatback doner cow, kielbasa pig meatloaf short loin turkey flank ham porchetta. Sausage chicken pancetta turducken ball tip meatball pork chop filet mignon. Pork loin short loin beef flank tenderloin. Shankle turducken rump shoulder leberkas ham hock.',
  'Bacon ipsum dolor sit amet meatloaf brisket swine, andouille biltong corned beef rump ground round tri-tip salami pork porchetta beef ribs. Chuck andouille sirloin, swine spare ribs ball tip pork loin ham hock frankfurter ground round cow chicken filet mignon corned beef salami. Kielbasa capicola andouille strip steak, pork frankfurter ball tip turducken shank. <br /><br />Ribeye hamburger porchetta, shankle capicola t-bone strip steak spare ribs chuck filet mignon. Beef rump sausage cow bacon turkey venison brisket ball tip tongue shankle shank shoulder. Kevin kielbasa doner porchetta prosciutto ribeye. Landjaeger ribeye venison pork brisket t-bone tongue strip steak bresaola spare ribs. Pork belly pork loin biltong turkey, kielbasa pig prosciutto kevin brisket beef. Pork jerky tongue pancetta, chicken ground round porchetta. Beef ball tip chuck chicken jowl prosciutto pastrami salami ham hock spare ribs. Venison cow pancetta, tenderloin hamburger pig sirloin shankle sausage t-bone turducken tail porchetta kielbasa. Turkey ground round bresaola, swine prosciutto pork belly ball tip pancetta short loin kielbasa jerky meatloaf sausage leberkas. Pork chop porchetta t-bone meatloaf leberkas jerky frankfurter beef corned beef kielbasa. Kevin rump pork bacon jerky chicken, turducken doner tongue ham venison spare ribs leberkas beef hamburger. Ground round doner ball tip beef ribs tongue frankfurter pancetta pork loin capicola short loin hamburger kielbasa chuck bacon. Frankfurter cow jerky pork belly, prosciutto sausage short ribs leberkas landjaeger. Strip steak flank sirloin biltong frankfurter tail jowl bresaola salami corned beef beef pastrami pork tenderloin. Hamburger frankfurter doner shankle, leberkas cow meatball shoulder swine fatback short loin drumstick kielbasa. Cow short ribs strip steak kielbasa bacon tri-tip capicola sirloin. Pancetta t-bone spare ribs, biltong meatloaf tail bresaola rump swine sirloin turkey shank brisket sausage hamburger. Chuck pork belly pork pork loin doner filet mignon landjaeger, andouille pastrami meatball meatloaf. Fatback doner cow, kielbasa pig meatloaf short loin turkey flank ham porchetta. Sausage chicken pancetta turducken ball tip meatball pork chop filet mignon. Pork loin short loin beef flank tenderloin. Shankle turducken rump shoulder leberkas ham hock.',
  '5/22/2014',
  'Bacon ipsum dolor sit amet meatloaf brisket swine, andouille biltong corned beef rump ground round tri-tip salami pork porchetta beef ribs. Chuck andouille sirloin, swine spare ribs ball tip pork loin ham hock frankfurter ground round cow chicken filet mignon corned beef salami. Kielbasa capicola andouille strip steak, pork frankfurter ball tip turducken shank.<br /> Ribeye hamburger porchetta, shankle capicola t-bone strip steak spare ribs chuck filet mignon. Beef rump sausage cow bacon turkey venison brisket ball tip tongue shankle shank shoulder. Kevin kielbasa doner porchetta prosciutto ribeye. Landjaeger ribeye venison pork brisket t-bone tongue strip steak bresaola spare ribs. Pork belly pork loin biltong turkey, kielbasa pig prosciutto kevin brisket beef. Pork jerky tongue pancetta, chicken ground round porchetta.<br /> Beef ball tip chuck chicken jowl prosciutto pastrami salami ham hock spare ribs. Venison cow pancetta, tenderloin hamburger pig sirloin shankle sausage t-bone turducken tail porchetta kielbasa. Turkey ground round bresaola, swine prosciutto pork belly ball tip pancetta short loin kielbasa jerky meatloaf sausage leberkas. Pork chop porchetta t-bone meatloaf leberkas jerky frankfurter beef corned beef kielbasa. Kevin rump pork bacon jerky chicken, turducken doner tongue ham venison spare ribs leberkas beef hamburger. Ground round doner ball tip beef ribs tongue frankfurter pancetta pork loin capicola short loin hamburger kielbasa chuck bacon. Frankfurter cow jerky pork belly, prosciutto sausage short ribs leberkas landjaeger. Strip steak flank sirloin biltong frankfurter tail jowl bresaola salami corned beef beef pastrami pork tenderloin. Hamburger frankfurter doner shankle, leberkas cow meatball shoulder swine fatback short loin drumstick kielbasa. Cow short ribs strip steak kielbasa bacon tri-tip capicola sirloin. Pancetta t-bone spare ribs, biltong meatloaf tail bresaola rump swine sirloin turkey shank brisket sausage hamburger. Chuck pork belly pork pork loin doner filet mignon landjaeger, andouille pastrami meatball meatloaf. Fatback doner cow, kielbasa pig meatloaf short loin turkey flank ham porchetta. Sausage chicken pancetta turducken ball tip meatball pork chop filet mignon. Pork loin short loin beef flank tenderloin. Shankle turducken rump shoulder leberkas ham hock.',
  ],

  ];


  if ( populateFalseData ) {

    $(document).ready(function(){
      var table = $('table');

      table.find('tbody tr').remove();

      $(data).each(function(i, d) {
        var tr = $('<tr />');
        var td;
        $(d).each(function(j, el) {
          td = $('<td><div>'+el+'</div></td>');
          tr.append(td);
        });
        table.append(tr);

      });

      var divs = table.find('div');

      var maxHeight = parseInt($(divs[0]).css('maxHeight'),10);
      var padding = parseInt($(divs[0]).css('padding'),10);

      divs.each(function(){
        var $this = $(this);
        var height = $this.height();
        if ( height >= maxHeight - padding*2) {
          $this.tooltipster({
            content: $this.html(),
            contentAsHTML: true,
            theme: 'tooltipster-light',
            maxWidth: 800
          });
        }
      });
    });
  }
})(jQuery);
