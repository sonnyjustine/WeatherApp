<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<jsp:include page="header.jsp"/>

<div id="main-container" class="container">
    <div class="container">
        <div class="row">
            <div class="col-sm-12">
                <h2 class="headline first-child text-color">Weather Logs</h2>
            </div>
        </div>
    </div>

    <div class="jumbotron">
        <div class="container">
            <div class="row">
                <form action="/list" method="get">
                    <div class="form-group row">
                        <label for="cityName" class="col-sm-2 col-form-label">City</label>
                        <div class="col-sm-8 col-md-5"><input type="text" id="cityName" name="cityName" class="form-control"/></div>
                    </div>
                    <div class="form-group row">
                        <label for="countryCode" class="col-sm-2 col-form-label">Country Code</label>
                        <div class="col-sm-8 col-md-5"><input type="text" id="countryCode" name="countryCode" class="form-control"/></div>
                    </div>
                    <div class="form-group row">
                        <label for="weatherData" class="col-sm-2 col-form-label">Weather</label>
                        <div class="col-sm-8 col-md-5">
                            <select id="weatherData" name="weatherData" class="form-control">
                                <option value="">Select weather type...</option>
                                <c:forEach var="weatherData" items="${weatherDataList}">
                                    <option value="${weatherData.id}">${weatherData.weatherGroup} (${weatherData.description})</option>
                                </c:forEach>
                            </select>
                        </div>
                    </div>
                    <button type="submit" class="btn btn-primary">Filter Results</button>
                </form>
            </div>
        </div>
    </div>

    <div class="container">
        <c:if test="${empty weatherLogsList}">
            <div class="row" id="no-data-div">
                <div class="col-sm-6 alert alert-warning" role="alert">No matching records found for the given search criteria.</div>
            </div>
        </c:if>
        <div class="row">
            <div class="col-md-8 col-md-offset-2">
                <table class="table">
                    <colgroup>
                        <col class="col-xs-3">
                        <col class="col-xs-3">
                        <col class="col-xs-6">
                    </colgroup>
                    <tbody id="search-weather-logs-tbody">
                        <c:forEach items="${weatherLogsList}" var="log">
                        <tr class="weather-log-row" data-id="${log.id}">
                            <td>
                                <img src="https://openweathermap.org/img/wn/${log.weatherData.icon}@2x.png" width="100" height="100"  alt="Weather Icon"/>
                            </td>
                            <td>
                                <div class="row">
                                    <span class="large-text"><b>${log.cityName}</b></span>
                                    <span class="text-muted">, ${log.countryCode}</span>
                                </div>
                                <div class="row">
                                    <small class="text-muted">${log.weatherDateStr}</small>
                                </div></td>
                            <td>
                                <div class="row">
                                    <span class="label label-default">${log.temperature.temperature} &deg;C</span>&nbsp;<span>${log.weatherData.weatherGroup} (${log.weatherData.description})</span>
                                </div>
                                <div class="row">
                                    <small class="text-muted"><c:out value="${log.windSpeed} m/s. ${log.cloudiness}% ${log.temperature.pressure} hpa"></c:out></small>
                                </div>
                            </td>
                            <td>
                                <button type="button" class="btn btn-danger delete-weather-log">Delete</button>
                            </td>
                        </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>

<jsp:include page="footer.jsp"/>
