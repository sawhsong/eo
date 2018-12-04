package project.common.schedule;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import project.common.module.commoncode.CommonCodeManager;
import project.conf.resource.ormapper.dao.SysBoard.SysBoardDao;
import project.conf.resource.ormapper.dto.oracle.SysBoard;
import zebra.util.CommonUtil;
import zebra.util.ConfigUtil;

public class BoardArticleCreationJob extends QuartzJobBean {
	private Logger logger = LogManager.getLogger(BoardArticleCreationJob.class);
	private SysBoardDao sysBoardDao;

	public void setSysBoardDao(SysBoardDao sysBoardDao) {
		this.sysBoardDao = sysBoardDao;
	}

	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
		addBoard();
	}

	private void addBoard() {
		String uid;

		try {
			SysBoard sysBoard = new SysBoard();
			uid = CommonUtil.uid();

			sysBoard.setArticleId(uid);
			if (CommonUtil.toInt(CommonUtil.substring(uid, uid.length()-3)) % 2 == 0) {
				sysBoard.setBoardType(CommonCodeManager.getCodeByConstants("BOARD_TYPE_NOTICE"));
			} else {
				sysBoard.setBoardType(CommonCodeManager.getCodeByConstants("BOARD_TYPE_FREE"));
			}
			sysBoard.setWriterId("0");
			sysBoard.setWriterName("BoardArticleCreationJob");
			sysBoard.setWriterEmail(ConfigUtil.getProperty("mail.default.from"));
			sysBoard.setWriterIpAddress("127.0.0.1");
			sysBoard.setArticleSubject("System generated article - "+CommonUtil.getSysdate("yyyy-MM-dd HH:mm:ss"));
			sysBoard.setArticleContents("BoardArticleCreationJob System generated article - "+CommonUtil.getSysdate("yyyy-MM-dd HH:mm:ss"));
			sysBoard.setInsertUserId("0");
			sysBoard.setInsertDate(CommonUtil.toDate(CommonUtil.getSysdate()));
			sysBoard.setParentArticleId("-1");

			sysBoardDao.insert(sysBoard);
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex);
		}
	}
}