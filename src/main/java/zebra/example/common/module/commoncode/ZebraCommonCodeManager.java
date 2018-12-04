package zebra.example.common.module.commoncode;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import zebra.config.MemoryBean;
import zebra.data.DataSet;
import zebra.example.common.extend.BaseBiz;
import zebra.example.conf.resource.ormapper.dao.ZebraCommonCode.ZebraCommonCodeDao;
import zebra.util.CommonUtil;
import zebra.util.ConfigUtil;

public class ZebraCommonCodeManager extends BaseBiz implements ZebraCommonCodeManagerBiz {
	private static Logger logger = LogManager.getLogger(ZebraCommonCodeManager.class);
	private static ZebraCommonCodeDao zebraCommonCodeDao;

	public static ZebraCommonCodeDao getZebraCommonCodeDao() {
		return zebraCommonCodeDao;
	}

	public static void setZebraCommonCodeDao(ZebraCommonCodeDao zebraCommonCodeDao) {
		ZebraCommonCodeManager.zebraCommonCodeDao = zebraCommonCodeDao;
	}

	public static void loadCommonCode() throws Exception {
		MemoryBean.set("zebraCommonCodeDataSet", zebraCommonCodeDao.getAllActiveCommonCode());
		logger.info("[MemoryBean] - Framework Common Code has been loaded.");
	}

	public static void reload() throws Exception {
		loadCommonCode();
	}

	public static DataSet getCodeTypeDataSet() throws Exception {
		DataSet ds = new DataSet();
		DataSet dsAll = (DataSet)MemoryBean.get("zebraCommonCodeDataSet");

		ds.addName(dsAll.getNames());
		for (int i=0; i<dsAll.getRowCnt(); i++) {
			if ("0000000000".equals(dsAll.getValue(i, "COMMON_CODE"))) {
				ds.addRow();
				for (int j=0; j<ds.getColumnCnt(); j++) {
					ds.setValue(ds.getRowCnt()-1, j, dsAll.getValue(i, j));
				}
			}
		}

		return ds;
	}

	public static DataSet getCodeDataSetByCodeType(String codeType) throws Exception {
		DataSet ds = new DataSet();
		DataSet dsAll = (DataSet)MemoryBean.get("zebraCommonCodeDataSet");

		if (CommonUtil.isNotBlank(codeType)) {
			ds.addName(dsAll.getNames());
			for (int i=0; i<dsAll.getRowCnt(); i++) {
				if ((codeType.equals(dsAll.getValue(i, "CODE_TYPE"))) && (!"0000000000".equals(dsAll.getValue(i, "COMMON_CODE")))) {
					ds.addRow();
					for (int j=0; j<ds.getColumnCnt(); j++) {
						ds.setValue(ds.getRowCnt()-1, j, dsAll.getValue(i, j));
					}
				}
			}
		}

		return ds;
	}

	public static String getCodeDescription(String langCode, String codeType, String comCode) throws Exception {
		DataSet ds = new DataSet();
		String str = "";

		if (CommonUtil.isNotBlank(codeType) && CommonUtil.isNotBlank(comCode)) {
			ds = getCodeDataSetByCodeType(codeType);
			str = ds.getValue(ds.getRowIndex("COMMON_CODE", comCode), "DESCRIPTION_"+(CommonUtil.nvl(langCode, ConfigUtil.getProperty("etc.default.language"))).toUpperCase());
		}

		return str;
	}

	public static String getCodeDescription(String codeType, String comCode) throws Exception {
		return getCodeDescription("", codeType, comCode);
	}

	public static String getCodeByConstants(String constants) throws Exception {
		String rtn = "";
		DataSet ds = (DataSet)MemoryBean.get("zebraCommonCodeDataSet");

		for (int i=0; i<ds.getRowCnt(); i++) {
			if (CommonUtil.equals(constants, ds.getValue(i, "PROGRAM_CONSTANTS"))) {
				rtn = ds.getValue(i, "COMMON_CODE");
				break;
			}
		}

		return rtn;
	}
}