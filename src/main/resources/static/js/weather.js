$(document).ready(function() {
	console.log('ready to serve...');

	// Search button
	$("#search-weather-button").click(function(e) {
		e.preventDefault();

		var form = $("#search-weather-form");
		var errorDiv = $('#error-div');
		var dataContainer = $("#search-weather-logs-tbody");

		$.ajax({
			url		: form.data("action"),
			type	: form.data("method"),
			data	: form.serialize(),
			success	: function(data) {
				renderWeatherLogs(data, errorDiv, dataContainer);
			},
			error	: function(data) {
				renderErrorDiv(data, errorDiv, dataContainer);
			}
		});

	});

	// Delete button
	$(".delete-weather-log").click(function(e) {
		var container = $(this).closest('.weather-log-row');

		$.ajax({
			url		: "/api/weather/delete/" + container.data("id"),
			type	: "DELETE",
			success	: function() {
				container.fadeOut("normal", function() {
					$(this).remove();
				});
			}
		});
	});
});

function renderWeatherLogs(data, errorDiv, dataContainer) {
	errorDiv.html('');

	var weatherLogsHTML = '';

	for(var i=0; i<data.length; i++) {
		var icon 			= data[i].weatherData.icon;
		var cityName 		= data[i].cityName;
		var countryCode 	= data[i].countryCode;
		var weatherDate		= data[i].weatherDateStr;
		var temperature 	= data[i].temperature.temperature;
		var weatherGroup 	= data[i].weatherData.weatherGroup
		var weatherDesc 	= data[i].weatherData.description;
		var windSpeed 		= data[i].windSpeed + " m/s. ";
		var cloudiness 		= data[i].cloudiness + "% ";
		var pressure 		= data[i].temperature.pressure + " hpa";

		weatherLogsHTML += '<tr></tr><td><img src="https://openweathermap.org/img/wn/' + icon + '@2x.png" width="100" height="100" alt="Weather Icon"/></td>'
			+ '<td><div class="row"><span class="large-text"><b>' + cityName + '</b></span><span class="text-muted">, ' + countryCode +'</span></div><div class="row"><small class="text-muted">' + weatherDate + '</small></div></td>'
			+ '<td><div class="row"><span class="label label-default">' + temperature + ' &deg;С</span>&nbsp;<span>' + weatherGroup +' (' + weatherDesc+')</span></div><div class="row"><small class="text-muted">' + windSpeed + cloudiness + pressure + '</small></div></td></tr>';
	}

	dataContainer.html(weatherLogsHTML);
}

function renderErrorDiv(data, errorDiv, dataContainer) {
	dataContainer.html('');
	errorDiv.html('<div class="col-sm-6 alert alert-warning" role="alert">' + data.responseJSON.message + '</div>');
}