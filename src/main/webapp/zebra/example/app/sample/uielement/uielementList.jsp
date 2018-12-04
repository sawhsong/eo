<%/************************************************************************************************
* Description
* - 
************************************************************************************************/%>
<%@ include file="/shared/page/incCommon.jsp"%>
<%/************************************************************************************************
* Declare objects & variables
************************************************************************************************/%>
<%
	ParamEntity pe = (ParamEntity)request.getAttribute("paramEntity");
	DataSet ds = (DataSet)pe.getObject("resultDataSet");
%>
<%/************************************************************************************************
* HTML
************************************************************************************************/%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<link rel="icon" type="image/png" href="<mc:cp key="imgIcon"/>/zebraFavicon.png">
<title><mc:msg key="fwk.main.system.title"/></title>
<%/************************************************************************************************
* Stylesheet & Javascript
************************************************************************************************/%>
<%@ include file="/shared/page/incCssJs.jsp"%>
<style type="text/css">
</style>
<script type="text/javascript" src="<mc:cp key="viewPageJsName"/>"></script>
<script type="text/javascript">
</script>
</head>
<%/************************************************************************************************
* Page & Header
************************************************************************************************/%>
<body>
<form id="fmDefault" name="fmDefault" method="post" action="">
<div id="divHeaderHolder" class="ui-layout-north"><%@ include file="/zebra/example/common/include/header.jsp"%></div>
<div id="divBodyHolder" class="ui-layout-center">
<div id="divBodyLeft" class="ui-layout-west"><%@ include file="/zebra/example/common/include/bodyLeft.jsp"%></div>
<div id="divBodyCenter" class="ui-layout-center">
<div id="divFixedPanel">
<div id="divLocationPathArea"><%@ include file="/zebra/example/common/include/bodyLocationPathArea.jsp"%></div>
<%/************************************************************************************************
* Real Contents - fixed panel(tab, button, search, information)
************************************************************************************************/%>
<div id="divTabArea" class="areaContainer">
	<ui:tab id="tabCategory">
		<ui:tabList caption="fwk.uielement.tabTable" isActive="true" iconClass="fa-table" iconPosition="left"/>
		<ui:tabList caption="fwk.uielement.tabElements" iconClass="fa-edit" iconPosition="left"/>
		<ui:tabList caption="fwk.uielement.tabEtc" iconClass="fa-code" iconPosition="left"/>
		<ui:tabList caption="Disabled Tab" iconClass="fa-code" iconPosition="left" isClickable="false"/>
		<ui:tabList caption="Disabled Tab" iconClass="fa-code" iconPosition="left" isActive="true" isClickable="false"/>
	</ui:tab>
</div>
<div id="divButtonArea">
	<div id="divButtonAreaLeft"></div>
	<div id="divButtonAreaRight"></div>
</div>
<div id="divSearchCriteriaArea"></div>
<div id="divInformArea"></div>
<%/************************************************************************************************
* End of fixed panel
************************************************************************************************/%>
<div class="breaker"></div>
</div>
<div id="divScrollablePanel">
<%/************************************************************************************************
* Real Contents - scrollable panel(data, paging)
************************************************************************************************/%>
<div id="divDataArea" class="areaContainer">
	<div id="div0" style="">
		<div class="panel panel-default">
			<div class="panel-heading"><h3 class="panel-title">Data Table in Panel - with Body Panel</h3></div>
			<div class="panel-body">Data Table</div>
			<table id="tblFixedHeaderTable1" class="tblGrid sort autosort">
				<caption class="captionEdit">Data Table in Panel - with Body Panel</caption>
				<colgroup>
					<col width="4%"/>
					<col width="4%"/>
					<col width="32%"/>
					<col width="55%"/>
					<col width="5%"/>
				</colgroup>
				<thead>
					<tr>
						<th class="thGrid"><i id="icnCheck" class="fa fa-check-square-o fa-lg icnEn" title="Select to generate"></i></th>
						<th class="thGrid sortable:numeric">No.</th>
						<th class="thGrid sortable:string">Table Name</th>
						<th class="thGrid sortable:string">Table Description</th>
						<th class="thGrid">Action</th>
					</tr>
				</thead>
				<tbody>
<%
				for (int i=0; i<10; i++) {
%>
					<tr class="success">
						<td class="tdGrid Ct"><input type="checkbox" id="chkForGenerate" name="chkForGenerate" class="chkEn inTblGrid" value="NONY_BOARD"/></td>
						<td class="tdGrid Ct"><%=i+1%></td>
						<td class="tdGrid Lt"><a class="aEn" onclick="" class="aNormal">NONY_BOARD_<%=i+1%></a></td>
						<td class="tdGrid Lt"><%=i+1%>_Attached Files</td>
						<td class="tdGrid Ct">
							<i id="icnAction"class="fa fa-tasks fa-lg icnEn" tableName="NONY_BOARD" tableDesc="" onclick="" title="Action"></i>
						</td>
					</tr>
<%
				}
%>
				</tbody>
			</table>
			<div class="panel-footer">Panel footer</div>
		</div>
		<div class="panel panel-primary">
			<div class="panel-heading"><h3 class="panel-title">Data Table in Panel - within Body Panel</h3></div>
			<div id="divDataTablePanel2" class="panel-body">
				<div id="divDataGrid">
					<table id="tblFixedHeaderTable2" class="tblGrid sort autosort">
						<colgroup>
							<col width="5%"/>
							<col width="6%"/>
							<col width="32%"/>
							<col width="52%"/>
							<col width="5%"/>
						</colgroup>
						<thead>
							<tr>
								<th class="thGrid"><ui:icon id="icnCheck" className="fa-check-square-o fa-lg" title="Select to generate"/></th>
								<th class="thGrid sortable:numeric">Index</th>
								<th class="thGrid sortable:string">First Name</th>
								<th class="thGrid sortable:date">Date</th>
								<th class="thGrid">Action</th>
							</tr>
						</thead>
						<tbody>
<%
						for (int i=0; i<200; i++) {
%>
							<tr>
								<td class="tdGrid Ct"><input type="checkbox" id="chkForGenerate" name="chkForGenerate" class="chkEn inTblGrid" value="NONY_BOARD"/></td>
								<td class="tdGrid Ct"><%=i+1%></td>
								<td class="tdGrid Lt"><a class="aEn" onclick="" class="aNormal"><%=i+1%>_Dustin</a></td>
								<td class="tdGrid Lt"><%=CommonUtil.getSysdate("dd-MM-yyyy")%></td>
								<td class="tdGrid Ct"><ui:icon id="icnAction" className="fa-tasks fa-lg" title="Action"/></td>
							</tr>
<%
						}
%>
						</tbody>
					</table>
				</div>
			</div>
			<div class="panel-footer">Panel footer</div>
		</div>
		<div class="panel panel-default">
			<div class="panel-heading"><h3 class="panel-title">Table - class="tblDefault"</h3></div>
			<div class="panel-body">
				<table class="tblDefault withPadding" style="width:100%;">
					<colgroup>
						<col width="15%"/>
						<col width="35%"/>
						<col width="15%"/>
						<col width="35%"/>
					</colgroup>
					<tr>
						<th class="thDefault Lt">thDefault</th>
						<td class="tdDefault">tdDefault</td>
						<th class="thDefault Rt">thDefaultRt</th>
						<td class="tdDefault Rt">tdDefaultRt</td>
					</tr>
					<tr>
						<th class="thDefault Lt">thDefaultLt</th>
						<td class="tdDefault Lt">tdDefaultLt</td>
						<th class="thDefault Ct">thDefaultCt</th>
						<td class="tdDefault Ct">tdDefaultCt</td>
					</tr>
				</table>
			</div>
		</div>
		<div class="panel panel-success">
			<div class="panel-heading"><h3 class="panel-title">Table - class="tblEdit"</h3></div>
			<div class="panel-body">
				<table class="tblEdit" style="width:100%;">
					<caption class="captionEdit">Table class="tblEdit"</caption>
					<colgroup>
						<col width="15%"/>
						<col width="35%"/>
						<col width="15%"/>
						<col width="35%"/>
					</colgroup>
					<tr>
						<th class="thEdit">thEdit</th>
						<td class="tdEdit">tdEdit</td>
						<th class="thEdit Rt">thEditRt</th>
						<td class="tdEdit Rt">tdEditRt</td>
					</tr>
					<tr>
						<th class="thEdit Lt">thEditLt</th>
						<td class="tdEdit Lt">tdEditLt</td>
						<th class="thEdit Ct">thEditCt</th>
						<td class="tdEdit Ct">tdEditCt</td>
					</tr>
				</table>
			</div>
		</div>
	</div>
	<div id="div1" style="display:none">
		<div class="accordionFormElements">
			<div class="accordionGroup">
				<h3>Basic Input / Image / Icon / Button</h3>
				<div class="accordionContents">
					<div id="divBasicIcnImgBtn1">
						<label for="txtWithIcon" class="lblEn hor">Input text</label>
						<input type="text" id="txtWithIcon" name="txtWithIcon" class="txtEn hor" style="width:200px" checkName="txtTest1" maxlength="30" minlength="4" checkFlag="optional" mandatory placeholder="txtWithIcon"/>
						<i id="icnWithIconEn" class="fa fa-calendar icnEn hor" title="icnWithIcon"></i>
						<i id="icnWithIconDis" class="fa fa-calendar icnDis hor" title="icnWithIcon"></i>
	
						<div class="horGap20"></div>
	
						<label for="txtWithImage" class="lblEn hor mandatory">Input text</label>
						<input type="text" id="txtWithImage" name="txtWithImage" class="txtEn hor success" value="" style="width:200px" checkName="txtTest1" maxlength="30" minlength="4" checkFlag="optional" mandatory placeholder="txtWithImage"/>
						<img id="imgWithImageEn" src="<mc:cp key="imgThemeCom"/>/generate_Black.png" class="imgEn hor" title="imgWithImageEn"/>
						<img id="imgWithImageDis" src="<mc:cp key="imgThemeCom"/>/generate_Black.png" class="imgDis hor" title="imgWithImageDis"/>

						<div class="horGap20"></div>
	
						<label for="txtTaglib" class="lblEn hor">Input text</label>
						<ui:text name="txtTaglib" className="hor success" style="width:200px" checkName="txtTest1" maxlength="30" minlength="4" checkFlag="optional" options="mandatory" placeHolder="txtTaglib"/>
						<ui:img id="imgTaglib" src="<mc:cp key=imgThemeCom/>/generate_Black.png" className="hor" style="margin-top:6px" title="imgTaglib"/>
						<ui:icon id="icnTaglib" className="fa-calendar fa-lg hor" style="margin-top:8px" title="icnTaglib"/>
					</div>
					<div class="verGap20"></div>
					<div id="divBasicIcnImgBtn2">
						<label for="txtWithPngImage" class="lblEn hor mandatory">Input text</label>
						<input type="text" id="txtWithPngImage" name="txtWithPngImage" class="txtEn hor warning" value="" style="width:200px" placeholder="txtWithPngImage"/>
						<img id="imgWithPngImageEn" src="<mc:cp key="imgThemeCom"/>/icnDocument.png" class="imgEn hor" title="imgWithPngImageEn"/>
						<img id="imgWithPngImageDis" src="<mc:cp key="imgThemeCom"/>/icnDocument.png" class="imgDis hor" title="imgWithPngImageDis"/>
	
						<div class="horGap20"></div>
	
						<label for="txtWithButton" class="lblEn hor mandatory">Input text</label>
						<input type="text" id="txtWithButton" name="txtWithButton" class="txtEn hor error" value="" style="width:200px" checkName="txtTest1" maxlength="30" minlength="4" checkFlag="optional" mandatory placeholder="txtWithButton"/>
						<button id="btnPrintLog" type="button" class="btn btn-default"><i class="fa fa-save fa-lg"></i> Print Log</button>
						<a id="btnAnchor" type="button" class="btn btn-primary"><i class="fa fa-list-alt fa-lg"></i> <mc:msg key="button.com.create"/></a>
					</div>
				</div>
			</div>
			<div class="accordionGroup">
				<h3>Buttons</h3>
				<div class="accordionContents">
					<div id="divEnableButtons">
						<label for="txtWithEnableButtons" class="lblEn hor mandatory">Input text with Buttons Enabled</label>
						<input type="text" id="txtWithEnableButtons" name="txtWithEnableButtons" class="txtEn hor" value="" style="width:200px" placeholder="txtWithEnabledButtons"/>
						<button id="btnTestEmail" type="button" class="btn btn-default"><i class="fa fa-print fa-lg"></i>Test Email</button>
						<a id="btnPrimaryEn" type="button" class="btn btn-primary"><i class="fa fa-comment-o fa-lg"></i>Comment</a>
						<a id="btnSuccessEn" type="button" class="btn btn-success"><i class="fa fa-envelope-o fa-lg"></i>Envelope</a>
						<button id="btnInfoEn" type="button" class="btn btn-info"><i class="fa fa-calendar-o fa-lg"></i>Calendar</button>
						<button id="btnWarningEn" type="button" class="btn btn-warning"><i class="fa fa-key fa-lg"></i>Key</button>
						<button id="btnDangerEn" type="button" class="btn btn-danger"><i class="fa fa-search fa-lg"></i>Search</button>
					</div>
					<div class="verGap10"></div>
					<div id="divDisnableButtons">
						<label for="txtWithDisnableButtons" class="lblEn hor mandatory">Input text with Buttons Disnabled</label>
						<input type="text" id="txtWithDisnableButtons" name="txtWithDisnableButtons" class="txtDis hor" readonly="readonly" style="width:200px" placeholder="txtWithDisabledButtons"/>
						<button id="btnDefaultDis" type="button" class="btn btn-default" disabled><i class="fa fa-print fa-lg"></i> Print</button>
						<a id="btnPrimaryDis" type="button" class="btn btn-primary" disabled><i class="fa fa-comment-o fa-lg"></i> Comment</a>
						<a id="btnSuccessDis" type="button" class="btn btn-success" disabled><i class="fa fa-envelope-o fa-lg"></i> Envelope</a>
						<button id="btnInfoDis" type="button" class="btn btn-info" disabled><i class="fa fa-calendar-o fa-lg"></i> Calendar</button>
						<button id="btnWarningDis" type="button" class="btn btn-warning" disabled><i class="fa fa-key fa-lg"></i> Key</button>
						<button id="btnDangerDis" type="button" class="btn btn-danger" disabled><i class="fa fa-search fa-lg"></i> Search</button>
					</div>
					<div class="verGap10"></div>
					<div id="divButtonGroup">
						<label for="txtWithButtonGroup" class="lblEn hor">Input text with Button Group</label>
						<input type="text" id="txtWithButtonGroup" name="txtWithButtonGroup" class="txtDpl hor" disabled style="width:200px" placeholder="txtWithButtonGroup"/>
						<div class="btn-group">
							<button id="btnGroupDefaultEn" type="button" class="btn btn-default"><i class="fa fa-print fa-lg"></i> Print</button>
							<a id="btnGroupSuccessEn" type="button" class="btn btn-success"><i class="fa fa-envelope-o fa-lg"></i> Envelope</a>
							<button id="btnGroupWarningEn" type="button" class="btn btn-warning"><i class="fa fa-key fa-lg"></i> Key</button>
							<button id="btnGroupDangerDis" type="button" class="btn btn-danger" disabled><i class="fa fa-search fa-lg"></i> Search</button>
						</div>
					</div>
					<div class="verGap10"></div>
					<div id="divTagButton">
						<label for="txtWithTagButton" class="lblEn hor">Button by Tag Library</label>
						<input type="text" id="txtWithTagButton" name="txtWithTagButton" class="txtEn hor" style="width:200px" placeholder="txtWithTagButton"/>
						<ui:button id="btnTag1" caption="btnTag1" type="default" iconClass="fa-print" script="alert('btnTag1')"/>
						<ui:button id="btnTag2" caption="btnTag2" type="primary" iconClass="fa-comment-o" script="alert('btnTag2')"/>
						<ui:button id="btnTag3" caption="btnTag3" type="btn-success" status="disabled" iconClass="fa-envelope-o"/>
						<ui:button id="btnTag4" caption="btnTag4" type="warning" status="disabled"/>
						<ui:button id="btnTag5" caption="btnTag5" type="danger" iconClass="fa-search" script="alert('btnTag5');"/>
					</div>
					<div class="verGap10"></div>
					<div id="divTagButtonGroup">
						<label for="txtWithTagButtonGroup" class="lblEn hor">Button Group by Tag Library</label>
						<input type="text" id="txtWithTagButtonGroup" name="txtWithTagButtonGroup" class="txtEn hor" style="width:200px" placeholder="txtWithTagButtonGroup"/>
						<ui:buttonGroup id="btnGroupTag" groupClass="" style="">
							<ui:button id="btnGroupTag1" caption="btnGroupTag1" type="default" iconClass="fa-print" script="alert('btnGroupTag1')"/>
							<ui:button id="btnGroupTag2" caption="btnGroupTag2" type="primary" iconClass="fa-comment-o" script="alert('btnGroupTag2')"/>
							<ui:button id="btnGroupTag3" caption="btnGroupTag3" type="btn-success" status="disabled" iconClass="fa-envelope-o"/>
							<ui:button id="btnGroupTag4" caption="btnGroupTag4" type="warning" status="disabled"/>
							<ui:button id="btnGroupTag5" caption="btnGroupTag5" type="danger" iconClass="fa-search" script="alert('btnGroupTag5');"/>
						</ui:buttonGroup>
					</div>
				</div>
			</div>
			<div class="accordionGroup">
				<h3>Input type text / Textarea</h3>
				<div class="accordionContents">
					<table class="tblEdit" style="width:100%;">
						<caption class="captionEdit">Textbox</caption>
						<colgroup>
							<col width="10%"/>
							<col width="23%"/>
							<col width="10%"/>
							<col width="23%"/>
							<col width="10%"/>
							<col width="24%"/>
						</colgroup>
						<tr>
							<th class="thEdit Rt">txtEn</th>
							<td class="tdEdit">
								<input type="text" id="txtFormElementsEn" name="txtFormElementsEn" class="txtEn" placeholder="txtFormElementsEn"/>
							</td>
							<th class="thEdit Rt">txtDis</th>
							<td class="tdEdit">
								<input type="text" id="txtFormElementsDis" name="txtFormElementsDis" class="txtDis" disabled placeholder="txtFormElementsDis"/>
							</td>
							<th class="thEdit Rt">txtDpl</th>
							<td class="tdEdit">
								<input type="text" id="txtFormElementsDpl" name="txtFormElementsDpl" class="txtDpl" disabled placeholder="txtFormElementsDpl"/>
							</td>
						</tr>
						<tr>
							<th class="thEdit Rt">txtEn Success</th>
							<td class="tdEdit">
								<input type="text" id="txtFormElementsEnSuccess" name="txtFormElementsEnSuccess" class="txtEn success" placeholder="txtFormElementsEnSuccess"/>
							</td>
							<th class="thEdit Rt">txtEn Warning</th>
							<td class="tdEdit">
								<input type="text" id="txtFormElementsEnWarning" name="txtFormElementsEnWarning" class="txtEn warning" placeholder="txtFormElementsEnWarning"/>
							</td>
							<th class="thEdit Rt">txtEn Error</th>
							<td class="tdEdit">
								<input type="text" id="txtFormElementsEnError" name="txtFormElementsEnError" class="txtEn error" placeholder="txtFormElementsEnError"/>
							</td>
						</tr>
						<tr>
							<th class="thEdit Rt">Taglib Text</th>
							<td class="tdEdit">
								<ui:text name="txtTaglibEn" placeHolder="txtFormElementsEn"/>
							</td>
							<th class="thEdit Rt">Taglib txtDis</th>
							<td class="tdEdit">
								<ui:text name="txtTaglibDis" placeHolder="txtFormElementsDis" status="disabled"/>
							</td>
							<th class="thEdit Rt">Taglib txtDpl</th>
							<td class="tdEdit">
								<ui:text name="txtTaglibDpl" placeHolder="txtFormElementsDpl" status="display"/>
							</td>
						</tr>
					</table>
					<div class="horGap10"></div>
					<table class="tblEdit" style="width:100%;">
						<caption class="captionEdit">Textarea</caption>
						<colgroup>
							<col width="10%"/>
							<col width="23%"/>
							<col width="10%"/>
							<col width="23%"/>
							<col width="10%"/>
							<col width="24%"/>
						</colgroup>
						<tr>
							<th class="thEdit Rt">txaEn</th>
							<td class="tdEdit">
								<textarea id="txaFormElementsEn" name="txaFormElementsEn" class="txaEn" placeholder="txaFormElementsEn" style="height:80px">txaFormElementsEn</textarea>
							</td>
							<th class="thEdit Rt">txaDis</th>
							<td class="tdEdit">
								<textarea id="txaFormElementsDis" name="txaFormElementsDis" class="txaDis" disabled placeholder="txaFormElementsDis" style="height:80px">txaFormElementsDis</textarea>
							</td>
							<th class="thEdit Rt">txaDpl</th>
							<td class="tdEdit">
								<textarea id="txaFormElementsDpl" name="txaFormElementsDpl" class="txaDpl" disabled placeholder="txaFormElementsDpl" style="height:80px">txaFormElementsDis</textarea>
							</td>
						</tr>
						<tr>
							<th class="thEdit Rt">txaEn Success</th>
							<td class="tdEdit">
								<textarea id="txaFormElementsEnSuccess" name="txaFormElementsEnSuccess" class="txaEn success" placeholder="txaFormElementsEnSuccess" style="height:80px">txaFormElementsEnSuccess</textarea>
							</td>
							<th class="thEdit Rt">txaEn Warning</th>
							<td class="tdEdit">
								<textarea id="txaFormElementsEnWarning" name="txaFormElementsEnWarning" class="txaEn warning" placeholder="txaFormElementsEnWarning" style="height:80px">txaFormElementsEnWarning</textarea>
							</td>
							<th class="thEdit Rt">txaEn Error</th>
							<td class="tdEdit">
								<textarea id="txaFormElementsEnError" name="txaFormElementsEnError" class="txaEn error" placeholder="txaFormElementsEnError" style="height:80px">txaFormElementsEnError</textarea>
							</td>
						</tr>
						<tr>
							<th class="thEdit Rt">Taglib txaEn</th>
							<td class="tdEdit">
								<ui:txa name="txaTaglibEn" placeHolder="txaTaglibEn" style="height:80px" value="txaFormElementsEn"/>
							</td>
							<th class="thEdit Rt">Taglib txaDis</th>
							<td class="tdEdit">
								<ui:txa name="txaTaglibDis" placeHolder="txaTaglibDis" style="height:80px" value="txaFormElementsDis" status="disabled"/>
							</td>
							<th class="thEdit Rt">Taglib txaDpl</th>
							<td class="tdEdit">
								<ui:txa name="txaTaglibDpl" placeHolder="txaTaglibDpl" style="height:80px" value="txaFormElementsDis" status="display"/>
							</td>
						</tr>
					</table>
				</div>
			</div>
			<div class="accordionGroup">
				<h3>Checkbox / Radio</h3>
				<div class="accordionContents">
					<table class="tblEdit" style="width:100%;">
						<caption class="captionEdit">Checkbox</caption>
						<colgroup>
							<col width="17%"/>
							<col width="*"/>
						</colgroup>
						<tr>
							<th class="thEdit">Checkbox block</th>
							<td class="tdEdit">
								<label class="lblCheckEn block"><input type="checkbox" name="chkBlock" class="chkEn" value="chkBlock0"/>chkBlock0</label>
								<label class="lblCheckEn block"><input type="checkbox" name="chkBlock" class="chkEn" value="chkBlock1" checked/>chkBlock1</label>
								<label class="lblCheckDis block"><input type="checkbox" name="chkBlock" class="chkDis" value="chkBlock2" disabled/>chkBlock2</label>
								<label class="lblCheckEn block"><input type="checkbox" name="chkBlock" class="chkEn" value="chkBlock3"/>chkBlock3</label>
							</td>
						</tr>
						<tr>
							<th class="thEdit">Checkbox Inline</th>
							<td class="tdEdit">
								<label class="lblCheckEn"><input type="checkbox" name="chkInline" class="chkEn" value="chkInline0" onclick="alert($(this).val())"/>chkInline0</label>
								<label class="lblCheckEn"><input type="checkbox" name="chkInline" class="chkEn" value="chkInline1" checked/>chkInline1</label>
								<label class="lblCheckEn"><input type="checkbox" name="chkInline" class="chkEn" value="chkInline2"/>chkInline2</label>
								<label class="lblCheckEn"><input type="checkbox" name="chkInline" class="chkEn" value="chkInline3"/>chkInline3</label>
								<label class="lblCheckDis"><input type="checkbox" name="chkInline" class="chkDis" value="chkInline4" disabled/>chkInline4</label>
								<label class="lblCheckDis"><input type="checkbox" name="chkInline" class="chkDis" value="chkInline5" disabled/>chkInline5</label>
							</td>
						</tr>
						<tr>
							<th class="thEdit">Checkbox Inline with textbox</th>
							<td class="tdEdit">
								<input type="text" id="txtEnWithCheckbox" name="txtEnWithCheckbox" class="txtEn hor" style="width:200px;" placeholder="txtEnWithCheckbox"/>
								<label class="lblCheckEn withElem"><input type="checkbox" name="chkInlineWTextbox" class="chkEn" value="chkInlineWTextbox0"/>chkInlineWTextbox0</label>
								<label class="lblCheckEn withElem"><input type="checkbox" name="chkInlineWTextbox" class="chkEn" value="chkInlineWTextbox1" checked/>chkInlineWTextbox1</label>
								<label class="lblCheckDis withElem"><input type="checkbox" name="chkInlineWTextbox" class="chkDis" value="chkInlineWTextbox2" disabled/>chkInlineWTextbox2</label>
								<label class="lblCheckEn withElem"><input type="checkbox" name="chkInlineWTextbox" class="chkEn" value="chkInlineWTextbox3"/>chkInlineWTextbox3</label>
							</td>
						</tr>
						<tr>
							<th class="thEdit">Bootstrap Checkbox Block</th>
							<td class="tdEdit">
								<div class="checkbox">
									<label><input type="checkbox" name="chkBootstrapBlock" value="chkBootstrapBlock0"/>chkBootstrapBlock0</label>
								</div>
								<div class="checkbox disabled">
									<label><input type="checkbox" name="chkBootstrapBlock" value="chkBootstrapBlock1" disabled/>chkBootstrapBlock1</label>
								</div>
								<div class="checkbox">
									<label><input type="checkbox" name="chkBootstrapBlock" value="chkBootstrapBlock2"/>chkBootstrapBlock2</label>
								</div>
							</td>
						</tr>
						<tr>
							<th class="thEdit">Bootstrap Checkbox inline</th>
							<td class="tdEdit">
								<label class="checkbox-inline"><input type="checkbox" name="chkBootstrapInline" value="chkBootstrapInline0"/>chkBootstrapInline0</label>
								<label class="checkbox-inline disabled" ><input type="checkbox" name="chkBootstrapInline" value="chkBootstrapInline1" disabled/>chkBootstrapInline1</label>
								<label class="checkbox-inline"><input type="checkbox" name="chkBootstrapInline" value="chkBootstrapInline2"/>chkBootstrapInline2</label>
							</td>
						</tr>
					</table>
					<div class="verGap10"></div>
					<table class="tblEdit" style="width:100%;">
						<caption class="captionEdit">Radio</caption>
						<colgroup>
							<col width="17%"/>
							<col width="*"/>
						</colgroup>
						<tr>
							<th class="thEdit">Radio block</th>
							<td class="tdEdit">
								<label class="lblRadioEn block"><input type="radio" name="rdoBlock" class="rdoEn" value="rdoBlock0"/>rdoBlock0</label>
								<label class="lblRadioEn block"><input type="radio" name="rdoBlock" class="rdoEn" value="rdoBlock1" checked/>rdoBlock1</label>
								<label class="lblRadioDis block"><input type="radio" name="rdoBlock" class="rdoDis" value="rdoBlock2" disabled/>rdoBlock2</label>
								<label class="lblRadioEn block"><input type="radio" name="rdoBlock" class="rdoEn" value="rdoBlock3"/>rdoBlock3</label>
							</td>
						</tr>
						<tr>
							<th class="thEdit">Radio Inline</th>
							<td class="tdEdit">
								<label class="lblRadioEn"><input type="radio" name="rdoInline" class="rdoEn" value="rdoInline0"/>rdoInline0</label>
								<label class="lblRadioEn"><input type="radio" name="rdoInline" class="rdoEn" value="rdoInline1" checked/>rdoInline1</label>
								<label class="lblRadioEn"><input type="radio" name="rdoInline" class="rdoEn" value="rdoInline2"/>rdoInline2</label>
								<label class="lblRadioDis"><input type="radio" name="rdoInline" class="rdoDis" value="rdoInline3" disabled/>rdoInline3</label>
								<label class="lblRadioDis"><input type="radio" name="rdoInline" class="rdoDis" value="rdoInline4" disabled/>rdoInline4</label>
								<label class="lblRadioEn"><input type="radio" name="rdoInline" class="rdoEn" value="rdoInline5"/>rdoInline5</label>
							</td>
						</tr>
						<tr>
							<th class="thEdit">Radio Inline with textbox</th>
							<td class="tdEdit">
								<input type="text" id="txtEnWithRadio" name="txtEnWithRadio" class="txtEn hor" style="width:200px;" placeholder="txtEnWithRadio"/>
								<label class="lblRadioEn withElem"><input type="radio" name="rdoInlineWTextbox" class="rdoEn" value="rdoInlineWTextbox0"/>rdoInlineWTextbox0</label>
								<label class="lblRadioEn withElem"><input type="radio" name="rdoInlineWTextbox" class="rdoEn" value="rdoInlineWTextbox1" checked/>rdoInlineWTextbox1</label>
								<label class="lblRadioDis withElem"><input type="radio" name="rdoInlineWTextbox" class="rdoDis" value="rdoInlineWTextbox2" disabled/>rdoInlineWTextbox2</label>
								<label class="lblRadioEn withElem"><input type="radio" name="rdoInlineWTextbox" class="rdoEn" value="rdoInlineWTextbox3"/>rdoInlineWTextbox3</label>
							</td>
						</tr>
						<tr>
							<th class="thEdit">Bootstrap Radio Block</th>
							<td class="tdEdit">
								<div class="radio">
									<label><input type="radio" name="rdoBootstrapBlock" value="rdoBootstrapBlock0">rdoBootstrapBlock0</label>
								</div>
								<div class="radio disabled">
									<label><input type="radio" name="rdoBootstrapBlock" value="rdoBootstrapBlock1" disabled>rdoBootstrapBlock1</label>
								</div>
								<div class="radio">
									<label><input type="radio" name="rdoBootstrapBlock" value="rdoBootstrapBlock2">rdoBootstrapBlock2</label>
								</div>
							</td>
						</tr>
						<tr>
							<th class="thEdit">Bootstrap Radio inline</th>
							<td class="tdEdit">
								<label class="radio-inline"><input type="radio" name="rdoBootstrapInline" value="rdoBootstrapInline0">rdoBootstrapInline0</label>
								<label class="radio-inline disabled"><input type="radio" name="rdoBootstrapInline" value="rdoBootstrapInline1" disabled>rdoBootstrapInline1</label>
								<label class="radio-inline"><input type="radio" name="rdoBootstrapInline" value="rdoBootstrapInline2">rdoBootstrapInline2</label>
							</td>
						</tr>
					</table>
				</div>
			</div>
			<div class="accordionGroup">
				<h3>Selectbox</h3>
				<div class="accordionContents">
					<table class="tblEdit" style="width:100%;">
						<caption class="captionEdit">Selectbox - default selectbox & bootstrap-select</caption>
						<colgroup>
							<col width="17%"/>
							<col width="*"/>
						</colgroup>
						<tr>
							<th class="thEdit">Single Selection(Taglib)</th>
							<td class="tdEdit">
								<ui:text name="txtEnWithTaglibSelectbox" className="inline" style="width:200px;" placeHolder="txtEnWithTaglibSelectbox"/>
								<ui:select name="selTaglibSingleSelection1" className="hor">
									<ui:seloption value="SingleSelection1-0" text="SingleSelection1-0"/>
									<ui:seloption value="SingleSelection1-1" text="SingleSelection1-1"/>
									<ui:seloption value="SingleSelection1-2" text="SingleSelection1-2"/>
									<ui:seloption value="SingleSelection1-3" text="SingleSelection1-3" isDisabled="true"/>
									<ui:seloption value="SingleSelection1-4" text="SingleSelection1-4"/>
								</ui:select>
								<ui:select name="selTaglibSingleSelection2" className="hor error">
									<ui:seloption value="SingleSelection2-0" text="SingleSelection2-0"/>
									<ui:seloption value="SingleSelection2-1" text="SingleSelection2-1"/>
									<ui:seloption value="SingleSelection2-2" text="SingleSelection2-2"/>
									<ui:seloption value="SingleSelection2-3" text="SingleSelection2-3"/>
									<ui:seloption value="SingleSelection2-4" text="SingleSelection2-4"/>
								</ui:select>
								<ui:select name="selTaglibSingleSelection3" className="hor" status="disabled">
									<ui:seloption value="SingleSelection2-0" text="SingleSelection2-0"/>
									<ui:seloption value="SingleSelection2-1" text="SingleSelection2-1"/>
									<ui:seloption value="SingleSelection2-2" text="SingleSelection2-2"/>
									<ui:seloption value="SingleSelection2-3" text="SingleSelection2-3"/>
									<ui:seloption value="SingleSelection2-4" text="SingleSelection2-4"/>
								</ui:select>
								<ui:text name="txtDisWithTaglibSelectbox" className="inline" style="width:200px;" options="disabled" placeHolder="txtDisWithTaglibSelectbox"/>
							</td>
						</tr>
						<tr>
							<th class="thEdit">Single Selection(Default)</th>
							<td class="tdEdit">
								<input type="text" id="txtEnWithDefaultSelectbox" name="txtEnWithDefaultSelectbox" class="txtEn inline" style="width:200px;" placeholder="txtEnWithDefaultSelectbox"/>
								<select id="selDefaultSingleSelection1" name="selDefaultSingleSelection1" class="selSingleEn hor default">
									<option value="SingleSelection1-0">SingleSelection1-0</option>
									<option value="SingleSelection1-1">SingleSelection1-1</option>
									<option value="SingleSelection1-2" disabled>SingleSelection1-2</option>
									<option value="SingleSelection1-3">SingleSelection1-3</option>
									<option value="SingleSelection1-4">SingleSelection1-4</option>
								</select>
								<select id="selDefaultSingleSelection2" name="selDefaultSingleSelection2" class="selSingleEn hor error">
									<option value="SingleSelection2-0">SingleSelection2-0</option>
									<option value="SingleSelection2-1">SingleSelection2-1</option>
									<option value="SingleSelection2-2">SingleSelection2-2</option>
									<option value="SingleSelection2-3">SingleSelection2-3</option>
									<option value="SingleSelection2-4">SingleSelection2-4</option>
								</select>
								<select id="selDefaultSingleSelection3" name="selDefaultSingleSelection3" class="selSingleDis hor" disabled>
									<option value="SingleSelection3-0">SingleSelection3-0</option>
									<option value="SingleSelection3-1">SingleSelection3-1</option>
									<option value="SingleSelection3-2">SingleSelection3-2</option>
									<option value="SingleSelection3-3">SingleSelection3-3</option>
									<option value="SingleSelection3-4">SingleSelection3-4</option>
								</select>
								<input type="text" id="txtDisWithDefaultSelectbox" name="txtDisWithDefaultSelectbox" class="txtDis inline" style="width:200px;" disabled placeholder="txtDisWithDefaultSelectbox"/>
							</td>
						</tr>
						<tr>
							<th class="thEdit">Single Selection(Bootstrap)</th>
							<td class="tdEdit">
								<input type="text" id="txtEnWithSelectbox" name="txtEnWithSelectbox" class="txtEn inline" style="width:200px;" placeholder="txtEnWithSelectbox"/>
								<select id="selSingleSelection1" name="selSingleSelection1" class="bootstrapSelect hor default">
									<option value="SingleSelection1-0">SingleSelection1-0</option>
									<option value="SingleSelection1-1">SingleSelection1-1</option>
									<option value="SingleSelection1-2">SingleSelection1-2</option>
									<option value="SingleSelection1-3" disabled>SingleSelection1-3</option>
									<option value="SingleSelection1-4">SingleSelection1-4</option>
								</select>
								<select id="selSingleSelection2" name="selSingleSelection2" class="bootstrapSelect hor success">
									<option value="SingleSelection2-0">SingleSelection2-0</option>
									<option value="SingleSelection2-1">SingleSelection2-1</option>
									<option value="SingleSelection2-2">SingleSelection2-2</option>
									<option value="SingleSelection2-3">SingleSelection2-3</option>
									<option value="SingleSelection2-4">SingleSelection2-4</option>
								</select>
								<select id="selSingleSelection3" name="selSingleSelection3" class="bootstrapSelect hor" disabled>
									<option value="SingleSelection3-0">SingleSelection3-0</option>
									<option value="SingleSelection3-1">SingleSelection3-1</option>
									<option value="SingleSelection3-2">SingleSelection3-2</option>
									<option value="SingleSelection3-3">SingleSelection3-3</option>
									<option value="SingleSelection3-4">SingleSelection3-4</option>
								</select>
								<input type="text" id="txtDisWithSelectbox" name="txtDisWithSelectbox" class="txtDis inline" style="width:200px;" disabled placeholder="txtDisWithSelectbox"/>
							</td>
						</tr>
						<tr>
							<th class="thEdit">Multiple Selection(Default)</th>
							<td class="tdEdit">
								<select id="selDefaultMultipleSelection1" name="selDefaultMultipleSelection1" class="selMultiEn" style="width:200px;height:100px;" multiple="multiple">
									<option value="DefaultMultipleSelection1-0">MultipleSelection1-0</option>
									<option value="DefaultMultipleSelection1-1">MultipleSelection1-1</option>
									<option value="DefaultMultipleSelection1-2">MultipleSelection1-2</option>
									<option value="DefaultMultipleSelection1-3">MultipleSelection1-3</option>
									<option value="DefaultMultipleSelection1-4">MultipleSelection1-4</option>
								</select>
								<select id="selMultipleSelection2" name="selMultipleSelection2" class="selMultiDis" disabled style="width:200px;height:100px;" multiple="multiple">
									<option value="DefaultMultipleSelection2-0">MultipleSelection2-0</option>
									<option value="DefaultMultipleSelection2-1">MultipleSelection2-1</option>
									<option value="DefaultMultipleSelection2-2">MultipleSelection2-2</option>
									<option value="DefaultMultipleSelection2-3">MultipleSelection2-3</option>
									<option value="DefaultMultipleSelection2-4">MultipleSelection2-4</option>
								</select>
							</td>
						</tr>
						<tr>
							<th class="thEdit">Multiple Selection(Bootstrap)</th>
							<td class="tdEdit">
								<select id="selMultipleSelection1" name="selMultipleSelection1" class="bootstrapSelect warning" style="width:200px;height:100px;" multiple="multiple">
									<option value="MultipleSelection1-0">MultipleSelection1-0</option>
									<option value="MultipleSelection1-1">MultipleSelection1-1</option>
									<option value="MultipleSelection1-2">MultipleSelection1-2</option>
									<option value="MultipleSelection1-3">MultipleSelection1-3</option>
									<option value="MultipleSelection1-4">MultipleSelection1-4</option>
								</select>
								<select id="selMultipleSelection2" name="selMultipleSelection2" class="bootstrapSelect" disabled style="width:200px;height:100px;" multiple="multiple">
									<option value="MultipleSelection2-0">MultipleSelection2-0</option>
									<option value="MultipleSelection2-1">MultipleSelection2-1</option>
									<option value="MultipleSelection2-2">MultipleSelection2-2</option>
									<option value="MultipleSelection2-3">MultipleSelection2-3</option>
									<option value="MultipleSelection2-4">MultipleSelection2-4</option>
								</select>
							</td>
						</tr>
						<tr>
							<th class="thEdit">Option Group(Default)</th>
							<td class="tdEdit">
								<select id="selDefaultOptionGroup1" name="selDefaultOptionGroup1" class="selSingleEn">
									<optgroup label="German Cars">
										<option value="mercedes">Mercedes</option>
										<option value="audi">Audi</option>
										<option value="bmw">BMW</option>
									</optgroup>
									<optgroup label="Swedish Cars">
										<option value="volvo">Volvo</option>
										<option value="saab">Saab</option>
									</optgroup>
									<optgroup label="Korean Cars">
										<option value="hyundai">Hyundai</option>
										<option value="kia">Kia</option>
										<option value="ssangyong">Ssangyong</option>
									</optgroup>
									<optgroup label="Japnese Cars">
										<option value="toyota">Toyota</option>
										<option value="nissan">Nissan</option>
										<option value="mazda">Mazda</option>
										<option value="mitsubishi">Mitsubishi</option>
									</optgroup>
								</select>
							</td>
						</tr>
						<tr>
							<th class="thEdit">Option Group(Bootstrap)</th>
							<td class="tdEdit">
								<select id="selOptionGroup1" name="selOptionGroup1" class="bootstrapSelect">
									<optgroup label="German Cars">
										<option value="mercedes">Mercedes</option>
										<option value="audi">Audi</option>
										<option value="bmw">BMW</option>
									</optgroup>
									<optgroup label="Swedish Cars">
										<option value="volvo">Volvo</option>
										<option value="saab">Saab</option>
									</optgroup>
									<optgroup label="Korean Cars">
										<option value="hyundai">Hyundai</option>
										<option value="kia">Kia</option>
										<option value="ssangyong">Ssangyong</option>
									</optgroup>
									<optgroup label="Japnese Cars">
										<option value="toyota">Toyota</option>
										<option value="nissan">Nissan</option>
										<option value="mazda">Mazda</option>
										<option value="mitsubishi">Mitsubishi</option>
									</optgroup>
								</select>
							</td>
						</tr>
					</table>
				</div>
			</div>
		</div>
	</div>
	<div id="div2" style="display:none">
		<div class="accordionMiscellaneous">
			<div class="accordionGroup">
				<h3>File Select</h3>
				<div class="accordionContents">
					<table class="tblEdit" style="width:100%;">
						<caption class="captionEdit">File Select</caption>
						<colgroup>
							<col width="25%"/>
							<col width="*"/>
						</colgroup>
						<tr>
							<th class="thEdit">File Selection(Default)</th>
							<td class="tdEdit">
								<input type="file" id="fileDefault" name="fileDefault" class="file" value="" style="width:300px"/>
							</td>
						</tr>
						<tr>
							<th class="thEdit">File Selection(Modified by fileElement.js)</th>
							<td class="tdEdit">
								<input type="file" id="fileModified" name="fileModified" class="file" value="" style="width:300px"/>
							</td>
						</tr>
						<tr>
							<th class="thEdit">
								File Selection(Modified by fileElement.js)<br/>
								<ui:button id="btnAddFileSelection" caption="Add" type="default" iconClass="fa-plus"/>
							</th>
							<td class="tdEdit">
								<div id="divAttachedFile" style="height:114px;overflow-y:auto;"></div>
							</td>
						</tr>
					</table>
				</div>
			</div>
			<div class="accordionGroup">
				<h3>Popup / Dialog / Calendar</h3>
				<div class="accordionContents">
					<div id="divPopupAndDialog">
						<label for="btnPopupWindowExample1" class="lblEn hor">Popup Window Example</label>
						<ui:button id="btnPopupWindowExample1" caption="Popup Window" type="default" iconClass="glyphicon-new-window" buttonStyle="float:left"/>
						<div class="horGap30"></div>
						<label for="btnPopupDialogExample1" class="lblEn hor">Popup Dialog Example</label>
						<ui:button id="btnPopupDialogExample1" caption="Popup Dialog" type="default" iconClass="glyphicon-new-window" buttonStyle="float:left"/>
					</div>
					<div class="verGap20"></div>
					<div id="divCalendar">
						<label for="txtFromDate" class="lblEn hor">From Date</label>
						<input type="text" id="txtFromDate" name="txtFromDate" class="txtEn Ct hor" style="width:90px" value="<%//=CommonUtil.getSysdate(ConfigUtil.getProperty("defaultDate"))%>"/>
						<i id="icnFromDate" class="fa fa-calendar icnEn hor" title="From Date"></i>
						<div class="horGap20"></div>
						<label for="txtToDate" class="lblEn hor">To Date</label>
						<input type="text" id="txtToDate" name="txtToDate" class="txtEn Ct hor" style="width:90px" value="<%//=CommonUtil.getSysdate(ConfigUtil.getProperty("defaultDate"))%>"/>
						<input type="text" id="txtToTime" name="txtToTime" class="txtEn Ct hor" style="width:80px" value=""/>
						<input type="text" id="txtToDay" name="txtToDay" class="txtEn Ct hor" style="width:80px" value=""/>
						<input type="text" id="txtToWeek" name="txtToWeek" class="txtEn Ct hor" style="width:80px" value=""/>
						<i id="icnToDate" class="fa fa-calendar icnEn hor" title="To Date"></i>
					</div>
					<div class="verGap20"></div>
					<div id="divJQueryCalendar">
						<label for="txtJQueryDatePicker" class="lblEn hor">JQuery Date Picker</label>
						<input type="text" id="txtJQueryDatePicker" name="txtJQueryDatePicker" class="txtEn Ct hor" style="width:90px" value="<%//=CommonUtil.getSysdate(ConfigUtil.getProperty("defaultDate"))%>"/>
						<i id="icnJQueryDatePicker" class="fa fa-calendar icnEn hor" title="From Date"></i>
					</div>
					<div class="verGap20"></div>
					<div id="divJQueryCalendarInline">
						<div id="divJQueryInlineDatePicker"></div>
					</div>
				</div>
			</div>
			<div class="accordionGroup">
				<h3>Selectbox / Checkbox / Radio by Common Code</h3>
				<div class="accordionContents">
					<table class="tblEdit" style="width:100%;">
						<caption class="captionEdit">Selectbox - rendered by taglib with Common Code</caption>
						<colgroup>
							<col width="17%"/>
							<col width="*"/>
						</colgroup>
						<tr>
							<th class="thEdit">Single Selection</th>
							<td class="tdEdit">
								<ui:ccselect name="selComCodeSingle1" codeType="BOARD_TYPE" caption="==BOARD_TYPE==" className="hor" selectedValue="REPOSITORY" script="alert($(this).val());" source="framework"/>
								<ui:ccselect name="selComCodeSingle2" codeType="BOARD_TYPE" caption="==BOARD_TYPE==" className="hor error" status="disabled" selectedValue="BBS" source="framework"/>
							</td>
						</tr>
						<tr>
							<th class="thEdit">Multiple Selection(Default)</th>
							<td class="tdEdit">
								<ui:ccselect name="selComCodeMulti1" codeType="USER_THEME_TYPE" className="hor" style="width:200px;height:100px;" isMultiple="true" isBootstrap="false" source="framework"/>
								<ui:ccselect name="selComCodeMulti2" codeType="USER_THEME_TYPE" className="hor" style="width:200px;height:100px;" isMultiple="true" isBootstrap="false" status="disabled" source="framework"/>
							</td>
						</tr>
						<tr>
							<th class="thEdit">Multiple Selection(Bootstrap)</th>
							<td class="tdEdit">
								<ui:ccselect name="selComCodeMulti3" codeType="USER_THEME_TYPE" className="hor" isMultiple="true" source="framework"/>
								<ui:ccselect name="selComCodeMulti3" codeType="USER_THEME_TYPE" className="hor" isMultiple="true" status="disabled" source="framework"/>
							</td>
						</tr>
					</table>
					<div class="verGap10"></div>
					<table class="tblEdit" style="width:100%;">
						<caption class="captionEdit">Checkbox - rendered by taglib with Common Code</caption>
						<colgroup>
							<col width="17%"/>
							<col width="*"/>
						</colgroup>
						<tr>
							<th class="thEdit">Checkbox block</th>
							<td class="tdEdit">
								<ui:cccheck name="chkComCodeBlock1" codeType="BOARD_TYPE" selectedValue="REPOSITORY" disabledValue="REPOSITORY" displayType="block" script="alert($(this).val());" source="framework"/>
							</td>
						</tr>
						<tr>
							<th class="thEdit">Checkbox inline</th>
							<td class="tdEdit">
								<ui:cccheck name="chkComCodeInline1" codeType="BOARD_TYPE" selectedValue="REPOSITORY" disabledValue="BBS;NOTICE" script="" source="framework"/>
							</td>
						</tr>
						<tr>
							<th class="thEdit">Bootstrap Checkbox Block</th>
							<td class="tdEdit">
								<ui:cccheck name="chkComCodeBlock2" codeType="BOARD_TYPE" selectedValue="REPOSITORY" disabledValue="BBS;NOTICE;REPOSITORY" isBootstrap="true" displayType="block" source="framework"/>
							</td>
						</tr>
						<tr>
							<th class="thEdit">Bootstrap Checkbox Inline</th>
							<td class="tdEdit">
								<ui:cccheck name="chkComCodeInline2" codeType="BOARD_TYPE" selectedValue="REPOSITORY" disabledValue="REPOSITORY" isBootstrap="true" script="alert($(this).val());" source="framework"/>
							</td>
						</tr>
					</table>
					<div class="verGap10"></div>
					<table class="tblEdit" style="width:100%;">
						<caption class="captionEdit">Radio - rendered by taglib with Common Code</caption>
						<colgroup>
							<col width="17%"/>
							<col width="*"/>
						</colgroup>
						<tr>
							<th class="thEdit">Radio block</th>
							<td class="tdEdit">
								<ui:ccradio name="rdoComCodeBlock1" codeType="BOARD_TYPE" selectedValue="REPOSITORY" disabledValue="REPOSITORY" displayType="block" script="alert($(this).val());" source="framework"/>
							</td>
						</tr>
						<tr>
							<th class="thEdit">Radio inline</th>
							<td class="tdEdit">
								<ui:ccradio name="rdoComCodeInline1" codeType="BOARD_TYPE" selectedValue="REPOSITORY" disabledValue="NOTICE;REPOSITORY" script="alert($(this).val());" source="framework"/>
							</td>
						</tr>
						<tr>
							<th class="thEdit">Bootstrap Radio Block</th>
							<td class="tdEdit">
								<ui:ccradio name="rdoComCodeBlock2" codeType="BOARD_TYPE" selectedValue="REPOSITORY" disabledValue="BBS;NOTICE;REPOSITORY" isBootstrap="true" displayType="block" script="alert($(this).val());" source="framework"/>
							</td>
						</tr>
						<tr>
							<th class="thEdit">Bootstrap Radio Inline</th>
							<td class="tdEdit">
								<ui:ccradio name="rdoComCodeInline2" codeType="BOARD_TYPE" selectedValue="REPOSITORY" disabledValue="REPOSITORY" isBootstrap="true" script="alert($(this).val());" source="framework"/>
							</td>
						</tr>
					</table>
				</div>
			</div>
			<div class="accordionGroup">
				<h3>Image Slider</h3>
				<div class="accordionContents">
					<div id="slider">
						<div>
							<img data-u="image" src="<mc:cp key="slider"/>/img/sample/031.jpg"/>
<%-- 							<img data-u="thumb" src="<mc:cp key="slider"/>/img/sample/031-s190x90.jpg" /> --%>
<!-- 							<div u="thumb">Slide description #001</div> -->
						</div>
						<div>
							<img data-u="image" src="<mc:cp key="slider"/>/img/sample/032.jpg" />
<%-- 							<img data-u="thumb" src="<mc:cp key="slider"/>/img/sample/032-s190x90.jpg" /> --%>
<!-- 								<div u="thumb">Slide description #002</div> -->
						</div>
						<div>
							<img data-u="image" src="<mc:cp key="slider"/>/img/sample/033.jpg" />
<%-- 							<img data-u="thumb" src="<mc:cp key="slider"/>/img/sample/033-s190x90.jpg" /> --%>
<!-- 							<div u="thumb">Slide description #003</div> -->
						</div>
						<div>
							<img data-u="image" src="<mc:cp key="slider"/>/img/sample/034.jpg" />
<%-- 							<img data-u="thumb" src="<mc:cp key="slider"/>/img/sample/034-s190x90.jpg" /> --%>
<!-- 							<div u="thumb">Slide description #004</div> -->
						</div>
						<div>
							<img data-u="image" src="<mc:cp key="slider"/>/img/sample/035.jpg" />
<%-- 							<img data-u="thumb" src="<mc:cp key="slider"/>/img/sample/035-s190x90.jpg" /> --%>
<!-- 							<div u="thumb">Slide description #005</div> -->
						</div>
						<div>
							<img data-u="image" src="<mc:cp key="slider"/>/img/sample/036.jpg" />
<%-- 							<img data-u="thumb" src="<mc:cp key="slider"/>/img/sample/036-s190x90.jpg" /> --%>
<!-- 							<div u="thumb">Slide description #006</div> -->
						</div>
						<div>
							<img data-u="image" src="<mc:cp key="slider"/>/img/sample/037.jpg" />
<%-- 							<img data-u="thumb" src="<mc:cp key="slider"/>/img/sample/037-s190x90.jpg" /> --%>
<!-- 							<div u="thumb">Slide description #007</div> -->
						</div>
						<div>
							<img data-u="image" src="<mc:cp key="slider"/>/img/sample/038.jpg" />
<%-- 							<img data-u="thumb" src="<mc:cp key="slider"/>/img/sample/038-s190x90.jpg" /> --%>
<!-- 							<div u="thumb">Slide description #008</div> -->
						</div>
						<div>
							<img data-u="image" src="<mc:cp key="slider"/>/img/sample/039.jpg" />
<%-- 							<img data-u="thumb" src="<mc:cp key="slider"/>/img/sample/039-s190x90.jpg" /> --%>
<!-- 							<div u="thumb">Slide description #009</div> -->
						</div>
						<div>
							<img data-u="image" src="<mc:cp key="slider"/>/img/sample/040.jpg" />
<%-- 							<img data-u="thumb" src="<mc:cp key="slider"/>/img/sample/040-s190x90.jpg" /> --%>
<!-- 							<div u="thumb">Slide description #010</div> -->
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
<div id="divPagingArea"></div>
<%/************************************************************************************************
* Right & Footer
************************************************************************************************/%>
</div>
</div>
<div id="divBodyRight" class="ui-layout-east"><%@ include file="/zebra/example/common/include/bodyRight.jsp"%></div>
</div>
<div id="divFooterHolder" class="ui-layout-south"><%@ include file="/zebra/example/common/include/footer.jsp"%></div>
<%/************************************************************************************************
* Additional Elements
************************************************************************************************/%>
</form>
<%/************************************************************************************************
* Additional Form
************************************************************************************************/%>
<form id="fmHidden" name="fmHidden" method="post" action=""></form>
</body>
</html>