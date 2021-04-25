<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>   
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<jsp:include page="header.jsp"/>

<div id="main-container" class="container">
    <div class="container">
        <div class="row">
            <div class="col-sm-12">
                <h2 class="headline first-child text-color">How's the weather?</h2>
            </div>
        </div>
    </div>

    <div class="jumbotron">
        <div class="container">
            <div class="row">
                <div class="col-sm-12">
                    <form id="search-weather-form" data-action='/api/weather/search' data-method='post'>
                        <div class="input-group">
                            <input type="text" class="form-control" placeholder="Enter City Name OR City Name,Country Code..." name="searchString"/>
                            <div class="input-group-btn">
                                <button class="btn btn-primary" id="search-weather-button">
                                    <span class="glyphicon glyphicon-search"></span>
                                </button>
                            </div>
                        </div>
                        <small class="text-muted">(e.g. Manila Singapore Manila,PH Cambridge,UK)</small>
                    </form>
                </div>
            </div>
        </div>
    </div>

    <div class="container">
        <div class="row" id="error-div"></div>
        <div class="row">
            <div class="col-md-8 col-md-offset-2">
                <table class="table">
                    <colgroup>
                        <col class="col-xs-3">
                        <col class="col-xs-3">
                        <col class="col-xs-6">
                    </colgroup>
                    <tbody id="search-weather-logs-tbody"></tbody>
                </table>
            </div>
        </div>
    </div>
</div>

<jsp:include page="footer.jsp"/>
