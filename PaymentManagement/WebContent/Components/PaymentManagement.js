$(document).ready(function() {
	$("#alertSuccess").hide();
	$("#alertError").hide();
});

// SAVE ============================================
$(document).on("click", "#btnSave", function(event) {
	// Clear alerts---------------------
	$("#alertSuccess").text("");
	$("#alertSuccess").hide();
	$("#alertError").text("");
	$("#alertError").hide();

	// Form validation-------------------
	var status = validateProjectForm();
	if (status != true) {
		$("#alertError").text(status);
		$("#alertError").show();
		return;
	}

	// If valid------------------------
	var type = ($("#hidProjectIDSave").val() == "") ? "POST" : "PUT";

	$.ajax({
		url : "PaymentManagementAPI",
		type : type,
		data : $("#formPaymentManagement").serialize(),
		dataType : "text",
		complete : function(response, status) {
			onProjectSaveComplete(response.responseText, status);
		}
	});
});

function onProjectSaveComplete(response, status) {
	if (status == "success") {
		var resultSet = JSON.parse(response);

		if (resultSet.status.trim() == "success") {
			$("#alertSuccess").text("Successfully saved.");
			$("#alertSuccess").show();

			$("#divProjectGrid").html(resultSet.data);
		} else if (resultSet.status.trim() == "error") {
			$("#alertError").text(resultSet.data);
			$("#alertError").show();
		}

	} else if (status == "error") {
		$("#alertError").text("Error while saving.");
		$("#alertError").show();
	} else {
		$("#alertError").text("Unknown error while saving..");
		$("#alertError").show();
	}

	$("#hidProjectIDSave").val("");
	$("#formProduct")[0].reset();
}

// UPDATE==========================================
$(document).on(
		"click",
		".btnUpdate",
		function(event) {
			$("#hidProjectIDSave").val(
					$(this).closest("tr").find('#hidProjectIDUpdate').val());
			
			$("#pid").val($(this).closest("tr").find('td:eq(0)').text());
			$("#acc").val($(this).closest("tr").find('td:eq(1)').text());
			$("#unit").val($(this).closest("tr").find('td:eq(2)').text());
			$("#price").val($(this).closest("tr").find('td:eq(3)').text());
			
			
		});

// REMOVE===========================================
$(document).on("click", ".btnRemove", function(event) {
	$.ajax({
		url : "PaymentManagementAPI",
		type : "DELETE",
		data : "id=" + $(this).data("payid"),
		dataType : "text",
		complete : function(response, status) {
			onProjectDeleteComplete(response.responseText, status);
		}
	});
});

function onProjectDeleteComplete(response, status) {
	if (status == "success") {
		var resultSet = JSON.parse(response);

		if (resultSet.status.trim() == "success") {

			$("#alertSuccess").text("Successfully deleted.");
			$("#alertSuccess").show();

			$("#divProjectGrid").html(resultSet.data);

		} else if (resultSet.status.trim() == "error") {
			$("#alertError").text(resultSet.data);
			$("#alertError").show();
		}

	} else if (status == "error") {
		$("#alertError").text("Error while deleting.");
		$("#alertError").show();
	} else {
		$("#alertError").text("Unknown error while deleting..");
		$("#alertError").show();
	}
}

// CLIENT-MODEL=========================================================================
function validateProjectForm() {
	
	if ($("#pid").val().trim() == "") {
		return "Insert Payment ID";
	}

	
	if ($("#acc").val().trim() == "") {
		return "Insert Account";
	}
	
	 
	 
	 var tmpunit = $("#unit").val().trim();
	 if (!$.isNumeric(tmpunit)) 
	 {
		 return "Insert Unit";
	 }
	 
	 
	 var tmpprice = $("#price").val().trim();
	 if (!$.isNumeric(tmpprice)) 
	 {
		 return "Insert Price";
	 }
	 
	 
	 
	 
	 
	 
	 
		


	return true;
}