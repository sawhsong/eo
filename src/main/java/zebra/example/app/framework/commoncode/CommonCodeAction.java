package zebra.example.app.framework.commoncode;

import org.springframework.beans.factory.annotation.Autowired;

import zebra.example.common.extend.BaseAction;
import zebra.example.common.module.commoncode.ZebraCommonCodeManager;

public class CommonCodeAction extends BaseAction {
	@Autowired
	private CommonCodeBiz biz;

	public String getDefault() throws Exception {
		biz.getDefault(paramEntity);
		return "list";
	}

	public String getList() throws Exception {
		try {
			biz.getList(paramEntity);
		} catch (Exception ex) {
		}
		setRequestAttribute("paramEntity", this.paramEntity);
		return "ajaxResponse";
	}

	public String getInsert() throws Exception {
		biz.getInsert(this.paramEntity);
		return "insert";
	}

	public String exeInsert() throws Exception {
		try {
			biz.exeInsert(paramEntity);
			ZebraCommonCodeManager.reload();
		} catch (Exception ex) {
		}
		setRequestAttribute("paramEntity", this.paramEntity);
		return "ajaxResponse";
	}

	public String getDetail() throws Exception {
		biz.getDetail(paramEntity);
		return "detail";
	}

	public String getUpdate() throws Exception {
		biz.getUpdate(paramEntity);
		return "update";
	}

	public String exeUpdate() throws Exception {
		try {
			biz.exeUpdate(paramEntity);
			ZebraCommonCodeManager.reload();
		} catch (Exception ex) {
		}
		setRequestAttribute("paramEntity", this.paramEntity);
		return "ajaxResponse";
	}

	public String exeDelete() throws Exception {
		try {
			biz.exeDelete(paramEntity);
			ZebraCommonCodeManager.reload();
		} catch (Exception ex) {
		}
		setRequestAttribute("paramEntity", this.paramEntity);
		return "ajaxResponse";
	}

	public String exeExport() throws Exception {
		biz.exeExport(paramEntity);
		setRequestAttribute("paramEntity", paramEntity);
		return "export";
	}
}