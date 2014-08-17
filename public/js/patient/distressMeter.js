var DistressMeter = function() {
  var showDistressButton, nextPageButton;
  var distressModal, distressForm, distressAmountText, distressAmountValue;
  var indicator, indicatorAmount, indicatorText;
  var sliderRange;
  var labelExtreme, labelNone;

  var amount = 1;

  var page = 1;

  var distressRange = {
    1: 'No Distress',
    2: 'Mild Distress',
    3: 'Mild Distress',
    4: 'Mild Distress',
    5: 'Mild Distress',
    6: 'Mild Distress',
    7: 'Mild Distress',
    8: 'Mild Distress',
    9: 'Mild Distress',
    10: 'Extreme Distress'
  };

  var range = function(value, min, max) {
    // debugger;
    value = parseInt(value) * (1/.92) ;
    return value * max + min;
  }

  var setIndicator = function(amount) {
    // 0 - 76
    // console.log(range(calculateTopOps(amount), 0, .76));
    indicator.css('bottom', range(calculateTopOps(amount), 0, .81) + '%');
    indicatorAmount.html(amount);
    indicatorText.html(distressRange[amount]);


    if ( labelNone.is(':visible') && amount < 2 ) {
      labelNone.animate({opacity: 0}).hide();
    } else if ( !labelNone.is(':visible') && amount >= 2) {
      labelNone.show().animate({opacity: 1});
    }


    if ( labelExtreme.is(':visible') && amount > 8 ) {
      console.log('fade out');
      labelExtreme.animate({opacity: 0}).hide();
    } else if ( !labelExtreme.is(':visible') && amount <= 8 ) {
      console.log('fade in');
      labelExtreme.show().animate({opacity: 1});
    }


  }



  var launchDistressMeter = function() {

    page = 1;
    $("#step1").show();
    distressAmountValue.html(amount);
    $("#step2").hide();
    $(".progress-bar").slider( "value", amount );
    setIndicator(amount);

    $(".otherDetails").val('');
    $('.toggle-switch input[name="distressType"]').prop('checked', false);
    distressModal.modal({ keyboard: false });

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
      $("#step1").hide();
      $(".step2Distress").html(amount);
      $("#step2").show();
      distressAmountText.html(distressRange[amount]);
    } else if ( page === 2 ) {
      var postData = distressForm.serializeArray();
      var formURL = distressForm.attr("action");
      $.post(formURL, postData, function( data ) {
        distressModal.modal('hide');
      });
    }
    page++;
  };

  var calculateTopOps = function(value) {
    var topOps = value-1;
    var topOps = topOps  * 10;
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
    nextPageButton = $('#distress button');
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

    showDistressButton.click(function(e){
      e.preventDefault();
      launchDistressMeter();
    });

    nextPageButton.click(function(e){
      e.preventDefault();
      nextPage();
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

    $("#distress button").click(function(){

    });
  });
  return {

  };
}();