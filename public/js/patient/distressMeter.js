var DistressMeter = function() {
  var showDistressButton, nextPageButton, backButton;
  var distressContainer, distressForm, distressAmountText, distressAmountValue;
  var curDist;
  var step1, step2, step3;
  var indicator, indicatorAmount, indicatorText;
  var sliderRange;
  var labelExtreme, labelNone;
  var isProblemList;

  var patientId;

  var amount = 1;
  var postData;
  var formURL;
  var page = 1;
  var lastDistress = {};
  var distressRange = {
    1: 'No Distress',
    2: 'Mild Distress',
    3: 'Low Distress',
    4: 'Modest Distress',
    5: 'Moderate Distress',
    6: 'More Than Moderate Distress',
    7: 'High Distress',
    8: 'Severe Distress',
    9: 'Very Severe Distress',
    10: 'Extreme Distress'
  };

  var range = function(value, min, max) {
    value = parseInt(value,10) * (1/0.92) ;
    return value * max + min;
  };

  var setIndicator = function(amount) {
    // 0 - 76
    indicator.css('bottom', range(calculateTopOps(amount), 0, 0.81) + '%');
    indicatorAmount.html(amount);
    indicatorText.html(distressRange[amount]);

    if ( labelNone.is(':visible') && amount < 2 ) {
      labelNone.animate({opacity: 0}).hide();
    } else if ( !labelNone.is(':visible') && amount >= 2) {
      labelNone.show().animate({opacity: 1});
    }

    if ( labelExtreme.is(':visible') && amount > 8 ) {
      labelExtreme.animate({opacity: 0}).hide();
    } else if ( !labelExtreme.is(':visible') && amount <= 8 ) {
      labelExtreme.show().animate({opacity: 1});
    }
  };

  

  var launchDistressMeter = function() {

    $.post('/distress/lastDistressIn24Hours', {
        patientId: showDistressButton.attr("patientId")
    }, function(data) {
        setElements();
        if ($.isEmptyObject(data)) {
            lastDistress = {};
        }
        else {
            lastDistress = data.lastDistress;
        }        

        var amount;
        if ($.isEmptyObject(lastDistress)) {
            amount = 1;
        } else {
            amount = lastDistress.curDist;
        }    

        page = 1;
        $("#step1").show();
        distressAmountValue.html(amount);
        $("#step2").hide();
        $(".progress-bar").slider( "value", amount );
        $("#step3").hide();

        $(".otherDetails").val('');
        $('.toggle-switch input[name="distressType"]').prop('checked', false);
        setStepCheckers();
        setYouTubeVideo();
        setTimeout(function() {
            setIndicator(amount);
        });
    }, "json");
    
  };

  var setYouTubeVideo = function() {
    distressContainer.on('hidden.bs.modal', function (e) {
    	$("#youTubeVideo").show();
    	$("#youTubeVideoImage").hide();
    });
    distressContainer.on('shown.bs.modal', function (e) {
    	$("#youTubeVideo").hide();
    	$("#youTubeVideoImage").show();
    });
  };

  var setStepCheckers = function() {
    distressContainer.find('div.stepchecker strong').each(function(){
      var strong = $(this);
      var toggle = strong.prev().find('label');
      strong.unbind('click').click(function(){
        toggle.click();
      });
    });
  };

  var nextPage = function() {
    amount = $( ".progress-bar" ).slider( "value" );
    if ( page === 1 ) {
      step1.hide();
      distressAmountValue.html(amount);
      step2.show();
      step3.hide();
      distressAmountText.html(distressRange[amount]);
      if (!$.isEmptyObject(lastDistress)) {
        var otherDetail = lastDistress.otherdetail;
        var distressList = otherDetail.split(",<br/>");        
        $('div.stepchecker').each(function(index, elm) {
          var $checkbox = $(elm).find('input[type="checkbox"]');
          var $stext = $(elm).find("strong");
          var name = $stext.text();          
          for (var i = 0; i < distressList.length; i++) {
            if ( name == distressList[i]) {
              $checkbox.prop("checked", true);
              break;
            }
          }
        });
      }
    } else if ( page === 2 ) {
    	isProblemList = false;
    	 if (!$.isEmptyObject(lastDistress)) {
            $('div.stepchecker').each(function(index, elm) {
              var $checkbox = $(elm).find('input[type="checkbox"]');
                if ($checkbox.prop("checked")) {
                  isProblemList = true;
              }          
            });
    	 }
      var d = moment(new Date());
      document.forms.distressForm.daterecrded.value = d.format('M/D/YYYY h:m A');
      postData = distressForm.serializeArray();
      formURL = distressForm.attr("action");
      step1.hide();
      step2.hide();
      step3.show();
      $.post(formURL, postData, function( data ) {
    	  try{
    		mixpanel.track("Distress level");
    		if(isProblemList){
    			mixpanel.track("Problem list");
    		}
    	  }catch(e){}    		
      });
    } else if ( page === 3 ) {    
    	 distressContainer.modal('hide');

} else if ( page === 4 ) {
 distressContainer.modal('hide');
}
page++;
};

var previousPage = function() {
  if (page == 3) {
    step1.hide();
    step2.show();
    step3.hide();
  }
  else if (page == 2) {
    step1.show();
    step2.hide();
    step3.hide();
  }
  page--;
};

var calculateTopOps = function(value) {
  var topOps = ( value - 1 ) * 10;
  if(topOps>1) {
    topOps = topOps+2;
  }
  return topOps;
};

var slide = function( event, ui ) {
  var value = ui.value;
  curDist.val(value);
  setIndicator(value);

  if ( ui.value >= 7 ) {
    $(this).addClass('high-distress').removeClass('medium-distress');
  } else if ( ui.value >= 4 ) {
    $(this).removeClass('high-distress').addClass('medium-distress');
  } else {
    $(this).removeClass('high-distress').removeClass('medium-distress');
  }
};

var setElements = function() {
    if ( ! distressContainer ) { distressContainer = $('#distress'); }
    curDist = $('input[name="curDist"]');
  nextPageButton = distressContainer.find('button.next-page');
  backButton = distressContainer.find('button.back');
  distressForm = distressContainer.find('form');
  indicator = $('.progress .distress-amount');
  indicatorAmount = indicator.find('.distress-amount-value');
  indicatorText = indicator.find('.distress-amount-text');
  sliderRange = $('.progress-bar');
  distressAmountValue = $('.distress-amount-value');
  distressAmountText = $('.distress-amount-text');
  labelExtreme = distressContainer.find('label.extreme-distress');
  labelNone = distressContainer.find('label.no-distress');

  step1 = distressContainer.find('#step1');
  step2 = distressContainer.find('#step2');
  step3 = distressContainer.find('#step3');

  nextPageButton.click(function(e){
    e.preventDefault();
    nextPage();
  });

  backButton.click(function(e){
    e.preventDefault();
    previousPage();
  });

    // var slider_range_min_amount = $(".slider-range-min-amount");
    sliderRange.slider({
      range:  "min",
      min: 1,
      max: 10,
      value: 1,
      step: 1,
      orientation : 'vertical',
      slide: slide
    });

  // cute li'l hack to support iPads and touch devices for the toggle switches
  var touchend = function(e) {
    e.preventDefault();
    var toggleSwitch = $(this).find('.toggle-switch input');
    toggleSwitch.prop('checked', !toggleSwitch.prop('checked'));
  };
  $('.stepchecker').bind('touchend', touchend);
  $('.stepchecker').bind('touchcancel', touchend);
  $('.stepchecker').click(function(e){
    if ( e && e.target == this ) {
      touchend(e);
    }
  });

};

$(document).ready(function(){

    showDistressButton = $('a.distress-meter');
    showDistressButton.click(function(e){
        $('#distress').modal({ keyboard: false, backdrop: 'static' });
        launchDistressMeter();
    });



});

  // calling this function implies distress is not a modal
  var setTemplate = function(el) {
      $(document).ready(function() {
          el = $(el);
          if ( el.length ) {
              $('.content').html(el[0].outerHTML).addClass('distress');

              distressContainer = $('.content');
              // remove the modal on the page
              $('#distress').remove();
              launchDistressMeter();
          }
      });
  };

  return {
      setTemplate: setTemplate
  };

}();
