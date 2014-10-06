var DistressMeter = function() {
  var showDistressButton, nextPageButton, backButton;
  var distressModal, distressForm, distressAmountText, distressAmountValue;
  var step1, step2, step3;
  var indicator, indicatorAmount, indicatorText;
  var sliderRange;
  var labelExtreme, labelNone;

  var amount = 1;

  var page = 1;

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
    // debugger;
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
    amount = 1;

    page = 1;
    $("#step1").show();
    distressAmountValue.html(amount);
    $("#step2").hide();
    $(".progress-bar").slider( "value", amount );
    setIndicator(amount);
    $("#step3").hide();

    $(".otherDetails").val('');
    $('.toggle-switch input[name="distressType"]').prop('checked', false);
    distressModal.modal({ keyboard: false });
    distressModal.on('hidden.bs.modal', function (e) {
    	$("#youTubeVideo").show();
    	$("#youTubeVideoImage").hide();
    });
    distressModal.on('shown.bs.modal', function (e) {
    	$("#youTubeVideo").hide();
    	$("#youTubeVideoImage").show();
    });
    distressModal.find('div.stepchecker strong').each(function(){
      var strong = $(this);
      var toggle = strong.prev().find('label');
      strong.unbind('click').click(function(){
        toggle.click();
      });
    });

    if ( amount > 1 ) {
      labelNone.show().animate({opacity: 1});
    }
    if ( amount < 8 ) {
      labelExtreme.show().animate({opacity: 1});
    }
  };

  var nextPage = function() {
    amount = $( ".progress-bar" ).slider( "value" );
    if ( page === 1 ) {
      step1.hide();
      distressAmountValue.html(amount);
      step2.show();
      step3.hide();
      distressAmountText.html(distressRange[amount]);
    } else if ( page === 2 ) {
      var d = moment(new Date());
      document.forms.distressForm.daterecrded.value = d.format('M/D/YYYY h:m A');
      var postData = distressForm.serializeArray();
      var formURL = distressForm.attr("action");
      $.post(formURL, postData, function( data ) {
//        distressModal.modal('hide');
    	  nextPage();
      });
    } else if ( page === 3 ) {
//    	 distressModal.modal('hide');
    	 step1.hide();
    	 step2.hide();
         step3.show();
    } else if ( page === 4 ) {
    	distressModal.modal('hide');
    }
    page++;
  };

  var previousPage = function() {
    step1.show();
    step2.hide();
    step3.hide();
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
    document.forms.distressForm.curDist.value = value;
    setIndicator(value);
  };

  $(document).ready(function(){
    showDistressButton = $('a.distress-meter');
    nextPageButton = $('#distress button.next-page');
    backButton = $('#distress button.back');
    distressModal = $('#distress');
    distressForm = $('#distressForm');
    indicator = $('.progress .distress-amount');
    indicatorAmount = indicator.find('.distress-amount-value');
    indicatorText = indicator.find('.distress-amount-text');
    sliderRange = $('.progress-bar');
    distressAmountValue = $('.distress-amount-value');
    distressAmountText = $('.distress-amount-text');
    labelExtreme = distressModal.find('label.extreme-distress');
    labelNone = distressModal.find('label.no-distress');

    step1 = distressModal.find('#step1');
    step2 = distressModal.find('#step2');
    step3 = distressModal.find('#step3');
    showDistressButton.click(function(e){
      e.preventDefault();
      launchDistressMeter();
    });

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
      slide: slide,
      slide: function(event, ui) {
        if ( ui.value > 7 ) {
          $(this).addClass('high-distress').removeClass('medium-distress');
        } else if ( ui.value > 4 ) {
          $(this).removeClass('high-distress').addClass('medium-distress');
        } else {
          $(this).removeClass('high-distress').removeClass('medium-distress');
        }
      }
    });

  });

}();
