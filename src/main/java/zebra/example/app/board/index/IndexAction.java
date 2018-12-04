package zebra.example.app.board.index;

import org.springframework.beans.factory.annotation.Autowired;

import zebra.example.common.extend.BaseAction;

public class IndexAction extends BaseAction {
	@Autowired
	private IndexBiz biz;

	public String getDefault() throws Exception {
		biz.getDefault(paramEntity);
		return "index";
	}
}