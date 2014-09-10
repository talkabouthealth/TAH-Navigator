var careTeamController = (function() {
	var actions = {
		'ctp_diagnosis_json': '/carepatien/diagnosisjson',
		'ctp_diagnosis_update': '/carepatien/updateDiagnosis',
		'ctpt_radiation_form': '/carepatien/radiationForm',
        'ctpt_save_radiation_data': '/carepatien/saveRadiationData',
        'ctpt_chemotherapy_form': '/carepatien/chemotherapyForm',
        'ctpt_save_chemotherapy_data': '/carepatien/saveChemotherapyData',
        'ctpt_surgery_form': '/carepatien/surgeryForm',
        'ctpt_save_surgery_data': '/carepatien/saveSurgeryData',
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
    
    var addRadiationTreatmentData = function() {
    	var patientId = $('#radiation-treatment-form').attr('patient_id');
    	var radiationType = $('#rt_type').val();
        if (!radiationType) {
    		return false;
    	}
    	var dose = $('#rt_dose').val();
    	var schedule = $('#rt_schedule').val();
    	var startDate = $('#rt_start_date').val();
    	var endDate = $('#rt_end_date').val();
    	var region = $('#rt_region').val();
        var sideEffectIds = $('#rt_side_effects_div').data('rt-see-ids');
    	var notes = $('#rt_notes').val();
    	
        var params = {
            'patientId': patientId,
            'rtInfo.radiationType': radiationType,
            'rtInfo.dose': dose,
            'rtInfo.schedule': schedule,
            'rtInfo.startDate': startDate,
            'rtInfo.endDate': endDate,
            'rtInfo.region': region,
            'rtInfo.notes': notes
        };
        
        if (typeof sideEffectIds != "undefined") {
            for (var i = 0; i < sideEffectIds.length; i++) {
                params['sideEffect.' + i] = sideEffectIds[i];
            }
        }
        $.post(actions['ctpt_save_radiation_data'], params, function(htmlText) {
            $('#ui-tabs-4').html(htmlText);
        }, "html");
        return true;
    	
    };
    
    var addChemoTreatmentData = function() {
        var patientId = $('#chemotherapy-treatment-form').attr('patient_id');
        var medicationId = $('#ctt_gn').attr("medication-id");
        if (!medicationId) {
            return false;
        }
        var cycleNo = $('#ctt_cycle').val();
        var csId = $('#ctt_schedule').attr("cs-id");
        var doseReduction = $('#ctt_dose').attr("dose");
        var startDate = $('#ctt_start_date').val();
        var endDate = $('#ctt_end_date').val();
        var sideEffectIds = $('#ctt_side_effects_div').data('ctt-see-ids');
        var notes = $('#ctt_notes').val();
        var params = {
            'patientId': patientId,
            'cttInfo.medicationId': medicationId,
            'cttInfo.cycleNo': cycleNo,
            'cttInfo.csId': csId,
            'cttInfo.doseReduction': doseReduction,
            'cttInfo.startDate': startDate,
            'cttInfo.endDate': endDate,
            'cttInfo.notes': notes
        };
        if (typeof sideEffectIds != "undefined") {
            for (var i = 0; i < sideEffectIds.length; i++) {
                params['sideEffect.' + i] = sideEffectIds[i];
            }
        }
        $.post(actions['ctpt_save_chemotherapy_data'], params, function(htmlText) {
            $('#ui-tabs-4').html(htmlText);
        }, "html");
        return true;
    }
    var addSurgeryData = function() {
        var patientId = $('#surgery-treatment-form').attr('patient_id');
        var stId = $('#si_st').attr("st-id");
        if (!stId) {
            return false;
        }
        var surgeryDate = $('#si_date').val();
        var trId = $('#si_region').attr("tr-id");
        var sideEffectIds = $('#si_side_effects_div').data('si-see-ids');
        var notes = $('#si_notes').val();
        var params = {
            'patientId': patientId,
            'siInfo.stId': stId,
            'siInfo.surgeryDate': surgeryDate,
            'siInfo.trId': trId,
            'siInfo.notes': notes
        };
        if (typeof sideEffectIds != "undefined") {
            for (var i = 0; i < sideEffectIds.length; i++) {
                params['sideEffect.' + i] = sideEffectIds[i];
            }
        }
        $.post(actions['ctpt_save_surgery_data'], params, function(htmlText) {
            $('#ui-tabs-4').html(htmlText);
        }, "html");
        return true;
    }
    var initializeSurgeryForm = function(data) {
        $('#surgery-treatment-form').attr('init-flag', '1');
        var surgeryTypes = data.surgeryTypes;
        var length = surgeryTypes.length;
        var tmp = [];
        for (var i = 0; i < length; i++) {
            tmp[i] = surgeryTypes[i].name;
        }
        $('#si_st').data("surgeryTypes", surgeryTypes);
        $('#si_st').typeahead({
            source: tmp,
            updater: function(item) {
                var surgeryTypes = $('#si_st').data("surgeryTypes");
                var index = -1;
                for (var i = 0; i < surgeryTypes.length; i++) {
                    if (item == surgeryTypes[i].name) {
                        index = i;
                        break;
                    }
                }
                if (index != -1) {
                    $('#si_st').attr("st-id", surgeryTypes[index].id);
                    $('#si_st_div').addClass("has-success");
                    $('#si_st_icon').addClass("glyphicon-ok");
                    $('#si_st_div').removeClass("has-error");
                    $('#si_st_icon').removeClass("glyphicon-remove");
                }
                else {
                    $('#si_st_div').addClass("has-error");
                    $('#si_st_icon').addClass("glyphicon-remove");
                    $('#si_st_div').removeClass("has-success");
                    $('#si_st_icon').removeClass("glyphicon-ok");
                }
                return item;
            }
        });
        $('#si_st').keyup(function() {
            var str = $(this).val();
            if (!str) {
                $('#si_st_div').addClass("has-error");
                $('#si_st_icon').addClass("glyphicon-remove");
                $('#si_st_div').removeClass("has-success");
                $('#si_st_icon').removeClass("glyphicon-ok");
                $('#si_st').attr("st-id", '');
            }
        });
        $('#si_date').datepicker({
            dateFormat: "yy-mm-dd"
        });
        
        var treatmentRegions = data.treatmentRegions;
        length = treatmentRegions.length;
        tmp = [];
        for (var i = 0; i < length; i++) {
            tmp[i] = treatmentRegions[i].region;
        }
        $('#si_region').data("treatmentRegions", treatmentRegions);
        $('#si_region').typeahead({
            source: tmp,
            updater: function(item) {
                var treatmentRegions = $('#si_region').data("treatmentRegions");
                var index = -1;
                for (var i = 0; i < treatmentRegions.length; i++) {
                    if (item == treatmentRegions[i].region) {
                        index = i;
                        break;
                    }
                }
                if (index != -1) {
                    $('#si_region').attr("tr-id", treatmentRegions[index].id); 
                }
                return item;
            }
        });
        var sideEffects = data.sideEffects;
        length = sideEffects.length;
        tmp = [];
        for (var i = 0; i < length; i++) {
            tmp[i] = sideEffects[i].description;
        }
        $('#si_side_effects').typeahead({
            source: tmp,
            updater: function(item) {
                updateSideEffects('si_side_effects_div', 'si-see', item, 'add');
                return item;
            }
        });
        $('#si_side_effects_div').data("sideEffects", sideEffects);
        $('#add-surgery-treatment').click(function() {
            if (addSurgeryData()) {
                $('#surgery-treatment-form').modal("hide");
            }
        });
    };
    var emptySurgeryForm = function() {
        $('#si_st').val('');
        $('#si_date').val('');
        $('#si_region').val('');
        $('#si_side_effects').val('');
        $('#si_side_effects_div').empty();
        $('#si_side_effects_div').data('si-see', []);
        $('#si_side_effects_div').data('si-see-ids', []);
        $('#si_notes').val('');
    };
    var emptyChemoTreatmentForm = function() {
        $('#ctt_gn').val('');
        $('#ctt_bn').val('');
        $('#ctt_cycle').val('');
        $('#ctt_schedule').val('');
        $('#ctt_dose').val('');
        $('#ctt_start_date').val('');
        $('#ctt_end_date').val('');
        $('#ctt_side_effects').val('');
        $('#ctt_side_effects_div').empty();
        $('#ctt_side_effects_div').data('ctt-see', []);
        $('#ctt_side_effects_div').data('ctt-see-ids', []);
        $('#ctt_notes').val('');
    };
    var initializeChemoTreatmentForm = function(data) {
        $('#chemotherapy-treatment-form').attr('init-flag', '1');
        var medications = data.medications;
        var length = medications.length;
        var tmp = [];
        for (var i = 0; i < length; i++) {
            var name = medications[i].mgnDto.name + " (" + medications[i].mbnDto.name + ")";
            tmp[i] = name;
            medications[i].combinedName = name;
        }
        $('#ctt_gn').typeahead({
            source: tmp,
            updater: function(item) {
                var index = findMedication(item);
                if (index != -1) {
                    var medications = $('#ctt_gn').data("medications");
                    item = medications[index].mgnDto.name;
                    $('#ctt_bn').val(medications[index].mbnDto.name);
                    $('#ctt_gn').attr("medication-id", medications[index].id);
                    
                    $('#ctt_gn_div, #ctt_bn_div').addClass("has-success");
                    $('#ctt_gn_icon, #ctt_bn_icon').addClass("glyphicon-ok");
                    $('#ctt_gn_div, #ctt_bn_div').removeClass("has-error");
                    $('#ctt_gn_icon, #ctt_bn_icon').removeClass("glyphicon-remove");
                }
                else {
                    $('#ctt_gn_div, #ctt_bn_div').addClass("has-error");
                    $('#ctt_gn_icon, #ctt_bn_icon').addClass("glyphicon-remove");
                    $('#ctt_gn_div, #ctt_bn_div').removeClass("has-success");
                    $('#ctt_gn_icon, #ctt_bn_icon').removeClass("glyphicon-ok");
                }
                return item;
            }
        });
        $('#ctt_bn').typeahead({
            source: tmp,
            updater: function(item) {
                var index = findMedication(item);
                if (index != -1) {
                    var medications = $('#ctt_gn').data("medications");
                    item = medications[index].mbnDto.name;
                    $('#ctt_gn').val(medications[index].mgnDto.name);
                    $('#ctt_gn').attr("medication-id", medications[index].id);
                    
                    $('#ctt_gn_div, #ctt_bn_div').addClass("has-success");
                    $('#ctt_gn_icon, #ctt_bn_icon').addClass("glyphicon-ok");
                    $('#ctt_gn_div, #ctt_bn_div').removeClass("has-error");
                    $('#ctt_gn_icon, #ctt_bn_icon').removeClass("glyphicon-remove");
                }
                else {
                    $('#ctt_gn_div, #ctt_bn_div').addClass("has-error");
                    $('#ctt_gn_icon, #ctt_bn_icon').addClass("glyphicon-remove");
                    $('#ctt_gn_div, #ctt_bn_div').removeClass("has-success");
                    $('#ctt_gn_icon, #ctt_bn_icon').removeClass("glyphicon-ok");
                }
                return item;
            }
        });
        $('#ctt_gn').data("medications", medications);
        $('#ctt_gn, #ctt_bn').keyup(function() {
            var str = $(this).val();
            if (!str) {
                var domId = $(this).attr("id");
                $('#' + domId + "_div").addClass("has-error");
                $('#' + domId + "_icon").addClass("glyphicon-remove");
                $('#' + domId + "_div").removeClass("has-success");
                $('#' + domId + "_icon").removeClass("glyphicon-ok");
                $('#ctt_gn').attr("medication-id", '');
            }
        });
        tmp = [];
        for (var i = 0; i < 20; i++) {
            tmp[i] = '' + (i + 1);
        }
        $('#ctt_cycle').typeahead({
            source: tmp
        });
        
        var chemoSchedules = data.chemoSchedules;
        tmp = [];
        length = chemoSchedules.length;
        for (var i = 0; i < length; i++) {
            tmp[i] = chemoSchedules[i].timePeriod;
        }
        $('#ctt_schedule').typeahead({
            source: tmp,
            updater: function(item) {
                var arr = $('#ctt_schedule').data('chemoSchedules');
                for (var i = 0; i < arr.length; i++) {
                    if (item == arr[i].timePeriod) {
                        $('#ctt_schedule').attr("cs-id", arr[i].id);
                        break;
                    }
                }
                return item;
            }
        });
        $('#ctt_schedule').data('chemoSchedules', chemoSchedules);
        tmp = [];
        for (var i = 0; i < 100; i++) {
            tmp[i] = '' + (i + 1) + '%';
        }
        $('#ctt_dose').typeahead({
            source: tmp,
            updater: function(item) {
                var dose = parseInt(item);
                $('#ctt_dose').attr("dose", dose);
                return item;
            }
        });
        $('#ctt_start_date').datepicker({
            dateFormat: "yy-mm-dd"
        });
        $('#ctt_end_date').datepicker({
            dateFormat: "yy-mm-dd"
        });
        
        var sideEffects = data.sideEffects;
        length = sideEffects.length;
        tmp = [];
        for (var i = 0; i < length; i++) {
            tmp[i] = sideEffects[i].description;
        }
        $('#ctt_side_effects').typeahead({
            source: tmp,
            updater: function(item) {
                updateSideEffects('ctt_side_effects_div', 'ctt-see', item, 'add');
                return item;
            }
        });
        $('#ctt_side_effects_div').data("sideEffects", sideEffects);
        
        $('#add-chemotherapy-treatment').click(function() {
            if (addChemoTreatmentData()) {
                $('#chemotherapy-treatment-form').modal("hide");
            }
        });
    };
    var findMedication = function(name) {
        var medications = $('#ctt_gn').data("medications");
        var length = medications.length;
        for (var i = 0; i < length; i++) {
            if (medications[i].combinedName == name) {
                return i;
            }
        }
        return -1;
    };
    
    var initializeRadiationTreatmentForm = function(data) {
        $('#radiation-treatment-form').attr('init-flag', '1');
        var radiationTypes = data.radiationTypes;
        var length = radiationTypes.length;
        var tmp = [];
        for (var i = 0; i < length; i++) {
            tmp[i] = radiationTypes[i].name
        }
        $('#rt_type').typeahead({
            source: tmp
        });
        
        $('#rt_type').keyup(function() {
            var str = $(this).val();
            if (str) {
                $('#rt_type_div').addClass('has-success');
                $('#rt_type_icon').addClass('glyphicon-ok');
                $('#rt_type_div').removeClass('has-error');
                $('#rt_type_icon').removeClass('glyphicon-remove');
            }
            else {
                $('#rt_type_div').addClass('has-error');
                $('#rt_type_icon').addClass('glyphicon-remove');
                $('#rt_type_div').removeClass('has-success');
                $('#rt_type_icon').removeClass('glyphicon-ok');
            }
        });
        tmp = [];
        for (var i = 0; i < 100; i++) {
            tmp[i] = '' + (i+1) + ' Gy';
        }
        $('#rt_dose').typeahead({
            source: tmp
        });
        var radiationSchedules = data.radiationSchedules;
        length = radiationSchedules.length;
        tmp = [];
        for (var i = 0; i < length; i++) {
            tmp[i] = radiationSchedules[i].timePeriod;
        }
        $('#rt_schedule').typeahead({
            source: tmp
        });
        $('#rt_start_date').datepicker({
            dateFormat: "yy-mm-dd"
        });
        $('#rt_end_date').datepicker({
            dateFormat: "yy-mm-dd"
        });
        var treatmentRegions = data.treatmentRegions;
        length = treatmentRegions.length;
        tmp = [];
        for (var i = 0; i < length; i++) {
            tmp[i] = treatmentRegions[i].region;
        }
        $('#rt_region').typeahead({
            source: tmp
        });
        
        var sideEffects = data.sideEffects;
        length = sideEffects.length;
        tmp = [];
        for (var i = 0; i < length; i++) {
            tmp[i] = sideEffects[i].description;
        }
        $('#rt_side_effects').typeahead({
            source: tmp,
            updater: function(item) {
                updateSideEffects('rt_side_effects_div', 'rt-see', item, 'add');
                return item;
            }
        });
        $('#rt_side_effects_div').data("sideEffects", sideEffects);
        $('#add-radiation-treatment').click(function() {
            if (addRadiationTreatmentData()) {
                $('#radiation-treatment-form').modal('hide');
            }
        });
    };
    
    var emptyRadiationTreatmentForm = function() {
        $('#rt_type').val('');
        $('#rt_dose').val('');
        $('#rt_schedule').val('');
        $('#rt_start_date').val('');
        $('#rt_end_date').val('');
        $('#rt_region').val('');
        $('#rt_side_effects_div').empty();
        $('#rt_side_effects_div').data('rt-see', []);
        $('#rt_side_effects_div').data('rt-see-ids', []);
        $('#rt_side_effects').val('');
        $('#rt_notes').val('');
    };
    
    var radiationTreatmentForm = function(domElement) {
        var patientId = $(domElement).attr("patient_id");
        var initFlag = $('#radiation-treatment-form').attr('init-flag');
        $('#radiation-treatment-form').attr('patient_id', patientId);
        $.post(actions['ctpt_radiation_form'], {
            patientId: patientId,
            formType: 'new'
        }, function(data) {   
            if (initFlag == '0') {
                $('#radiation-treatment-form').modal({
                    keyboard: false,
                    backdrop: 'static'
                });
                initializeRadiationTreatmentForm(data);
            }
            else {
                $('#radiation-treatment-form').modal('show');
            }
            // empty fields for new form
            emptyRadiationTreatmentForm();
            // auto trigger for validation check
            $('#rt_type').keyup();
        }, "json");
    };
    var removeSideEffect = function(domElement) {
    	var elmId = $(domElement).attr('elmId');
    	var key = $(domElement).attr('key');
    	var item = $(domElement).attr('item');
    	updateSideEffects(elmId, key, item, 'remove');
    	
    };
    var updateSideEffects = function(elmId, key, item, op) {
    	var arr = $('#' + elmId).data(key);
    	if (typeof arr == 'undefined') {
    		arr = [];
    	}
    	var index = $.inArray(item, arr);
    	var length;
    	if (op == 'add') {
	    	if (index == -1) {
	    		arr[arr.length] = item;
	    	}
    	}
    	else if (op == 'remove') {
    		if (index != -1) {
    			arr.splice(index, 1);
    		}
    	}
    	$('#' + elmId).data(key, arr);
    	length = arr.length;
    	var htmlText = '';
    	for (var i = 0; i < length; i++) {
    		htmlText += '<div class="row"><div class="col-xs-12"><span class="glyphicon glyphicon-remove" elmId="'+ elmId +'" key="' + key + '" item="' + arr[i] + '" onclick="careTeamController.evtMsgHandler(\'remove_side_effect\', this);"><a href="#">' + arr[i] + '</a></span></div></div>';
    	}
    	$('#' + elmId).html(htmlText);
        
        var sideEffects = $('#' + elmId).data("sideEffects");
        var keyIds = key + "-ids";
        var tmpArr = [];
        for (var i = 0; i < arr.length; i++) {
            for (var j = 0; j < sideEffects.length; j++) {
                if (arr[i] == sideEffects[j].description) {
                    tmpArr[i] = sideEffects[j].id;
                    break;
                }
            }
        }
        $('#' + elmId).data(keyIds, tmpArr);
    };
    
    var chemoTreatmentForm = function(domElement) {
        var patientId = $(domElement).attr("patient_id");
        var initFlag = $('#chemotherapy-treatment-form').attr('init-flag');
        $('#chemotherapy-treatment-form').attr('patient_id', patientId);
        $.post(actions['ctpt_chemotherapy_form'], {
            formType: 'new',
            patientId: patientId
        }, function(data) {
        	if (initFlag == '0') {
                $('#chemotherapy-treatment-form').modal({
                    keyboard: false,
                    backdrop: "static"
                });
                initializeChemoTreatmentForm(data);
            }
            else {
                $('#chemotherapy-treatment-form').modal("show");
            }
            emptyChemoTreatmentForm();
        }, "json");
        
    };

    var surgeryForm = function(domElement) {
        var patientId = $(domElement).attr("patient_id");
        var initFlag = $('#surgery-treatment-form').attr('init-flag');
        $('#surgery-treatment-form').attr('patient_id', patientId);
        $.post(actions['ctpt_surgery_form'], {
            formType: 'new',
            patientId: patientId
        }, function(data) {
        	if (initFlag == '0') {
                $('#surgery-treatment-form').modal({
                    keyboard: false,
                    backdrop: "static"
                });
                initializeSurgeryForm(data);
            }
            else {
                $('#surgery-treatment-form').modal("show");
            }
            emptySurgeryForm();
        }, "json");
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