var careTeamController = (function() {
	var actions = {
		'ctp_diagnosis_json': '/carepatien/diagnosisjson',
		'ctp_diagnosis_update': '/carepatien/updateDiagnosis',
		'default': '#'    // nothing 
	};
    var BREAST_CANCER_ID = 1;
    var diagnosis = function(domElement) {
        var patientId = $(domElement).attr("patient_id");
        $.post(actions['ctp_diagnosis_json'], {
               patientId: patientId
        }, function(data) {
        	var initFlag = $('#diagnosis-edit-form').attr('init-flag');
        	if (initFlag == '0') {
        		$('#diagnosis-edit-form').attr('init-flag', '1');
        		var diseases = data.diseases;
        		var length = diseases.length;
        		for (var i = 0; i < length; i++) {
        			$('#disease').append('<option value="' + diseases[i].id + '">' + diseases[i].name + '</option>');
        		}
        		$('#first-diagnosed').datepicker({
        			dateFormat: "yy-mm-dd"
        		});
        		$('#dob').datepicker({
        			dateFormat: "yy-mm-dd"
        		});
        		var bcStages = data.bcStages;
        		length = bcStages.length;
        		for (var i = 0; i < length; i++) {
        			$('#stage').append('<option value="' + bcStages[i].id + '">' + bcStages[i].name + '</option>');
        		}
        		$('#disease').change(function() {
        	        var disease_id = $(this).val();
        	        if (disease_id != BREAST_CANCER_ID) {  
        	            $('#er_div').hide();
        	            $('#pr_div').hide();
        	            $('#her2_div').hide();
        	            $('#stage_div').hide();
        	            $('#brca_div').hide();
        	        }
        	        else {
        	            $('#er_div').show();
        	            $('#pr_div').show();
        	            $('#her2_div').show();
        	            $('#stage_div').show();
        	            $('#brca_div').show();
        	        }
        	    });
        		$('#save-diagnosis-data').click(function(e) {
        			updateDiagnosisData();
        			$('#diagnosis-edit-form').modal('hide');
        		});
        	}
        	$('#diagnosis-edit-form').attr('patient_id', patientId);
            if ("diseaseId" in data) {
              $('#disease').val(data.diseaseId);
              if (BREAST_CANCER_ID == data.diseaseId) {
                  if ("stageId" in data) {
                      $('#stage').val(data.stageId);
                  }
                  else {
                	  $('#stage').val('');
                  }
                  $('#er').val(data.er);
                  $('#pr').val(data.pr);
                  $('#her2').val(data.her2);
                  $('#brca').val(data.brca);
              }
              else {
            	  $('#er').val('');
                  $('#pr').val('');
                  $('#her2').val('');
                  $('#brca').val('');
              }
            }
            else {
            	$('#diseaseId').val('');
            }
            if ("firstDiagnosed" in data) {
                $('#first-diagnosed').val(data.firstDiagnosed);
            }
            else {
            	$('#first-diagnosed').val('');
            }
            if ("dateOfBirth" in data) {
                $('#dob').val(data.dateOfBirth);
            }
            else {
            	$('#dob').val('');
            }
            if ("Phone" in data) {
                $("#phone").val(data.Phone);
            }
            else {
            	$("#phone").val('');
            }
            if ("supportName" in data) {
                $('#ec1name').val(data.supportName);
            }
            else {
            	$('#ec1name').val('');
            }
            if ("supportNumber" in data) {
                $('#ec1number').val(data.supportNumber);
            }
            else {
            	$('#ec1number').val('');
            }
            
            $('#disease').change(); 
        	$('#diagnosis-edit-form').modal({
        		keyboard: false,
        		backdrop: 'static'
        	});
        }, "json");
    };
    
    var updateDiagnosisData = function() {
        var patientId = $('#diagnosis-edit-form').attr("patient_id");
        // collect data and send
        var diseaseId = $('#disease').val();
        var dateOfDiagnosis = $('#first-diagnosed').val();
        var dob = $('#dob').val();
        var phone = $('#phone').val();
        var supportName = $('#ec1name').val();
        var supportNumber = $('#ec1number').val();
        var params = {
            'patientId': patientId, 
            'diseaseId': diseaseId, 
            'dateOfDiagnosis': dateOfDiagnosis, 
            'dob' : dob, 
            'phone' : phone, 
            'supportName' : supportName, 
            'supportNumber' : supportNumber
        };
        if (diseaseId == BREAST_CANCER_ID) {
            params['diseaseInfo.er'] = $('#er').val();
            params['diseaseInfo.pr'] = $('#pr').val();
            params['diseaseInfo.her2'] = $('#her2').val();
            params['diseaseInfo.stage_id'] = $('#stage').val();
            params['diseaseInfo.brca'] = $('#brca').val();
        }
        $('#ui-tabs-6').html()
        $.post(actions['ctp_diagnosis_update'], params, function(htmlText) {
            $('#ui-tabs-6').html(htmlText);
        }, "html");
    };
    
    var radiationTreatmentForm = function(domElement) {
    	$('#radiation-treatment-form').modal();
    	/*
        var patientId = $(domElement).attr("patient_id");
        $.post("/carepatien/radiationForm", {
            patientId: patientId,
            formType: 'new'
        }, function(data) {
            var radiationTypes = data;
            var length = radiationTypes.length;
            var tmp = [];
            for (var i = 0; i < length; i++) {
                tmp[i] = {
                    label: radiationTypes[i].name,
                    value: radiationTypes[i].id
                };
            }
            
            
            $('#radiation-treatment-form').modal();
        }, "json");
        
        */
        
    };
    
    var chemoTreatmentForm = function(domElement) {
        
    };

    var surgeryForm = function(domElement) {
        
    };
    
    var removeSideEffect = function(domElement) {
        var seContainer = $(domElement).parent().parent();
        var elm = $(domElement).parent();
        seContainer.append(elm.clone());
    };
    
    $(function() {});
    
    var msgMap = {
        'diagnosis': diagnosis,
        'radiation_treatment_form': radiationTreatmentForm,
        'chemotherapy_treatment_form': chemoTreatmentForm,
        'surgery_form': surgeryForm,
        'remove_side_effect': removeSideEffect
    };
    function evtMsgHandler(msg, domElement, params) {
        var func = msgMap[msg];
        if (typeof func !== 'undefined') {
            func(domElement, params);
        }
    }
    return {        
        evtMsgHandler: evtMsgHandler    
    };
})();