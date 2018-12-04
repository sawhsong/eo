package zebra.example.app.main;

import org.springframework.beans.factory.annotation.Autowired;

import zebra.example.common.extend.BaseAction;

public class MainAction extends BaseAction {
	@Autowired
	private MainBiz biz;

	public String getDefault() throws Exception {
		biz.getDefault(paramEntity);
		return "index";
	}

	public String getGarbageColletion() throws Exception {
		this.biz.getGarbageColletion(paramEntity);
		return "garbageCollection";
	}
}