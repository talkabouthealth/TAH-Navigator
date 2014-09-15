var careTeamController = (function() {
	var actions = {
		'ctp_diagnosis_json': '/carepatien/diagnosisjson',
		'ctp_diagnosis_update': '/carepatien/updateDiagnosis',
		'ctpt_radiation_form': '/carepatien/radiationForm',
        'ctpt_save_radiation_data': '/carepatien/saveRadiationData',
        'ctpt_remove_radiation_data': '/carepatien/removeRadiationData',
        'ctpt_chemotherapy_form': '/carepatien/chemotherapyForm',
        'ctpt_save_chemotherapy_data': '/carepatien/saveChemotherapyData',
        'ctpt_remove_chemotherapy_data': '/carepatien/removeChemotherapyData',
        'ctpt_surgery_form': '/carepatien/surgeryForm',
        'ctpt_save_surgery_data': '/carepatien/saveSurgeryData',
        'ctpt_remove_surgery_data': '/carepatien/removeSurgeryData',
        'verify': '/carepatien/verify',
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
                    format: 'yyyy-mm-dd',
                    autoclose: true
        		});
        		$('#dob').datepicker({
        			format: 'yyyy-mm-dd',
                    autoclose: true
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
            $('#diagnosis').html(htmlText);
        }, "html");
    };
    
    var removeRadiationTreatmentData = function() {
        var patientId = $('#radiation-treatment-form').attr('patient_id'); 
        var treatmentId = $('#remove-radiation-treatment').attr("treatment_id");
        var params = {
            'patientId': patientId,
            'treatmentId': treatmentId
        };
        
        $.post(actions['ctpt_remove_radiation_data'], params, function(htmlText) {
            $('#treatmentplan').html(htmlText);
        }, "html");
    };
    var removeChemotherapyData = function() {
        var patientId = $('#chemotherapy-treatment-form').attr('patient_id'); 
        var treatmentId = $('#remove-chemotherapy-treatment').attr("treatment_id");
        var params = {
            'patientId': patientId,
            'treatmentId': treatmentId
        };
        
        $.post(actions['ctpt_remove_chemotherapy_data'], params, function(htmlText) {
            $('#treatmentplan').html(htmlText);
        }, "html");
    };
    var removeSurgeryTreatmentData = function() {
        var patientId = $('#surgery-treatment-form').attr('patient_id'); 
        var treatmentId = $('#remove-surgery-treatment').attr("treatment_id");
        var params = {
            'patientId': patientId,
            'treatmentId': treatmentId
        };
        
        $.post(actions['ctpt_remove_surgery_data'], params, function(htmlText) {
            $('#treatmentplan').html(htmlText);
        }, "html");
    };
    var saveRadiationTreatmentData = function() {
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
        var sideEffects = $('#rt_side_effects_div').data('rt-see');
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
        
        if (typeof sideEffects != "undefined") {
            for (var i = 0; i < sideEffects.length; i++) {
                params['sideEffects.' + i] = sideEffects[i];
            }
        }
        
        if ($('#remove-radiation-treatment').is(":visible")) {
            var treatmentId = $('#remove-radiation-treatment').attr("treatment_id");
            params['treatmentId'] = treatmentId;
        }
        $.post(actions['ctpt_save_radiation_data'], params, function(htmlText) {
            $('#treatmentplan').html(htmlText);
        }, "html");
        return true;
    };
    
    var saveChemoTreatmentData = function() {
        var patientId = $('#chemotherapy-treatment-form').attr('patient_id');
        var genericName = $('#ctt_gn').val();
        var brandName = $('#ctt_bn').val();
        if (!genericName && !brandName) {
            return false;
        }
        var cycleNo = $('#ctt_cycle').val();
        var schedule = $('#ctt_schedule').val();
        var doseReduction = $('#ctt_dose').val();
        
        var startDate = $('#ctt_start_date').val();
        var endDate = $('#ctt_end_date').val();
        var sideEffects = $('#ctt_side_effects_div').data('ctt-see');
        var notes = $('#ctt_notes').val();
        var params = {
            'patientId': patientId,
            'ctInfo.genericName': genericName,
            'ctInfo.brandName': brandName,
            'ctInfo.cycleNo': cycleNo,
            'ctInfo.schedule': schedule,
            'ctInfo.startDate': startDate,
            'ctInfo.endDate': endDate,
            'ctInfo.notes': notes
        };
        if (parseInt(doseReduction)) {
            params['ctInfo.doseReduction'] = parseInt(doseReduction);
        }
        if (typeof sideEffects != "undefined") {
            for (var i = 0; i < sideEffects.length; i++) {
                params['sideEffects.' + i] = sideEffects[i];
            }
        }
        
        if ($('#remove-chemotherapy-treatment').is(":visible")) {
            var treatmentId = $('#remove-chemotherapy-treatment').attr("treatment_id");
            params['treatmentId'] = treatmentId;
        }
        
        $.post(actions['ctpt_save_chemotherapy_data'], params, function(htmlText) {
            $('#treatmentplan').html(htmlText);
        }, "html");
        return true;
    }
    var saveSurgeryData = function() {
        var patientId = $('#surgery-treatment-form').attr('patient_id');
        var surgeryType = $('#si_st').val();
        if (!surgeryType) {
    		return false;
    	}
        var surgeryDate = $('#si_date').val();
        var region = $('#si_region').val();
        var sideEffects = $('#si_side_effects_div').data('si-see');
        var notes = $('#si_notes').val();
        var params = {
            'patientId': patientId,
            'siInfo.surgeryType': surgeryType,
            'siInfo.surgeryDate': surgeryDate,
            'siInfo.region': region,
            'siInfo.notes': notes
        };
        if (typeof sideEffects != "undefined") {
            for (var i = 0; i < sideEffects.length; i++) {
                params['sideEffects.' + i] = sideEffects[i];
            }
        }
        if ($('#remove-surgery-treatment').is(":visible")) {
            var treatmentId = $('#remove-surgery-treatment').attr("treatment_id");
            params['treatmentId'] = treatmentId;
        }
        
        $.post(actions['ctpt_save_surgery_data'], params, function(htmlText) {
            $('#treatmentplan').html(htmlText);
        }, "html");
        return true;
    }
    var initializeSurgeryForm = function(data) {
        $('#surgery-treatment-form').attr('init_flag', '1');
        var surgeryTypes = data.surgeryTypes;
        var length = surgeryTypes.length;
        var tmp = [];
        for (var i = 0; i < length; i++) {
            tmp[i] = surgeryTypes[i].name;
        }
        $('#si_st').typeahead({
            source: tmp
        });
        $('#si_st').keyup(function() {
            var str = $(this).val();
            if (str) {
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
        });
        $('#si_date').datepicker({
            format: 'yyyy-mm-dd',
            autoclose: true
        });
        var treatmentRegions = data.treatmentRegions;
        length = treatmentRegions.length;
        tmp = [];
        for (var i = 0; i < length; i++) {
            tmp[i] = treatmentRegions[i].region;
        }
        
        $('#si_region').typeahead({
            source: tmp
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
        $('#si_side_effects').keyup(function(e) {
            if (e.keyCode == 13) {
                var item = $('#si_side_effects').val();
                updateSideEffects('si_side_effects_div', 'si-see', item, 'add');
                e.stopPropagation();
                e.preventDefault();
            }
        });
        $('#add-surgery-treatment').click(function() {
            if (saveSurgeryData()) {
                $('#surgery-treatment-form').modal("hide");
            }
        });
        $('#remove-surgery-treatment').click(function() {
            removeSurgeryTreatmentData();
            $('#surgery-treatment-form').modal('hide');
        });
    };
    var emptySurgeryForm = function() {
        $('#si_st').val('');
        $('#si_date').val('');
        $('#si_region').val('');
        $('#si_side_effects').val('');
        $('#si_side_effects_div').empty();
        $('#si_side_effects_div').data('si-see', []);
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
        $('#ctt_notes').val('');
    };
    var addChemotherapyToForm = function(pctDto) {
        $('#ctt_gn').val(pctDto.genericName);
        $('#ctt_bn').val(pctDto.brandName);
        if (pctDto.cycleNo) {
            $('#ctt_cycle').val(pctDto.cycleNo);
        }
        if ('csDto' in pctDto) {
            $('#ctt_schedule').val(pctDto.csDto.timePeriod);
        }
        if (pctDto.doseReduction) {
            $('#ctt_dose').val(pctDto.doseReduction + '%');
        }
        if (pctDto.startDate) {
            $('#ctt_start_date').val(dateToStr(new Date(pctDto.startDate), '-'));
        }
        if (pctDto.endDate) {
            $('#ctt_end_date').val(dateToStr(new Date(pctDto.endDate), '-'));
        }
        if ('pctSeDtos' in pctDto) {
            var pctSeDtos = pctDto.pctSeDtos;
            var length = pctSeDtos.length;
            var htmlText = '';
            var elmId = 'ctt_side_effects_div';
            var key = 'ctt-see';
            var arr = [];
            for (var i = 0; i < length; i++) {
                var seDto = pctSeDtos[i].seDto;
                var item = seDto.description;
                arr[i] = item;
                htmlText += '<div class="row"><div class="col-xs-12"><span class="glyphicon glyphicon-remove" elmId="'+ elmId +'" key="' + key + '" item="' + item + '" onclick="careTeamController.evtMsgHandler(\'remove_side_effect\', this);"><a href="#">' + item + '</a></span></div></div>';
            }
            $('#' + elmId).html(htmlText);
            $('#' + elmId).data(key, arr);
        }
        if (pctDto.notes) {
            $('#ctt_notes').val(pctDto.notes);
        }
    };
    var initializeChemoTreatmentForm = function(data) {
        $('#chemotherapy-treatment-form').attr('init_flag', '1');
        var medications = data.medications;
        var length = medications.length;
        var tmp = [];
        for (var i = 0; i < length; i++) {
            var label = medications[i].label;
            var brandname = medications[i].brandname;
            var arr = brandname.split(",");
            for (var j = 0; j < arr.length; j++) {
                tmp[tmp.length] = label + " (" + arr[j].trim() + ")";
            }
        }
        $('#ctt_gn').typeahead({
            source: tmp,
            updater: function(item) {
                var indexA = item.lastIndexOf("(");
                var indexB = item.lastIndexOf(")");
                var mgn = item.substring(0, indexA - 1);
                var mbn = item.substring(indexA + 1, indexB);
                $('#ctt_bn').val(mbn);
                $('#ctt_gn_div, #ctt_bn_div').addClass("has-success");
                $('#ctt_gn_icon, #ctt_bn_icon').addClass("glyphicon-ok");
                $('#ctt_gn_div, #ctt_bn_div').removeClass("has-error");
                $('#ctt_gn_icon, #ctt_bn_icon').removeClass("glyphicon-remove");
                return mgn;
            }
        });
        $('#ctt_bn').typeahead({
            source: tmp,
            updater: function(item) {
                var indexA = item.lastIndexOf("(");
                var indexB = item.lastIndexOf(")");
                var mgn = item.substring(0, indexA - 1);
                var mbn = item.substring(indexA + 1, indexB);
                $('#ctt_gn').val(mgn);
                $('#ctt_gn_div, #ctt_bn_div').addClass("has-success");
                $('#ctt_gn_icon, #ctt_bn_icon').addClass("glyphicon-ok");
                $('#ctt_gn_div, #ctt_bn_div').removeClass("has-error");
                $('#ctt_gn_icon, #ctt_bn_icon').removeClass("glyphicon-remove");
                return mbn;
            }
        });
        $('#ctt_gn, #ctt_bn').keyup(function() {
            var str = $(this).val();
            var domId = $(this).attr("id");
            if (str) {
                $('#' + domId + "_div").addClass("has-success");
                $('#' + domId + "_icon").addClass("glyphicon-ok");
                $('#' + domId + "_div").removeClass("has-error");
                $('#' + domId + "_icon").removeClass("glyphicon-remove");
            }
            else {
                $('#' + domId + "_div").addClass("has-error");
                $('#' + domId + "_icon").addClass("glyphicon-remove");
                $('#' + domId + "_div").removeClass("has-success");
                $('#' + domId + "_icon").removeClass("glyphicon-ok");
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
            source: tmp
        });
        tmp = [];
        for (var i = 0; i < 100; i++) {
            tmp[i] = '' + (i + 1) + '%';
        }
        $('#ctt_dose').typeahead({
            source: tmp
        });
        $('#ctt_start_date').datepicker({
            format: 'yyyy-mm-dd',
            autoclose: true
        });
        $('#ctt_end_date').datepicker({
            format: 'yyyy-mm-dd',
            autoclose: true
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
        $('#ctt_side_effects').keyup(function(e) {
            if (e.keyCode == 13) {
                var item = $('#ctt_side_effects').val();
                updateSideEffects('ctt_side_effects_div', 'ctt-see', item, 'add');
                e.stopPropagation();
                e.preventDefault();
            }
        });
        
        $('#add-chemotherapy-treatment').click(function() {
            if (saveChemoTreatmentData()) {
                $('#chemotherapy-treatment-form').modal("hide");
            }
        });
        $('#remove-chemotherapy-treatment').click(function() {
            removeChemotherapyData();
            $('#chemotherapy-treatment-form').modal('hide');
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
        $('#radiation-treatment-form').attr('init_flag', '1');
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
            format: 'yyyy-mm-dd',
            autoclose: true
        });
        $('#rt_end_date').datepicker({
            format: 'yyyy-mm-dd',
            autoclose: true
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
        $('#rt_side_effects').keyup(function(e) {
            if (e.keyCode == 13) {
                var item = $('#rt_side_effects').val();
                updateSideEffects('rt_side_effects_div', 'rt-see', item, 'add');
                e.stopPropagation();
                e.preventDefault();
            }
        });
        $('#add-radiation-treatment').click(function() {
            if (saveRadiationTreatmentData()) {
                $('#radiation-treatment-form').modal('hide');
            }
        });
        $('#remove-radiation-treatment').click(function() {
            removeRadiationTreatmentData();
            $('#radiation-treatment-form').modal('hide');
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
        $('#rt_side_effects').val('');
        $('#rt_notes').val('');
    };
    var addSurgeryInfoToForm = function(psiDto) {
        $('#si_st').val(psiDto.stDto.name);
        if (psiDto.surgeryDate) {
            $('#si_date').val(dateToStr(new Date(psiDto.surgeryDate), '-'));
        }
        if ('trDto' in psiDto) {
            $('#si_region').val(psiDto.trDto.region);
        }
        
        if ('pstSeDtos' in psiDto) {
            var pstSeDtos = psiDto.pstSeDtos;
            var length = pstSeDtos.length;
            var htmlText = '';
            var elmId = 'si_side_effects_div';
            var key = 'si-see';
            var arr = [];
            for (var i = 0; i < length; i++) {
                var seDto = pstSeDtos[i].seDto;
                var item = seDto.description;
                arr[i] = item;
                htmlText += '<div class="row"><div class="col-xs-12"><span class="glyphicon glyphicon-remove" elmId="'+ elmId +'" key="' + key + '" item="' + item + '" onclick="careTeamController.evtMsgHandler(\'remove_side_effect\', this);"><a href="#">' + item + '</a></span></div></div>';
            }
            $('#' + elmId).html(htmlText);
            $('#' + elmId).data(key, arr);
        }
        if (psiDto.notes) {
            $('#si_notes').val(psiDto.notes);
        }
    };
    var addRadiationTreatmentToForm = function(prtDto) {
        $('#rt_type').val(prtDto.rtDto.name);
        $('#rt_dose').val(prtDto.dose);
        if ('rsDto' in prtDto) {
            $('#rt_schedule').val(prtDto.rsDto.timePeriod);
        }
        if (prtDto.startDate) {
            $('#rt_start_date').val(dateToStr(new Date(prtDto.startDate), '-'));
        }
        if (prtDto.endDate) {
            $('#rt_end_date').val(dateToStr(new Date(prtDto.endDate), '-'));
        }
        if ('trDto' in prtDto) {
            $('#rt_region').val(prtDto.trDto.region);
        }
        if ('prtSeDtos' in prtDto) {
            var prtSeDtos = prtDto.prtSeDtos;
            var length = prtSeDtos.length;
            var htmlText = '';
            var elmId = 'rt_side_effects_div';
            var key = 'rt-see';
            var arr = [];
            for (var i = 0; i < length; i++) {
                var seDto = prtSeDtos[i].seDto;
                var item = seDto.description;
                arr[i] = item;
                htmlText += '<div class="row"><div class="col-xs-12"><span class="glyphicon glyphicon-remove" elmId="'+ elmId +'" key="' + key + '" item="' + item + '" onclick="careTeamController.evtMsgHandler(\'remove_side_effect\', this);"><a href="#">' + item + '</a></span></div></div>';
            }
            $('#' + elmId).html(htmlText);
            $('#' + elmId).data(key, arr);
        }
        $('#rt_notes').val(prtDto.notes);
    };
    var triggerRadiationFormEvents = function() {
        var str = $('#rt_type').val();
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
        
    };
    var triggerSurgeryFormEvents = function() {
        var str = $('#si_st').val();
        if (str) {
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
    };
    var triggerChemotherapyFormEvents = function() {
        var genericName = $('#ctt_gn').val();
        var brandName = $('#ctt_bn').val();
        if (genericName) {
            $('#ctt_gn_div').addClass("has-success");
            $('#ctt_gn_icon').addClass("glyphicon-ok");
            $('#ctt_gn_div').removeClass("has-error");
            $('#ctt_gn_icon').removeClass("glyphicon-remove");
        }
        else {
            $('#ctt_gn_div').addClass("has-error");
            $('#ctt_gn_icon').addClass("glyphicon-remove");
            $('#ctt_gn_div').removeClass("has-success");
            $('#ctt_gn_icon').removeClass("glyphicon-ok");
        }
        if (brandName) {
            $('#ctt_bn_div').addClass("has-success");
            $('#ctt_bn_icon').addClass("glyphicon-ok");
            $('#ctt_bn_div').removeClass("has-error");
            $('#ctt_bn_icon').removeClass("glyphicon-remove");
        }
        else {
            $('#ctt_bn_div').addClass("has-error");
            $('#ctt_bn_icon').addClass("glyphicon-remove");
            $('#ctt_bn_div').removeClass("has-success");
            $('#ctt_bn_icon').removeClass("glyphicon-ok");
        }
    };
    var radiationTreatmentForm = function(domElement) {
        var initFlag = $('#radiation-treatment-form').attr('init_flag');
        var patientId = $(domElement).attr("patient_id");
        var formType = $(domElement).attr("form_type");
        var params = {
            patientId: patientId,
            formType: formType,
            initFlag: initFlag
        };
        if (formType == 'new') {
            $('#remove-radiation-treatment').hide();
        }
        else {
            $('#remove-radiation-treatment').show();
            var treatmentId = $(domElement).attr('treatment_id');
            $('#remove-radiation-treatment').attr("treatment_id", treatmentId); 
            params['treatmentId'] = treatmentId;
        }
        $('#radiation-treatment-form').attr('patient_id', patientId);
        $.post(actions['ctpt_radiation_form'], params, function(data) {   
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
            emptyRadiationTreatmentForm();
            if (formType == 'edit') {
                addRadiationTreatmentToForm(data.prtDto);
            }
            triggerRadiationFormEvents();
        }, "json");
    };
    var dateToStr = function(date, separator) {
        var day = date.getDate();
        var month = date.getMonth() + 1;
        var year = date.getFullYear();
        if (day < 10) {
            day = '0' + day;
        }
        if (month < 10) {
            month = '0' + month;
        }
        return year + separator + month + separator + day;
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
    	length = arr.length;
    	var htmlText = '';
    	for (var i = 0; i < length; i++) {
    		htmlText += '<div class="row"><div class="col-xs-12"><span class="glyphicon glyphicon-remove" elmId="'+ elmId +'" key="' + key + '" item="' + arr[i] + '" onclick="careTeamController.evtMsgHandler(\'remove_side_effect\', this);"><a href="#">' + arr[i] + '</a></span></div></div>';
    	}
    	$('#' + elmId).html(htmlText);
        $('#' + elmId).data(key, arr);
    };
    
    var chemoTreatmentForm = function(domElement) {
        var initFlag = $('#chemotherapy-treatment-form').attr('init_flag');
        var patientId = $(domElement).attr("patient_id");
        var formType = $(domElement).attr("form_type");
        var params = {
            patientId: patientId,
            formType: formType,
            initFlag: initFlag
        };
        if (formType == 'new') {
            $('#remove-chemotherapy-treatment').hide();
        }
        else {
            $('#remove-chemotherapy-treatment').show();
            var treatmentId = $(domElement).attr('treatment_id');
            $('#remove-chemotherapy-treatment').attr("treatment_id", treatmentId); 
            params['treatmentId'] = treatmentId;
        }
        $('#chemotherapy-treatment-form').attr('patient_id', patientId);
        
        $.post(actions['ctpt_chemotherapy_form'], params, function(data) {
            if (initFlag == '0') {
                $('#chemotherapy-treatment-form').modal({
                    keyboard: false,
                    backdrop: 'static'
                });
                initializeChemoTreatmentForm(data);
            }
            else {
                $('#chemotherapy-treatment-form').modal('show');
            }
            emptyChemoTreatmentForm();
            if (formType == 'edit') {
                addChemotherapyToForm(data.pctDto);
            }
            triggerChemotherapyFormEvents();
        }, "json");
    };

    var surgeryForm = function(domElement) {
        var initFlag = $('#surgery-treatment-form').attr('init_flag');
        var patientId = $(domElement).attr("patient_id");
        var formType = $(domElement).attr("form_type");
        var params = {
            patientId: patientId,
            formType: formType,
            initFlag: initFlag
        };
        if (formType == 'new') {
            $('#remove-surgery-treatment').hide();
        }
        else {
            $('#remove-surgery-treatment').show();
            var treatmentId = $(domElement).attr('treatment_id');
            $('#remove-surgery-treatment').attr("treatment_id", treatmentId); 
            params['treatmentId'] = treatmentId;
        }
        $('#surgery-treatment-form').attr('patient_id', patientId);
        $.post(actions['ctpt_surgery_form'], params, function(data) {   
            if (initFlag == '0') {
                $('#surgery-treatment-form').modal({
                    keyboard: false,
                    backdrop: 'static'
                });
                initializeSurgeryForm(data);
            }
            else {
                $('#surgery-treatment-form').modal('show');
            }
            emptySurgeryForm();
            if (formType == 'edit') {
                addSurgeryInfoToForm(data.psiDto);
            }
            triggerSurgeryFormEvents();
        }, "json");
    };
    
    var toggleValidate = function(domElement) {
        var verified = $(domElement).attr('verified');
        if (verified == 'yes') {
            var initFlag = $('#invalidate_dialog').attr('init_flag');
            if (initFlag == '0') {
                $('#invalidate_dialog').attr('init_flag', '1');
                $('#invalidate_yes').click(function() {
                    var $btn = $('.validate_btn');
                    var $visibleBtn = $('.validate_btn:visible');
                    var patientId = $visibleBtn.attr('patient_id');
                    $.post(actions['verify'], {
                        patientId: patientId,
                        isVerified: false
                    }, function(data) {
                        $btn.attr("verified", "no");
                        $btn.text("Validate Now");
                    }, "json");
                    $('#invalidate_dialog').modal('hide');
                });
                
                $('#invalidate_dialog').modal({
                    keyboard: false,
                    backdrop: 'static'
                });
            }
            else {
                $('#invalidate_dialog').modal('show');
            }
        }
        else {
            var initFlag = $('#validate_dialog').attr('init_flag');
            $('#validate_yes').click(function() {
                var $btn = $('.validate_btn');
                var $visibleBtn = $('.validate_btn:visible');
                var patientId = $visibleBtn.attr('patient_id');
                $.post(actions['verify'], {
                    patientId: patientId,
                    isVerified: true
                }, function(data) {
                    $btn.attr("verified", "yes");
                    $btn.text("Validated");
                }, "json");
                $('#validate_dialog').modal('hide');
            });
            if (initFlag == '0') {
                $('#validate_dialog').attr('init_flag', '1');
                $('#validate_dialog').modal({
                    keyboard: false,
                    backdrop: 'static'
                });
            }
            else {
                $('#validate_dialog').modal('show');
            }
        }
    };
    
    $(function() {});
    
    var msgMap = {
        'diagnosis': diagnosis,
        'radiation_treatment_form': radiationTreatmentForm,
        'chemotherapy_treatment_form': chemoTreatmentForm,
        'surgery_form': surgeryForm,
        'remove_side_effect': removeSideEffect,
        'toggle_validate': toggleValidate
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