var DistressMeter = function() {
  var showDistressButton, nextPageButton;
  var distressModal, distressForm, distressAmountText, distressAmountValue;
  var step1, step2;
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
      step1.hide();
      distressAmountValue.html(amount);
      step2.show();
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

    step1 = distressModal.find('#step1');
    step2 = distressModal.find('#step2');

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

  });

}();
