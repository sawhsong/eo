package zebra.example.app.sample.uielement;

import org.springframework.beans.factory.annotation.Autowired;

import zebra.data.DataSet;
import zebra.example.common.extend.BaseAction;

public class UielementAction extends BaseAction {
	@Autowired
	private UielementBiz biz;

	public String getDefault() throws Exception {
		biz.getDefault(paramEntity);
		return "uielementList";
	}

	public String testEmail() throws Exception {
		try {
			biz.testEmail(paramEntity);
			paramEntity.setAjaxResponseDataSet((DataSet)this.paramEntity.getObject("resultDataSet"));
		} catch (Exception ex) {
		}
		setRequestAttribute("paramEntity", this.paramEntity);
		return "ajaxResponse";
	}
}