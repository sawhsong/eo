<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page errorPage = "/shared/page/error.jsp"%>
<%@ page import="java.util.*, net.sf.json.JSONObject, zebra.data.*, zebra.util.*"%>
<%@ page import="zebra.example.common.module.commoncode.*"%>
<%@ page import="zebra.example.common.module.menu.*"%>
<%@ page import="zebra.example.conf.resource.ormapper.dto.oracle.*"%>
<%@ page import="project.common.module.commoncode.*"%>
<%@ page import="project.common.module.menu.*"%>
<%@ page import="project.common.module.datahelper.*"%>
<%@ page import="project.conf.resource.ormapper.dto.oracle.*"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/xml" prefix="x"%>
<%@ taglib uri="/struts-tags" prefix="s"%>

<%@ taglib uri="/WEB-INF/tld/tagui.tld" prefix="ui"%>
<%@ taglib uri="/WEB-INF/tld/tagmc.tld" prefix="mc"%>
<%/************************************************************************************************
* Auto set Search Criteria
************************************************************************************************/%>
<%
	pageContext.setAttribute("searchCriteriaDataSet", (DataSet)request.getAttribute("searchCriteriaDataSet"));
%>