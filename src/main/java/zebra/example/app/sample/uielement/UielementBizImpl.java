package zebra.example.app.sample.uielement;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import zebra.data.DataSet;
import zebra.data.ParamEntity;
import zebra.example.common.extend.BaseBiz;
import zebra.exception.FrameworkException;

public class UielementBizImpl extends BaseBiz implements UielementBiz {
	public ParamEntity getDefault(ParamEntity paramEntity) throws Exception {
		try {
			paramEntity.setObject("resultDataSet", new DataSet());
			paramEntity.setSuccess(true);
		} catch (Exception ex) {
			throw new FrameworkException(paramEntity, ex);
		}
		return paramEntity;
	}

	public ParamEntity testEmail(ParamEntity paramEntity) throws Exception {
		DataSet ds = new DataSet();

		try {
			ds.addName(new String[] { "email", "result" });

			String regex = "^$|^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";

			ds.addRow();
			ds.setValue(ds.getRowCnt() - 1, "email", "");
			ds.addRow();
			ds.setValue(ds.getRowCnt() - 1, "email", "user@domain.com");
			ds.addRow();
			ds.setValue(ds.getRowCnt() - 1, "email", "user@domain.co.in");
			ds.addRow();
			ds.setValue(ds.getRowCnt() - 1, "email", "user.name@domain.com");
			ds.addRow();
			ds.setValue(ds.getRowCnt() - 1, "email", "username@domain.co.in");
			ds.addRow();
			ds.setValue(ds.getRowCnt() - 1, "email", ".username@yahoo.com");
			ds.addRow();
			ds.setValue(ds.getRowCnt() - 1, "email", "username@yahoo.com.");
			ds.addRow();
			ds.setValue(ds.getRowCnt() - 1, "email", "username@yahoo..com");

			Pattern pattern = Pattern.compile(regex);
			for (int i=0; i<ds.getRowCnt(); i++) {
				Matcher matcher = pattern.matcher(ds.getValue(i, "email"));
				ds.setValue(i, "result", Boolean.valueOf(matcher.matches()));
			}

			paramEntity.setSuccess(true);
			paramEntity.setObject("resultDataSet", ds);
			paramEntity.setMessage("I801", getMessage("I801"));
		} catch (Exception ex) {
			throw new FrameworkException(paramEntity, ex);
		}

		return paramEntity;
	}
}