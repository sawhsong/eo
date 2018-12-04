package zebra.example.app.framework.dtogenerator;

import org.springframework.beans.factory.annotation.Autowired;

import zebra.config.MemoryBean;
import zebra.data.DataSet;
import zebra.data.ParamEntity;
import zebra.data.QueryAdvisor;
import zebra.example.common.bizservice.framework.ZebraFrameworkBizService;
import zebra.example.common.extend.BaseBiz;
import zebra.example.conf.resource.ormapper.dao.Dummy.DummyDao;
import zebra.exception.FrameworkException;
import zebra.util.CommonUtil;
import zebra.util.ConfigUtil;

public class DtoGeneratorBizImpl extends BaseBiz implements DtoGeneratorBiz {
	@Autowired
	private DummyDao dummyDao;
	@Autowired
	private ZebraFrameworkBizService zebraFrameworkBizService;

	public ParamEntity getDefault(ParamEntity paramEntity) throws Exception {
		DataSet requestDataSet = paramEntity.getRequestDataSet();
		QueryAdvisor queryAdvisor = paramEntity.getQueryAdvisor();
		String dataSourceNames[] = CommonUtil.split(ConfigUtil.getProperty("jdbc.multipleDatasource"), ConfigUtil.getProperty("delimiter.data"));

		try {
			queryAdvisor.setRequestDataSet(requestDataSet);

			paramEntity.setObject("datasourceDataSet", getDatasourceDataSet(dataSourceNames));
			paramEntity.setSuccess(true);
		} catch (Exception ex) {
			throw new FrameworkException(paramEntity, ex);
		}

		return paramEntity;
	}

	public ParamEntity getList(ParamEntity paramEntity) throws Exception {
		DataSet requestDataSet = paramEntity.getRequestDataSet();
		QueryAdvisor queryAdvisor = paramEntity.getQueryAdvisor();
		String defaultDataSourceUser = ConfigUtil.getProperty("jdbc.user.name");
		String dataSource = CommonUtil.nvl(requestDataSet.getValue("dataSource"), defaultDataSourceUser);
		String dataSourceNames[] = CommonUtil.split(ConfigUtil.getProperty("jdbc.multipleDatasource"), ConfigUtil.getProperty("delimiter.data"));

		try {
			queryAdvisor.setRequestDataSet(requestDataSet);
			queryAdvisor.setPagination(false);

			if (!CommonUtil.equalsIgnoreCase(dataSource, defaultDataSourceUser)) {
				dummyDao.setDataSourceName(dataSource);
				paramEntity.setAjaxResponseDataSet(dummyDao.getTableListDataSetByCriteriaForAdditionalDataSource(queryAdvisor));
			} else {
				dummyDao.resetDataSourceName();
				paramEntity.setAjaxResponseDataSet(dummyDao.getTableListDataSetByCriteria(queryAdvisor));
			}

			paramEntity.setObject("datasourceDataSet", getDatasourceDataSet(dataSourceNames));
			paramEntity.setTotalResultRows(queryAdvisor.getTotalResultRows());
			paramEntity.setSuccess(true);
		} catch (Exception ex) {
			throw new FrameworkException(paramEntity, ex);
		}

		return paramEntity;
	}

	public ParamEntity getDetail(ParamEntity paramEntity) throws Exception {
		DataSet requestDataSet = paramEntity.getRequestDataSet();
		String defaultDataSourceUser = ConfigUtil.getProperty("jdbc.user.name");
		String dataSource = CommonUtil.nvl(requestDataSet.getValue("dataSource"), defaultDataSourceUser);
		String tableName = requestDataSet.getValue("tableName");

		try {
			if (!CommonUtil.equalsIgnoreCase(dataSource, defaultDataSourceUser)) {
				dummyDao.setDataSourceName(dataSource);
				paramEntity.setObject("resultDataSet", dummyDao.getTableDetailDataSetByTableNameForAdditionalDataSource(tableName));
			} else {
				dummyDao.resetDataSourceName();
				paramEntity.setObject("resultDataSet", dummyDao.getTableDetailDataSetByTableName(tableName));
			}

			paramEntity.setSuccess(true);
		} catch (Exception ex) {
			throw new FrameworkException(paramEntity, ex);
		}

		return paramEntity;
	}

	public ParamEntity getGeneratorInfo(ParamEntity paramEntity) throws Exception {
		String compilePath = "/target/entityonline";
		String rootPath = CommonUtil.remove((String)MemoryBean.get("applicationRealPath"), compilePath);
		String dbVendor = CommonUtil.lowerCase(ConfigUtil.getProperty("db.vendor"));

		String dtoProject = CommonUtil.replace(ConfigUtil.getProperty("path.common.dtoProject"), "#DB_VENDOR#", dbVendor);
		String daoProject = ConfigUtil.getProperty("path.common.daoProject");
		String hibernateQueryProject = CommonUtil.replace(ConfigUtil.getProperty("path.source.hibernateQueryProject"), "#DB_VENDOR#", dbVendor);
		String mybatisQueryProject = CommonUtil.replace(ConfigUtil.getProperty("path.source.mybatisQueryProject"), "#DB_VENDOR#", dbVendor);

		String dtoFramework = CommonUtil.replace(ConfigUtil.getProperty("path.common.dtoFwk"), "#DB_VENDOR#", dbVendor);
		String daoFramework = ConfigUtil.getProperty("path.common.daoFwk");
		String hibernateQueryFramework = CommonUtil.replace(ConfigUtil.getProperty("path.source.hibernateQueryFwk"), "#DB_VENDOR#", dbVendor);
		String mybatisQueryFramework = CommonUtil.replace(ConfigUtil.getProperty("path.source.mybatisQueryFwk"), "#DB_VENDOR#", dbVendor);

		try {
			paramEntity.setObject("dtoProjectPath", rootPath + dtoProject);
			paramEntity.setObject("daoProjectPath", rootPath + daoProject);
			paramEntity.setObject("hibernateQueryProjectPath", rootPath + hibernateQueryProject);
			paramEntity.setObject("mybatisQueryProjectPath", rootPath + mybatisQueryProject);

			paramEntity.setObject("dtoFrameworkPath", rootPath + dtoFramework);
			paramEntity.setObject("daoFrameworkPath", rootPath + daoFramework);
			paramEntity.setObject("hibernateQueryFrameworkPath", rootPath + hibernateQueryFramework);
			paramEntity.setObject("mybatisQueryFrameworkPath", rootPath + mybatisQueryFramework);

			paramEntity.setSuccess(true);
		} catch (Exception ex) {
			throw new FrameworkException(paramEntity, ex);
		}

		return paramEntity;
	}

	public ParamEntity exeGenerate(ParamEntity paramEntity) throws Exception {
		DataSet requestDataSet = paramEntity.getRequestDataSet();
		DataSet tableInfoDataSet;

		String tableName = requestDataSet.getValue("tableName");
		String defaultDataSourceUser = ConfigUtil.getProperty("jdbc.user.name");
		String dataSource = CommonUtil.nvl(requestDataSet.getValue("dataSource"), defaultDataSourceUser);
		String system = requestDataSet.getValue("system");

		boolean dtoProject = CommonUtil.toBoolean(CommonUtil.nvl(requestDataSet.getValue("dtoProject"), "N"));
		boolean hibernateDtoConfigProject = CommonUtil.toBoolean(CommonUtil.nvl(requestDataSet.getValue("hibernateDtoConfigProject"), "N"));
		boolean mybatisDtoMapperConfigProject = CommonUtil.toBoolean(CommonUtil.nvl(requestDataSet.getValue("mybatisDtoMapperConfigProject"), "N"));
		boolean daoProject = CommonUtil.toBoolean(CommonUtil.nvl(requestDataSet.getValue("daoProject"), "N"));
		boolean hibernateDaoImplProject = CommonUtil.toBoolean(CommonUtil.nvl(requestDataSet.getValue("hibernateDaoImplProject"), "N"));
		boolean mybatisDaoImplProject = CommonUtil.toBoolean(CommonUtil.nvl(requestDataSet.getValue("mybatisDaoImplProject"), "N"));
		boolean daoSpringConfigProject = CommonUtil.toBoolean(CommonUtil.nvl(requestDataSet.getValue("daoSpringConfigProject"), "N"));
		boolean hibernateQueryProject = CommonUtil.toBoolean(CommonUtil.nvl(requestDataSet.getValue("hibernateQueryProject"), "N"));
		boolean mybatisQueryProject = CommonUtil.toBoolean(CommonUtil.nvl(requestDataSet.getValue("mybatisQueryProject"), "N"));

		boolean dtoFramework = CommonUtil.toBoolean(CommonUtil.nvl(requestDataSet.getValue("dtoFramework"), "N"));
		boolean hibernateDtoConfigFramework = CommonUtil.toBoolean(CommonUtil.nvl(requestDataSet.getValue("hibernateDtoConfigFramework"), "N"));
		boolean mybatisDtoMapperConfigFramework = CommonUtil.toBoolean(CommonUtil.nvl(requestDataSet.getValue("mybatisDtoMapperConfigFramework"), "N"));
		boolean daoFramework = CommonUtil.toBoolean(CommonUtil.nvl(requestDataSet.getValue("daoFramework"), "N"));
		boolean hibernateDaoImplFramework = CommonUtil.toBoolean(CommonUtil.nvl(requestDataSet.getValue("hibernateDaoImplFramework"), "N"));
		boolean mybatisDaoImplFramework = CommonUtil.toBoolean(CommonUtil.nvl(requestDataSet.getValue("mybatisDaoImplFramework"), "N"));
		boolean daoSpringConfigFramework = CommonUtil.toBoolean(CommonUtil.nvl(requestDataSet.getValue("daoSpringConfigFramework"), "N"));
		boolean hibernateQueryFramework = CommonUtil.toBoolean(CommonUtil.nvl(requestDataSet.getValue("hibernateQueryFramework"), "N"));
		boolean mybatisQueryFramework = CommonUtil.toBoolean(CommonUtil.nvl(requestDataSet.getValue("mybatisQueryFramework"), "N"));

		try {
			if (!CommonUtil.equalsIgnoreCase(dataSource, defaultDataSourceUser)) {
				dummyDao.setDataSourceName(dataSource);
			} else {
				dummyDao.resetDataSourceName();
			}

			tableInfoDataSet = dummyDao.getTableDetailDataSetByTableName(tableName);

			if (dtoProject || dtoFramework) {
				zebraFrameworkBizService.generateDto(system, requestDataSet, tableInfoDataSet);
			}

			if (hibernateDtoConfigProject || hibernateDtoConfigFramework) {
				zebraFrameworkBizService.generateHibernateDtoConfig(system, requestDataSet, tableInfoDataSet);
			}

			if (mybatisDtoMapperConfigProject || mybatisDtoMapperConfigFramework) {
				zebraFrameworkBizService.generateMybatisDtoMapper(system, requestDataSet, tableInfoDataSet);
				zebraFrameworkBizService.generateMybatisDtoMapperXml(system, requestDataSet, tableInfoDataSet);
			}

			if (daoProject || daoFramework) {
				zebraFrameworkBizService.generateDao(system, requestDataSet, tableInfoDataSet);
			}

			if (hibernateDaoImplProject || hibernateDaoImplFramework) {
				zebraFrameworkBizService.generateHDaoImpl(system, requestDataSet, tableInfoDataSet);
			}

			if (mybatisDaoImplProject || mybatisDaoImplFramework) {
				zebraFrameworkBizService.generateDaoImpl(system, requestDataSet, tableInfoDataSet);
				zebraFrameworkBizService.generateDaoMapper(system, requestDataSet, tableInfoDataSet);
			}

			if (daoSpringConfigProject || daoSpringConfigFramework) {
				zebraFrameworkBizService.generateDaoSpringConfig(system, requestDataSet, tableInfoDataSet);
			}

			if (hibernateQueryProject || hibernateQueryFramework) {
				zebraFrameworkBizService.generateHibernateQuery(system, requestDataSet, tableInfoDataSet);
			}

			if (mybatisQueryProject || mybatisQueryFramework) {
				zebraFrameworkBizService.generateMybatisQuery(system, requestDataSet, tableInfoDataSet);
			}

			paramEntity.setSuccess(true);
		} catch (Exception ex) {
			throw new FrameworkException(paramEntity, ex);
		}

		return paramEntity;
	}

	private DataSet getDatasourceDataSet(String[] dataSourceNames) throws Exception {
		DataSet dataSourceDataSet = new DataSet();

		dataSourceDataSet.addName(new String[] {"VALUE", "NAME"});
		for (int i=0; i<dataSourceNames.length; i++) {
			dataSourceDataSet.addRow();
			dataSourceDataSet.setValue(i, "VALUE", dataSourceNames[i]);
			dataSourceDataSet.setValue(i, "NAME", dataSourceNames[i]);
		}

		return dataSourceDataSet;
	}
}