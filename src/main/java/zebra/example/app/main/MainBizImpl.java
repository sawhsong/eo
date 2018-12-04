package zebra.example.app.main;

import java.awt.Color;
import java.awt.Font;
import java.io.File;
import java.net.InetAddress;

import org.jfree.chart.ChartRenderingInfo;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.entity.StandardEntityCollection;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;

import zebra.config.MemoryBean;
import zebra.data.DataSet;
import zebra.data.ParamEntity;
import zebra.example.common.extend.BaseBiz;
import zebra.exception.FrameworkException;
import zebra.util.CommonUtil;
import zebra.util.ConfigUtil;
import zebra.util.FileUtil;

public class MainBizImpl extends BaseBiz implements MainBiz {
	public ParamEntity getDefault(ParamEntity paramEntity) throws Exception {
		try {
			paramEntity.setObject("resultDataSet", new DataSet());
		} catch (Exception ex) {
			throw new FrameworkException(paramEntity, ex);
		}
		return paramEntity;
	}

	public ParamEntity getGarbageColletion(ParamEntity paramEntity) throws Exception {
		DataSet requestDataSet = paramEntity.getRequestDataSet();

		try {
			String mode = requestDataSet.getValue("hdnGarbageCollector");
			String realPath = (String)MemoryBean.get("applicationRealPath");
			String webPath = "/shared/repository/temp/vmchart"; //ConfigUtil.getProperty("path.dir.temp") + "/vmchart";
			String chartFileName = "/vmchart" + CommonUtil.uid() + ".png";

			DefaultPieDataset pieDataset = new DefaultPieDataset();
			File folder = FileUtil.getFileByPath(realPath + webPath);

			int chartWidth = 380;
			int chartHeight = 235;

			File[] files = folder.listFiles();
			for (int i = 0; i < files.length; i++) {
				files[i].delete();
			}

			if ("Run".equals(mode)) {
				System.gc();
				System.runFinalization();
			}

			Runtime rt = Runtime.getRuntime();
			long totalMemory = rt.totalMemory() / 1024L / 1024L;
			long freeMemory = rt.freeMemory() / 1024L / 1024L;
			long usingMemory = totalMemory - freeMemory;

			pieDataset.setValue(getMessage("fwk.garbageCollection.usingMemory", paramEntity) + "(" + usingMemory + " MB)", usingMemory);
			pieDataset.setValue(getMessage("fwk.garbageCollection.freeMemory", paramEntity) + "(" + freeMemory + " MB)", freeMemory);

			PiePlot plot = new PiePlot(pieDataset);
			plot.setOutlineVisible(false);

			JFreeChart chart = new JFreeChart("VM Memory status", new Font("Verdana", 1, 12), plot, true);
			chart.setBackgroundPaint(Color.WHITE);

			ChartRenderingInfo info = new ChartRenderingInfo(new StandardEntityCollection());
			ChartUtilities.saveChartAsPNG(new File(realPath + webPath + chartFileName), chart, chartWidth, chartHeight, info);

			paramEntity.setObject("totalMemory", Long.valueOf(totalMemory));
			paramEntity.setObject("freeMemory", Long.valueOf(freeMemory));
			paramEntity.setObject("usingMemory", Long.valueOf(usingMemory));
			paramEntity.setObject("hostName", InetAddress.getLocalHost().getHostName());
			paramEntity.setObject("hostAddress", InetAddress.getLocalHost().getHostAddress());
			paramEntity.setObject("measuredTime", CommonUtil.getSysdate(ConfigUtil.getProperty("format.dateTime.java")));
			paramEntity.setObject("chart", chart);
			paramEntity.setObject("chartFilePath", webPath + chartFileName);

			paramEntity.setSuccess(true);
		} catch (Exception ex) {
			throw new FrameworkException(paramEntity, ex);
		}

		return paramEntity;
	}
}