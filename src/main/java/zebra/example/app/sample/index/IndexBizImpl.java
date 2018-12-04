package zebra.example.app.sample.index;

import zebra.data.DataSet;
import zebra.data.ParamEntity;
import zebra.example.common.extend.BaseBiz;
import zebra.exception.FrameworkException;

public class IndexBizImpl extends BaseBiz implements IndexBiz {
	public ParamEntity getDefault(ParamEntity paramEntity) throws Exception {
		try {
			paramEntity.setObject("resultDataSet", new DataSet());
		} catch (Exception ex) {
			throw new FrameworkException(paramEntity, ex);
		}
		return paramEntity;
	}
}