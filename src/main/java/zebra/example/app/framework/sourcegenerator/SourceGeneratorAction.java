package zebra.example.app.framework.sourcegenerator;

import org.springframework.beans.factory.annotation.Autowired;

import zebra.example.common.extend.BaseAction;

public class SourceGeneratorAction extends BaseAction {
	@Autowired
	private SourceGeneratorBiz biz;

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

	public String getGeneratorInfo() throws Exception {
		biz.getGeneratorInfo(paramEntity);
		return "info";
	}

	public String exeGenerate() throws Exception {
		try {
			biz.exeGenerate(paramEntity);
		} catch (Exception ex) {
		}
		setRequestAttribute("paramEntity", paramEntity);
		return "ajaxResponse";
	}
}