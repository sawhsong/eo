<!-- Keep the order of css files -->
<link type="text/css" rel="stylesheet" href="<mc:cp key="cssJqUi"/>/jquery-ui.css"/>
<link type="text/css" rel="stylesheet" href="<mc:cp key="cssBootstrap"/>/bootstrap.css"/>
<link type="text/css" rel="stylesheet" href="<mc:cp key="cssBootstrap"/>/font-awesome.css"/>
<link type="text/css" rel="stylesheet" href="<mc:cp key="cssTheme"/>/default.css"/>

<script type="text/javascript" src="<mc:cp key="jsJq"/>/jquery-2.1.4.js"></script>
<script type="text/javascript" src="<mc:cp key="jsJq"/>/jquery-ui.js"></script>
<script type="text/javascript" src="<mc:cp key="jsJq"/>/bootstrap.js"></script>
<script type="text/javascript" src="<mc:cp key="jsNg"/>/angular.js"></script>

<script type="text/javascript" src="<mc:cp key="jsMessage"/>/message-framework_${sessionScope.langCode}.js"></script>
<script type="text/javascript" src="<mc:cp key="jsMessage"/>/message-common_${sessionScope.langCode}.js"></script>
<script type="text/javascript" src="<mc:cp key="jsMessage"/>/message-project_${sessionScope.langCode}.js"></script>

<script type="text/javascript" src="<mc:cp key="jsJqPlugin"/>/jquery.blockUI.js"></script>
<script type="text/javascript" src="<mc:cp key="jsJqPlugin"/>/jquery.corner.js"></script>
<script type="text/javascript" src="<mc:cp key="jsJqPlugin"/>/jquery.layout-latest.js"></script>
<script type="text/javascript" src="<mc:cp key="jsJqPlugin"/>/jquery.textshadow.js"></script>
<script type="text/javascript" src="<mc:cp key="jsJqPlugin"/>/jquery.maskedinput.js"></script>
<script type="text/javascript" src="<mc:cp key="jsJqPlugin"/>/moment.js"></script>
<script type="text/javascript" src="<mc:cp key="jsJqPlugin"/>/numeral.js"></script>
<script type="text/javascript" src="<mc:cp key="jsJqPlugin"/>/jquery.number.js"></script>
<script type="text/javascript" src="<mc:cp key="jsJqPlugin"/>/sortableTable.js"></script>
<script type="text/javascript" src="<mc:cp key="jsJqPlugin"/>/jquery.actual.js"></script>
<script type="text/javascript" src="<mc:cp key="jsJqPlugin"/>/superfish.js"></script>
<script type="text/javascript" src="<mc:cp key="jsJqPlugin"/>/bootstrap-select.js"></script>
<script type="text/javascript" src="<mc:cp key="jsJqPlugin"/>/bowser.js"></script>

<script type="text/javascript" src="<mc:cp key="ckEditor"/>/ckeditor.js"></script>
<script type="text/javascript" src="<mc:cp key="ckEditor"/>/adapters/jquery.js"></script>
<script type="text/javascript" src="<mc:cp key="slider"/>/js/jssor.slider.min.js"></script>

<script type="text/javascript" src="<mc:cp key="jsCommon"/>/core.js"></script>
<script type="text/javascript" src="<mc:cp key="jsCommon"/>/popup.js"></script>
<script type="text/javascript" src="<mc:cp key="jsCommon"/>/calendar.js"></script>
<script type="text/javascript" src="<mc:cp key="jsCommon"/>/validator.js"></script>
<script type="text/javascript" src="<mc:cp key="jsCommon"/>/fixedHeaderTable.js"></script>
<script type="text/javascript" src="<mc:cp key="jsCommon"/>/ajax.js"></script>
<script type="text/javascript" src="<mc:cp key="jsCommon"/>/fileElement.js"></script>
<script type="text/javascript" src="<mc:cp key="jsCommon"/>/dataSet.js"></script>
<script type="text/javascript" src="<mc:cp key="jsCommon"/>/imageSlider.js"></script>

<script type="text/javascript" src="<mc:cp key="jsCommon"/>/UiAnchor.js"></script>
<script type="text/javascript" src="<mc:cp key="jsCommon"/>/UiCheckbox.js"></script>
<script type="text/javascript" src="<mc:cp key="jsCommon"/>/UiGridTd.js"></script>
<script type="text/javascript" src="<mc:cp key="jsCommon"/>/UiGridTr.js"></script>
<script type="text/javascript" src="<mc:cp key="jsCommon"/>/UiIcon.js"></script>

<script type="text/javascript" src="<mc:cp key="jsCommon"/>/contextMenu.js"></script>
<script type="text/javascript" src="<mc:cp key="jsCommon"/>/commonJs.js"></script>

<script type="text/javascript">
/*!
 * session attributes
 */
jsconfig.put("frameworkName", "${sessionScope.frameworkName}");
jsconfig.put("projectName", "${sessionScope.projectName}");
jsconfig.put("langCode", "${sessionScope.langCode}");
jsconfig.put("themeId", "${sessionScope.themeId}");
jsconfig.put("maxRowsPerPage", "${sessionScope.maxRowsPerPage}");
jsconfig.put("pageNumsPerPage", "${sessionScope.pageNumsPerPage}");
/*!
 * paths
 */
jsconfig.put("shareRoot", "<mc:cp key="sharedRoot"/>");
jsconfig.put("sharePage", "<mc:cp key="sharedPage"/>");
jsconfig.put("shareCss", "<mc:cp key="sharedCss"/>");
jsconfig.put("shareImg", "<mc:cp key="sharedImg"/>");
jsconfig.put("shareJs", "<mc:cp key="sharedJs"/>");
jsconfig.put("cssJqUi", "<mc:cp key="cssJqUi"/>");
jsconfig.put("imgIcon", "<mc:cp key="imgIcon"/>");
jsconfig.put("imgPhoto", "<mc:cp key="path.image.photo"/>");
jsconfig.put("imgSortableTable", "<mc:cp key="imgSortableTable"/>");
jsconfig.put("jsCommon", "<mc:cp key="jsCommon"/>");
jsconfig.put("jsJq", "<mc:cp key="jsJq"/>");
jsconfig.put("jsNg", "<mc:cp key="jsNg"/>");
jsconfig.put("jsJqPlugin", "<mc:cp key="jsJqPlugin"/>");
jsconfig.put("jsMessage", "<mc:cp key="jsMessage"/>");
jsconfig.put("cssTheme", "<mc:cp key="cssTheme"/>");
jsconfig.put("imgTheme", "<mc:cp key="imgTheme"/>");
jsconfig.put("imgThemeCom", "<mc:cp key="imgThemeCom"/>");
jsconfig.put("imgThemeCal", "<mc:cp key="imgThemeCal"/>");
jsconfig.put("imgThemePage", "<mc:cp key="imgThemePage"/>");
jsconfig.put("ckEditor", "<mc:cp key="ckEditor"/>");
jsconfig.put("slider", "<mc:cp key="slider"/>");
jsconfig.put("bootstrap", "<mc:cp key="bootstrap"/>");
/*!
 * jQuery UI
 */
jsconfig.put("useScrollablePanel", true);
jsconfig.put("useJqTooltip", true);
jsconfig.put("useJqSelectmenu", true);
jsconfig.put("useJqButton", true);
/*!
 * etc
 */
jsconfig.put("basicDateTimeJs", "<mc:cp key="format.basic.dateTime.js"/>");
jsconfig.put("dateTimeFormatJs", "<mc:cp key="format.dateTime.js"/>");
jsconfig.put("dateFormatJs", "<mc:cp key="dateFormatJs"/>");
jsconfig.put("dateFormatJs_en", "<mc:cp key="dateFormatJs_en"/>");
jsconfig.put("dateFormatJs_ko", "<mc:cp key="dateFormatJs_ko"/>");
jsconfig.put("submitEffect", "<mc:cp key="submitEffect"/>");
jsconfig.put("customFileObject", "<mc:cp key="customFileObject"/>");
jsconfig.put("recordDelimiter", "<mc:cp key="recordDelimiter"/>");
jsconfig.put("dataDelimiter", "<mc:cp key="dataDelimiter"/>");
jsconfig.put("dataSetHeaderDelimiter", "<mc:cp key="dataSetHeaderDelimiter"/>");
jsconfig.put("effectDuration", 400);
jsconfig.put("pagehandlerActionType", "<mc:cp key="pagehandlerActionType"/>");
jsconfig.put("webServiceProviderUrl", "<mc:cp key="webService.provider.url"/>");
jsconfig.put("maxRowsPerPageArray", "<mc:cp key="view.data.maxRowsPerPage"/>");
jsconfig.put("viewPageName", "<mc:cp key="viewPageName"/>");
/*!
 * auto-setup search criteria
 */
jsconfig.put("autoSetSearchCriteria", "<mc:cp key="autoSetSearchCriteria"/>");
jsconfig.put("searchCriteriaElementSuffix", "<mc:cp key="searchCriteriaElementSuffix"/>");
jsconfig.put("searchCriteriaDataSetString", "${pageScope.searchCriteriaDataSet.toStringForJs()}");
</script>
<script type="text/javascript" src="<mc:cp key="jsCommon"/>/contextMenuItems.js"></script>