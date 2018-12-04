package zebra.example.app.framework.tablescript;

import org.springframework.beans.factory.annotation.Autowired;

import zebra.example.common.extend.BaseAction;

public class TableScriptAction extends BaseAction {
	@Autowired
	private TableScriptBiz biz;

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

	public String getDetail() throws Exception {
		biz.getDetail(paramEntity);
		return "detail";
	}

	public String getInsert() throws Exception {
		biz.getInsert(this.paramEntity);
		return "insert";
	}

	public String exeInsert() throws Exception {
		try {
			biz.exeInsert(paramEntity);
		} catch (Exception ex) {
		}
		setRequestAttribute("paramEntity", this.paramEntity);
		return "ajaxResponse";
	}

	public String getUpdate() throws Exception {
		biz.getUpdate(paramEntity);
		return "update";
	}

	public String exeDelete() throws Exception {
		try {
			biz.exeDelete(paramEntity);
		} catch (Exception ex) {
		}
		setRequestAttribute("paramEntity", this.paramEntity);
		return "ajaxResponse";
	}

	public String exeUpdate() throws Exception {
		try {
			biz.exeUpdate(paramEntity);
		} catch (Exception ex) {
		}
		setRequestAttribute("paramEntity", this.paramEntity);
		return "ajaxResponse";
	}
}