package project.common.module.commoncode;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import project.common.extend.BaseBiz;
import project.conf.resource.ormapper.dao.SysCommonCode.SysCommonCodeDao;
import zebra.config.MemoryBean;
import zebra.data.DataSet;
import zebra.util.CommonUtil;
import zebra.util.ConfigUtil;

public class CommonCodeManager extends BaseBiz implements CommonCodeManagerBiz {
	private static Logger logger = LogManager.getLogger(CommonCodeManager.class);
	private static SysCommonCodeDao sysCommonCodeDao;

	public static SysCommonCodeDao getSysCommonCodeDao() {
		return sysCommonCodeDao;
	}

	public static void setSysCommonCodeDao(SysCommonCodeDao sysCommonCodeDao) {
		CommonCodeManager.sysCommonCodeDao = sysCommonCodeDao;
	}

	public static void loadCommonCode() throws Exception {
		MemoryBean.set("commonCodeDataSet", sysCommonCodeDao.getAllActiveCommonCode());
		logger.info("[MemoryBean] - Project Common Code has been loaded.");
	}

	public static void reload() throws Exception {
		loadCommonCode();
	}

	public static DataSet getCodeTypeDataSet() throws Exception {
		DataSet ds = new DataSet();
		DataSet dsAll = (DataSet)MemoryBean.get("commonCodeDataSet");

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
		DataSet dsAll = (DataSet)MemoryBean.get("commonCodeDataSet");

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

	public static String getCodeMeaning(String codeType, String comCode) throws Exception {
		DataSet ds = new DataSet();
		String str = "";

		if (CommonUtil.isNotBlank(codeType) && CommonUtil.isNotBlank(comCode)) {
			ds = getCodeDataSetByCodeType(codeType);
			str = ds.getValue(ds.getRowIndex("COMMON_CODE", comCode), "CODE_MEANING");
		}

		return str;
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
		DataSet ds = (DataSet)MemoryBean.get("commonCodeDataSet");

		for (int i=0; i<ds.getRowCnt(); i++) {
			if (CommonUtil.equals(constants, ds.getValue(i, "PROGRAM_CONSTANTS"))) {
				rtn = ds.getValue(i, "COMMON_CODE");
				break;
			}
		}

		return rtn;
	}
}