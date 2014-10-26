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
        'fup_concern_form' : '/CarePatien/concernForm',
        'fup_save_concern' : '/CarePatien/saveConcern',
        'fup_remove_concern': '/CarePatien/removeConcern',
        'fup_goal_form' : '/CarePatien/goalForm',
        'fup_save_goal' : '/CarePatien/saveGoal',
        'fup_remove_goal': '/CarePatien/removeGoal',
        'fup_care_item_form' : '/CarePatien/careItemForm',
        'fup_save_care_item' : '/CarePatien/saveCareItem',
        'fup_remove_care_item': '/CarePatien/removeCareItem',
		'default': '#'    // nothing 
	};
    var BREAST_CANCER_ID = 1;
    
    var concernForm = {
        followUpDiv: '#followupplan',
        formId: '#follow-up-concern-form',
        saveBtnId: '#save-fup-concern',
        deleteBtnId: '#remove-fup-concern',
        concernId: '#fup_cc_concern',
        nextStepId: '#fup_cc_action_plan',
        notesId: '#fup_cc_notes',
        concernDiv: '#fup_cc_concern_div',
        concernSpan: '#fup_cc_concern_icon',
        
        concernOk: function() {
            var self = concernForm;
            $(self.concernDiv).addClass('has-success');
            $(self.concernSpan).addClass('glyphicon-ok');
            $(self.concernDiv).removeClass('has-error');
            $(self.concernSpan).removeClass('glyphicon-remove');
        },
        concernNotOk: function() {  
            var self = concernForm;
            $(self.concernDiv).addClass('has-error');
            $(self.concernSpan).addClass('glyphicon-remove');
            $(self.concernDiv).removeClass('has-success');
            $(self.concernSpan).removeClass('glyphicon-ok');
        },
        init: function() {
            var self = concernForm;
            $(self.formId).attr('init_flag', '1');
            $(self.concernId).keyup(function() {
                var str = $(this).val();
                if (str) {
                    self.concernOk();
                }
                else {
                    self.concernNotOk();
                }
            });
            $(self.saveBtnId).click(function() {
                if (self.save()) {
                    $(self.formId).modal('hide');
                }
            });
            $(self.deleteBtnId).click(function() {
                self.remove();
                $(self.formId).modal('hide');
            });
        },
        empty: function() {
            var self = concernForm;
            $(self.concernId).val('');
            $(self.nextStepId).val('');
            $(self.notesId).val('');
        },
        load: function(concern) {
            var self = concernForm;
            if ('concern' in concern) {
                $(self.concernId).val(concern.concern);
            }
            if ('nextStep' in concern) {
                $(self.nextStepId).val(concern.nextStep);
            }
            if ('notes' in concern) {
                $(self.notesId).val(concern.notes);
            }
        },
        validate: function() {
            var self = concernForm;
            var str = $(self.concernId).val();
            if (str) {
                self.concernOk();
            }
            else {
                self.concernNotOk();
            }
        },
        save: function() {
            var self = concernForm;
            var params = {
                'patientId': $(self.formId).attr('patient_id'),
                'fupConcern.concern': $(self.concernId).val(),
                'fupConcern.nextStep': $(self.nextStepId).val(),
                'fupConcern.notes': $(self.notesId).val()
            };
            if (!params['fupConcern.concern']) {
                return;
            }
            if ($(self.deleteBtnId).is(":visible")) {
                var concernId = $(self.deleteBtnId).attr("concern_id");
                params['concernId'] = concernId;
            }
            $.post(actions['fup_save_concern'], params, function(htmlText) {
                $(self.followUpDiv).html(htmlText);
            }, "html");
            return true; 
        },
        remove: function() {
            var self = concernForm;
            var patientId = $(self.formId).attr('patient_id'); 
            var concernId = $(self.deleteBtnId).attr("concern_id");
            var params = {
                'patientId': patientId,
                'concernId': concernId
            };
            
            $.post(actions['fup_remove_concern'], params, function(htmlText) {
                $(self.followUpDiv).html(htmlText);
            }, "html");
        },
        open: function(elm) {
            var self = concernForm;
            var initFlag = $(self.formId).attr('init_flag');
            var patientId = $(elm).attr("patient_id");
            var formType = $(elm).attr("form_type");
            var params = {};
            $(self.formId).attr("patient_id", patientId);
            
            var formLoad = function() {
                if (initFlag == '0') {
                    $(self.formId).modal({
                        keyboard: false,
                        backdrop: 'static'
                    });
                    self.init();
                }
                else {
                    $(self.formId).modal('show');
                }
                self.empty();
            };
            if (formType == 'new') {
                $(self.deleteBtnId).hide();
                formLoad();
                self.validate();
            }
            else {
                $(self.deleteBtnId).show();
                var concernId = $(elm).attr('concern_id');
                $(self.deleteBtnId).attr("concern_id", concernId);
                params['concernId'] = concernId;
                $.post(actions['fup_concern_form'], params, function(data) {
                    formLoad();
                    self.load(data.concern);
                    self.validate();
                }, "json");
            }
        }
    };
    
    var goalForm = {
        followUpDiv: '#followupplan',
        formId: '#follow-up-goal-form',
        saveBtnId: '#save-fup-goal',
        deleteBtnId: '#remove-fup-goal',
        goalId: '#fup_gl_goal',
        nextStepId: '#fup_gl_action_plan',
        goalDeadlineId: '#fup_gl_goal_deadline',
        notesId: '#fup_gl_notes',
        goalDiv: '#fup_gl_goal_div',
        goalSpan: '#fup_gl_goal_icon',
        
        goalOk: function() {
            var self = goalForm;
            $(self.goalDiv).addClass('has-success');
            $(self.goalSpan).addClass('glyphicon-ok');
            $(self.goalDiv).removeClass('has-error');
            $(self.goalSpan).removeClass('glyphicon-remove');
        },
        goalNotOk: function() {  
            var self = goalForm;
            $(self.goalDiv).addClass('has-error');
            $(self.goalSpan).addClass('glyphicon-remove');
            $(self.goalDiv).removeClass('has-success');
            $(self.goalSpan).removeClass('glyphicon-ok');
        },
        init: function() {
            var self = goalForm;
            $(self.formId).attr('init_flag', '1');
            $(self.goalId).keyup(function() {
                var str = $(this).val();
                if (str) {
                    self.goalOk();
                }
                else {
                    self.goalNotOk();
                }
            });
            $(self.goalDeadlineId).datepicker({
                autoclose: true,  
                todayHighlight: true,                
                format: "mm-dd-yyyy"
            });
            $(self.saveBtnId).click(function() {
                if (self.save()) {
                    $(self.formId).modal('hide');
                }
            });
            $(self.deleteBtnId).click(function() {
                self.remove();
                $(self.formId).modal('hide');
            });
        },
        empty: function() {
            var self = goalForm;
            $(self.goalId).val('');
            $(self.nextStepId).val('');
            $(self.goalDeadlineId).val('');
            $(self.notesId).val('');
        },
        load: function(goal) {
            var self = goalForm;
            if ('goal' in goal) {
                $(self.goalId).val(goal.goal);
            }
            if ('nextStep' in goal) {
                $(self.nextStepId).val(goal.nextStep);
            }
            if ('goalDeadline' in goal) {                
                $(self.goalDeadlineId).val(formatDate(new Date(goal.goalDeadline), 'mm-dd-yyyy'));
            }
            if ('notes' in goal) {
                $(self.notesId).val(goal.notes);
            }
        },
        validate: function() {
            var self = goalForm;
            var str = $(self.goalId).val();
            if (str) {
                self.goalOk();
            }
            else {
                self.goalNotOk();
            }
        },
        save: function() {
            var self = goalForm;            
            var params = {
                'patientId': $(self.formId).attr('patient_id'),
                'fupGoal.goal': $(self.goalId).val(),
                'fupGoal.nextStep': $(self.nextStepId).val(),
                'fupGoal.goalDeadline': $(self.goalDeadlineId).val(),
                'fupGoal.notes': $(self.notesId).val()
            };
            if (!params['fupGoal.goal']) {
                return;
            }
            if ($(self.deleteBtnId).is(":visible")) {
                var goalId = $(self.deleteBtnId).attr("goal_id");
                params['goalId'] = goalId;
            }
            $.post(actions['fup_save_goal'], params, function(htmlText) {
                $(self.followUpDiv).html(htmlText);
            }, "html");
            return true; 
        },
        remove: function() {
            var self = goalForm;
            var patientId = $(self.formId).attr('patient_id'); 
            var goalId = $(self.deleteBtnId).attr("goal_id");
            var params = {
                'patientId': patientId,
                'goalId': goalId
            };
            
            $.post(actions['fup_remove_goal'], params, function(htmlText) {
                $(self.followUpDiv).html(htmlText);
            }, "html");
        },
        open: function(elm) {
            var self = goalForm;
            var initFlag = $(self.formId).attr('init_flag');
            var patientId = $(elm).attr("patient_id");
            var formType = $(elm).attr("form_type");
            var params = {};
            $(self.formId).attr("patient_id", patientId);
            
            var formLoad = function() {
                if (initFlag == '0') {
                    $(self.formId).modal({
                        keyboard: false,
                        backdrop: 'static'
                    });
                    self.init();
                }
                else {
                    $(self.formId).modal('show');
                }
                self.empty();
            };
            if (formType == 'new') {
                $(self.deleteBtnId).hide();
                formLoad();
                self.validate();
            }
            else {
                $(self.deleteBtnId).show();
                var goalId = $(elm).attr('goal_id');
                $(self.deleteBtnId).attr("goal_id", goalId);
                params['goalId'] = goalId;
                $.post(actions['fup_goal_form'], params, function(data) {
                    formLoad();
                    self.load(data.goal);
                    self.validate();
                }, "json");
            }
        }
    };
    
    var careItemForm = {
        followUpDiv: '#followupplan',
        formId: '#follow-up-care-item-form',
        saveBtnId: '#save-fup-careitem',
        deleteBtnId: '#remove-fup-careitem',
        activityId: '#fup_ci_activity',
        frequencyId: '#fup_ci_frequency',
        endDateId: '#fup_ci_end_date',
        ongoingId: '#fup_ci_ongoing',
        purposeId: '#fup_ci_purpose',
        doctorId: '#fup_ci_doctor',
        activityDiv: '#fup_ci_activity_div',
        activitySpan: '#fup_ci_activity_span',
        
        activityOk: function() {
            var self = careItemForm;
            $(self.activityDiv).addClass('has-success');
            $(self.activitySpan).addClass('glyphicon-ok');
            $(self.activityDiv).removeClass('has-error');
            $(self.activitySpan).removeClass('glyphicon-remove');
        },
        activityNotOk: function() {  
            var self = careItemForm;
            $(self.activityDiv).addClass('has-error');
            $(self.activitySpan).addClass('glyphicon-remove');
            $(self.activityDiv).removeClass('has-success');
            $(self.activitySpan).removeClass('glyphicon-ok');
        },
        init: function() {
            var self = careItemForm;
            var activities = [ "Breast Self-Exam", "Mammogram", "Follow-up Appointment", "Pelvic Exam"  ];
            var purposeMap = {
                "Breast Self-Exam" : "Search for lumps and changes in breast",
                "Mammogram" : "Breast cancer screening to catch breast cancer early",
                "Follow-up Appointment" : "General check-up and long-term side effect follow-up",
                "Pelvic Exam" : "To screen for illness in the pelvic area"
            };
            var frequencies = [ "Daily", "Weekly", "Bi-weekly", "Monthly", "Every 3 Months", "Every 6 Months", "Every Year" ];
            
            var doctors = [ "Dr. Jorge Freire", "Dr. Maen Hussein" ];
            
            $(self.formId).attr('init_flag', '1');
            $(self.activityId).keyup(function() {
                var str = $(this).val();
                if (str) {
                    self.activityOk();
                }
                else {
                    self.activityNotOk();
                }
            });
            $(self.activityId).typeahead({
                source: activities,
                updater: function(item) {
                    if (item) {
                        self.activityOk();
                    }
                    else {
                        self.activityNotOk();
                    }
                    if (item in purposeMap) {
                        $(self.purposeId).val(purposeMap[item]);
                    }
                    else {
                        $(self.purposeId).val('');
                    }
                    return item;
                }
            });
            
            $(self.endDateId).datepicker({
                autoclose: true,  
                todayHighlight: true,                
                format: "mm-dd-yyyy"
            });
            
            $(self.ongoingId).click(function() {
                if ($(this).prop("checked")) {
                    $(self.endDateId).prop("disabled", true);
                }
                else {
                    $(self.endDateId).prop("disabled", false);
                }
            });
            
            $(self.frequencyId).typeahead({
                source: frequencies
            });
            
            $(self.doctorId).typeahead({
                source: doctors
            });
            $(self.saveBtnId).click(function() {
                if (self.save()) {
                    $(self.formId).modal('hide');
                }
            });
            $(self.deleteBtnId).click(function() {
                self.remove();
                $(self.formId).modal('hide');
            });
        },
        empty: function() {
            var self = careItemForm;
            $(self.activityId).val('');
            $(self.frequencyId).val('');
            $(self.endDateId).val('');
            $(self.endDateId).prop("disabled", false);
            $(self.ongoingId).prop("checked", false);
            $(self.purposeId).val('');
            $(self.doctorId).val('');
        },
        load: function(careItem) {
            var self = careItemForm;
            if ('activity' in careItem) {
                $(self.activityId).val(careItem.activity);
            }
            if ('frequency' in careItem) {
                $(self.frequencyId).val(careItem.frequency);
            }
            if ('endDate' in careItem) {                
                $(self.endDateId).val(formatDate(new Date(careItem.endDate), 'mm-dd-yyyy'));
            }
            if ('ongoing' in careItem) {
                if (careItem.ongoing) {
                    $(self.ongoingId).prop("checked", true);
                }
            }
            if ('purpose' in careItem) {
                $(self.purposeId).val(careItem.purpose);
            }
            if ('doctor' in careItem) {
                $(self.doctorId).val(careItem.doctor);
            }
        },
        validate: function() {
            var self = careItemForm;
            var str = $(self.activityId).val();
            if (str) {
                self.activityOk();
            }
            else {
                self.activityNotOk();
            }
            if ($(self.ongoingId).prop("checked")) {
                $(self.endDateId).prop("disabled", true);
            }
        },
        save: function() {
            var self = careItemForm;
            var params = {
                'patientId': $(self.formId).attr('patient_id'),
                'fupCareItem.activity': $(self.activityId).val(),
                'fupCareItem.frequency': $(self.frequencyId).val(),
                'fupCareItem.endDate': $(self.endDateId).val(),
                'fupCareItem.purpose': $(self.purposeId).val(),
                'fupCareItem.doctor': $(self.doctorId).val()
            };
            if ($(self.ongoingId).prop("checked")) {
                params['fupCareItem.ongoing'] = "ongoing";
                params['fupCareItem.endDate'] = "";
            }
            else {
                params['fupCareItem.ongoing'] = "";
            }
            if (!params['fupCareItem.activity']) {
                return;
            }
            if ($(self.deleteBtnId).is(":visible")) {
                var careItemId = $(self.deleteBtnId).attr("care_item_id");
                params['careItemId'] = careItemId;
            }
            $.post(actions['fup_save_care_item'], params, function(htmlText) {
                $(self.followUpDiv).html(htmlText);
            }, "html");
            return true; 
        },
        remove: function() {
            var self = careItemForm;
            var patientId = $(self.formId).attr('patient_id'); 
            var careItemId = $(self.deleteBtnId).attr("care_item_id");
            var params = {
                'patientId': patientId,
                'careItemId': careItemId
            };
            
            $.post(actions['fup_remove_care_item'], params, function(htmlText) {
                $(self.followUpDiv).html(htmlText);
            }, "html");
        },
        open: function(elm) {
            var self = careItemForm;
            var initFlag = $(self.formId).attr('init_flag');
            var patientId = $(elm).attr("patient_id");
            var formType = $(elm).attr("form_type");
            var params = {};
            $(self.formId).attr("patient_id", patientId);
            
            var formLoad = function() {
                if (initFlag == '0') {
                    $(self.formId).modal({
                        keyboard: false,
                        backdrop: 'static'
                    });
                    self.init();
                }
                else {
                    $(self.formId).modal('show');
                }
                self.empty();
            };
            if (formType == 'new') {
                $(self.deleteBtnId).hide();
                formLoad();
                self.validate();
            }
            else {
                $(self.deleteBtnId).show();
                var careItemId = $(elm).attr('care_item_id');
                $(self.deleteBtnId).attr("care_item_id", careItemId);
                params['careItemId'] = careItemId;
                $.post(actions['fup_care_item_form'], params, function(data) {
                    formLoad();
                    self.load(data.careItem);
                    self.validate();
                }, "json");
            }
        }
    };
    
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
                    format: 'mm/dd/yyyy',
                    autoclose: true
        		});
        		$('#dob').datepicker({
        			format: 'mm/dd/yyyy',
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
                $('#first-diagnosed').val(formatDate(new Date(data.firstDiagnosed), 'mm/dd/yyyy'));
            }
            else {
            	$('#first-diagnosed').val('');
            }
            if ("dateOfBirth" in data) {                
                $('#dob').val(formatDate(new Date(data.dateOfBirth), 'mm/dd/yyyy'));
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
        params['ctInfo.doseReduction'] = doseReduction;        
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
            format: 'mm/dd/yyyy',
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
                item  = '';
                return item;
            }
        });
        $('#si_side_effects').keyup(function(e) {
            if (e.keyCode == 13) {
                var item = $('#si_side_effects').val();
                if (item) {
                    updateSideEffects('si_side_effects_div', 'si-see', item, 'add');
                    $('#si_side_effects').val('');
                }
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
            $('#ctt_dose').val(pctDto.doseReduction);
        }
        if (pctDto.startDate) {            
            $('#ctt_start_date').val(formatDate(new Date(pctDto.startDate), 'mm/dd/yyyy'));
        }
        if (pctDto.endDate) {
            $('#ctt_end_date').val(formatDate(new Date(pctDto.endDate), 'mm/dd/yyyy'));
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
            format: 'mm/dd/yyyy',
            autoclose: true
        });
        $('#ctt_end_date').datepicker({
            format: 'mm/dd/yyyy',
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
                item = '';
                return item;
            }
        });
        $('#ctt_side_effects').keyup(function(e) {
            if (e.keyCode == 13) {
                var item = $('#ctt_side_effects').val();
                if (item) {
                    updateSideEffects('ctt_side_effects_div', 'ctt-see', item, 'add');
                    $('#ctt_side_effects').val('');
                }
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
        
        $('#datepickerRangeRadForm').datepicker({
              defaultDate: +1 ,minDate:"+1d",              
              format: "mm/dd/yyyy"
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
                item = '';
                return item;
            }
        });
        $('#rt_side_effects').keyup(function(e) {
            if (e.keyCode == 13) {
                var item = $('#rt_side_effects').val();
                if (item) {
                    updateSideEffects('rt_side_effects_div', 'rt-see', item, 'add');
                    $('#rt_side_effects').val('');
                }
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
            $('#si_date').val(formatDate(new Date(psiDto.surgeryDate), 'mm/dd/yyyy'));
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
            $('#rt_start_date').val(formatDate(new Date(prtDto.startDate), 'mm/dd/yyyy'));
        }
        if (prtDto.endDate) {
            $('#rt_end_date').val(formatDate(new Date(prtDto.endDate), 'mm/dd/yyyy'));
        }
        $('#datepickerRangeRadForm').datepicker({ startDate: prtDto.startDate, endDate: prtDto.endDate});
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
    var formatDate = function(date, format) {
        var day = date.getDate();
        var month = date.getMonth() + 1;
        var year = date.getFullYear();
        if (day < 10) {
            day = '0' + day;
        }
        if (month < 10) {
            month = '0' + month;
        }
        format = format.replace('yyyy', year);
        format = format.replace('mm', month);
        format = format.replace('dd', day);
        return format;
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
    
    var distressGraph = function(patientId, days) {                
        var url = '/distressvalues/' + patientId + '/' + days;
        $.get(url, function(data) {
            var $distressGraph = $('#patient_distress_graph');
            var distressPoints = [];     
            
            for (date in data) {
                var arr = [Number(date), Number(data[date])];
                distressPoints[distressPoints.length] = arr;                
            }
            var length = distressPoints.length;
            for (var i = 0; i < length; i++) {
                for (var j = i+1; j < length; j++) {
                    var timej = distressPoints[j][0];
                    var timei = distressPoints[i][0];
                    if (timej < timei) {
                        var tmp = distressPoints[j];
                        distressPoints[j] = distressPoints[i];
                        distressPoints[i] = tmp;
                    }
                }
            }            
            var maxDate = new Date().getTime();
            var ONE_DAY = 86400000;
            var minDate;        
            var weekTickSize = [2, 'day'];
            var monthTickSize = [7, 'day'];
            var threeMonthTickSize = [20, 'day'];
            var sixMonthTickSize = [30, 'day'];
            var yearTickSize = [60, 'day'];
            var tickSize;
            
            if (days > 0 && days <= 7) {
                tickSize = weekTickSize;
                minDate = maxDate - ( 7 * ONE_DAY);
            }
            else if (days > 7 && days <= 30) {
                tickSize = monthTickSize;
                minDate = maxDate - ( 30 * ONE_DAY);
            }
            else if (days > 30 && days <= 90) {
                tickSize = threeMonthTickSize;
                minDate = maxDate - ( 90 * ONE_DAY);
            }
            else if (days > 90 && days <= 180) {
                tickSize = sixMonthTickSize;
                minDate = maxDate - ( 180 * ONE_DAY);
            }
            else if (days > 180 && days <= 366) {
                tickSize = yearTickSize;
                minDate = maxDate - ( 366 * ONE_DAY);
            }
            else {
                tickSize = monthTickSize;   // need to calculate
                maxDate = distressPoints[length-1][0];
                minDate = distressPoints[0][0];
            }
            var plots = [
              {              
                  color: 'rgb(0, 0, 255)',
                  lines: { show: true},
                  points: { show: true },                  
                  data: distressPoints
              }
            ];
            
            
            var options = {            
                xaxis: {
                    mode: "time",                
                    timeformat: "%m/%d/%Y",
                    tickSize: tickSize,                    
                    min: minDate,
                    max: maxDate
                },
                
                yaxis: {                
                    max: 10,
                    ticks: [1, 2, 3, 4, 5, 6, 7, 8, 9, 10],
                    tickDecimals: 0,
                    min: 0
                }
            };
            $distressGraph.plot(plots, options);
            
        }, "json");
    };
    
    $(function() {     
        var $distressGraph = $('#patient_distress_graph');
        var patientId = $distressGraph.attr("patient_id");
        var days = $('.date-range.active').attr("days");        
        distressGraph(patientId, days);
        
        $(document).on('click', 'a.date-range', function() {
            $('a.date-range').removeClass("active");
            $(this).addClass("active");
            var days = $(this).attr("days");
            distressGraph(patientId, days);
        });
    });
    
    var msgMap = {
        'diagnosis': diagnosis,
        'radiation_treatment_form': radiationTreatmentForm,
        'chemotherapy_treatment_form': chemoTreatmentForm,
        'surgery_form': surgeryForm,
        'remove_side_effect': removeSideEffect,
        'toggle_validate': toggleValidate,
        'fup_concern_form': concernForm.open,
        'fup_goal_form': goalForm.open,
        'fup_careitem_form': careItemForm.open
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