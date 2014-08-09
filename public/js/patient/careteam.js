(function($){
  /* Element References */
  var careTeamList;

  var h3List;
  var i = 0;

  /* Constants */
  var icons = {
    header: "ui-accordion-header-icon1 ui-icon-triangle-1-e",
    activeHeader: "ui-accordion-header-icon1 ui-icon-triangle-1-s"
  };

  /* Functions */

  /* This function handles show and hide */
  var beforeActivate = function(event, ui) {
    if (ui.newHeader[0]) {
      var currHeader  = ui.newHeader;
      var currContent = currHeader.next('.ui-accordion-content');
    } else {
      var currHeader  = ui.oldHeader;
      var currContent = currHeader.next('.ui-accordion-content');
    }
    var isPanelSelected = currHeader.attr('aria-selected') == 'true';

    currHeader.toggleClass('accordion-header-active ui-corner-top',!isPanelSelected).attr('aria-selected',((!isPanelSelected).toString()));
    currHeader.children('.ui-icon').toggleClass('ui-icon-triangle-1-e',isPanelSelected).toggleClass('ui-icon-triangle-1-s',!isPanelSelected);
    currContent.toggleClass('accordion-content-active',!isPanelSelected)

    if (isPanelSelected) {
      currContent.slideUp(); 
    } else {
      var teamId = $(currHeader).data('teamid');
      currContent.slideDown();
      $(currContent ).html('loading');
      $.post( "/patient/careteamSpecific", {d:new Date(),careTeamId:teamId}, function( data ) {
        $(currContent ).html( data );
      });
    }
    return false; // Cancels the default action
  };


  /* This function handles fetching the content and populating the divs */
  var create = function( event, ui ) {
    if (ui.header[0]) {
      var currHeader  = ui.header[0];
      var currContent = ui.content[0];
      var teamId = $(currHeader).data('teamid');
      $(currContent ).html('loading');
      $.post( "/patient/careteamSpecific", {d:new Date(),careTeamId:teamId}, function( data ) {
        $(currContent ).html( data );
      });
    }

    h3List = $('#careTeamList h3');
    for(j = 0;j<h3List.length;j++) {
      i = j;
      var teamId = $(h3List[j]).data('teamid');
      $.post( "/patient/careteamSpecific", {d:new Date(),careTeamId:teamId}, function( data ) {
        $(h3List[i]).next('div').html( data );
      });
    }
    $('#careTeamList h3').removeClass('ui-state-default').removeClass('ui-state-active').removeClass('ui-corner-all').attr('aria-expanded', 'true').attr('aria-selected', 'true').attr('tabIndex', 0)
    .find('span.ui-icon').removeClass('ui-icon-triangle-1-e').addClass('ui-icon-triangle-1-s').closest('h3').next('div').show();
  };


  $(document).ready(function() {
    careTeamList = $("#careTeamList");

    careTeamList.accordion({
      collapsible: true,
      heightStyle: "content",
      icons: icons,
      beforeActivate: beforeActivate,
      create: create
    });
  });

})(jQuery);
