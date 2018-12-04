package zebra.example.app.framework.dtogenerator;

import org.springframework.beans.factory.annotation.Autowired;

import zebra.data.DataSet;
import zebra.example.common.extend.BaseAction;

public class DtoGeneratorAction extends BaseAction {
	@Autowired
	private DtoGeneratorBiz biz;

	public String getDefault() throws Exception {
		biz.getDefault(paramEntity);
		return "list";
	}

	public String getList() throws Exception {
		try {
			biz.getList(paramEntity);
		} catch (Exception ex) {
		}
		setRequestAttribute("paramEntity", paramEntity);
		return "ajaxResponse";
	}

	public String getDetail() throws Exception {
		biz.getDetail(paramEntity);
		return "detail";
	}

	public String getGeneratorInfo() throws Exception {
		biz.getGeneratorInfo(paramEntity);
		return "info";
	}

	public String exeGenerate() throws Exception {
		try {
			biz.exeGenerate(paramEntity);
			paramEntity.setAjaxResponseDataSet((DataSet)paramEntity.getObject("resultDataSet"));
		} catch (Exception ex) {
		}
		setRequestAttribute("paramEntity", paramEntity);
		return "ajaxResponse";
	}
}