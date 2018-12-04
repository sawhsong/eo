/**
 * Common Javascript Messages
 */
var com = {
	constants : {
		/*!
		 * Context menu class
		 */
		ctxClassPrefixButton:"actionButton",
		ctxClassPrefixGrid:"actionInGrid",
		ctxClassPrefixTheme:"theme",
		ctxClassPrefixHeader:"header",

		/*!
		 * CKEditor Toolbar
		 */
		toolbarDefault:"frameworkBasic",
		toolbarFull:"full"
	},
	caption : {
		/*!
		 * Context Menu Item
		 */
		// Logged-in User Profile
		ctxMyProfile:"사용자 정보",
		ctxLogOut:"접속종료",

		// Action item
		ctxSave:"저장",
		ctxCreate:"생성",
		ctxGenerate:"생성",
		ctxEdit:"수정",
		ctxUpdate:"수정",
		ctxDelete:"삭제",
		ctxReply:"답변",
		ctxDetail:"상세",
		ctxCancel:"취소",

		// Context Export Menu
		ctxExportExcelAll:"엑셀로 변환 (전체)",
		ctxExportExcelCurrentPage:"엑셀로 변환 (현재 페이지)",
		ctxExportWordAll:"워드로 변환 (전체)",
		ctxExportWordCurrentPage:"워드로 변환 (현재 페이지)",
		ctxExportPdfAll:"PDF로 변환 (전체)",
		ctxExportPdfCurrentPage:"PDF로 변환 (현재 페이지)",
		ctxExportHtmlAll:"HTML로 변환 (전체)",
		ctxExportHtmlCurrentPage:"HTML로 변환 (현재 페이지)",

		/*!
		 * Button caption
		 */
		ok:"확인",
		cancel:"취소",
		yes:"예",
		no:"아니오",
		close:"닫기",
		reload:"Reload",
		search:"조회",
		"new":"신규",
		add:"추가",
		create:"생성",
		"delete":"삭제",
		edit:"수정",
		save:"저장",
		reply:"답변",
		back:"뒤로",
		clear:"지우기",
		action:"액션",
		"export":"내려받기",
		generate:"생성",
		change:"변경",
		"return":"돌아가기",
		browse:"찾아보기"
	},
	header : {
		searchCriteria:"검색조건",
		dataEntry:"데이터 입력",
		action:"액션",
		insertUser:"입력자",
		insertDate:"입력일",
		updateUser:"수정자",
		updateDate:"수정일",
		selectToDelete:"삭제선택",
		fileExport:"파일변환",
		fileDownload:"파일 다운로드",
		popHeaderEdit:"신규/수정",
		popHeaderDetail:"상세정보",
		popHeaderResult:"처리결과",
		popHeaderTableInfo:"테이블 정보",
		popHeaderGenerator:"생성"
	},
	message : {
		loading:"요청 작업을 처리중 입니다.",
		mandatory:"은(는) 필수입력항목 입니다.",
		required:"은(는) 필수항목 입니다.",
		invalid:" 가(이) 유효하지 않습니다.",
		error:"에러가 발생되었습니다.",
		sessionTimeOut:"연결이 종료되었습니다. 다시 로그인 하시기 바랍니다.",
		monthNameShort:["1월", "2월", "3월", "4월", "5월", "6월", "7월", "8월", "9월", "10월", "11월", "12월"],
		monthNameLong:["January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"],
		dayOfWeekShort:["Mo", "Tu", "We", "Th", "Fr", "Sa", "Su"],
		dayOfWeekLong:["월요일", "화요일", "수요일", "목요일", "금요일", "토요일", "일요일"],

		/*!
		 * Common Messages
		 *   - I : Information / General notice
		 *   - Q : Question / Request confirmation
		 *   - W : Warning
		 *   - E : Error
		 */
		I000:"Information",
		I001:"검색된 데이터가 없습니다.",
		I002:"검색 조건을 선택하세요.",
		I990:"이 시스템은 <a target='_blank' href='https://www.google.com.au/chrome/browser/desktop/#eula' style='cursor:pointer;'>Google Chrome V58</a> 이상 버전과 <a target='_blank' href='https://www.mozilla.org/en-US/firefox/new/?scene=2' style='cursor:pointer;'>Firefox V53</a> 이상 버전에 최적화 되었습니다.",

		I801:"요청작업이 정상적으로 처리되었습니다.",
		I802:"요청작업을 처리중입니다.",
		I803:"접근권한이 없습니다.",

		I901:"파일 객체가 존재하지 않습니다.",
		I902:"선택된 항목이 없습니다.",
		I903:"성공적으로 로그인되었습니다. 환영합니다.",

		Q000:"Question",
		Q001:"저장 하시겠습니까?",
		Q002:"삭제 하시겠습니까?",
		Q003:"내보내기를 실행하시겠습니까?",

		Q901:"소스코드를 생성하시겠습니까?",
		Q902:"데이터 마이그레이션을 진행하시겠습니까?",

		W000:"Warning",
		W001:"연결이 종료되었습니다. 다시 로그인 하시기 바랍니다.",

		E000:"Error",
		E001:"요청작업 처리중 오류가 발생하였습니다.",

		E801:"요청작업 처리중 오류가 발생되었습니다.",

		E901:"Query ID가 지정되지 않았습니다.",
		E902:"Query object가 먼저 정의되어야 합니다.",
		E903:"버튼그룹 생성에 실패하였습니다. 버튼그룹 인자가 유효하지 않습니다.",
		E904:"해당 사용자의 이메일 주소가 아닙니다.",
		E906:"Where절이 정의되지 않았습니다.",
		E907:"사용자 ID가 존재하지 않습니다.",
		E908:"비밀번호가 올바르지 않습니다.",
		E909:"로그인에 실패하였습니다.",
		E910:"중복 데이터 입니다.",
		E911:"유효한 사용자 ID가 아닙니다.",
		E912:"이미 존재하는 로그인 ID 입니다."
	}
};