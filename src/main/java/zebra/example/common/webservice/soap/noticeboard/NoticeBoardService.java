package zebra.example.common.webservice.soap.noticeboard;

import java.util.List;

import javax.activation.DataHandler;
import javax.jws.WebService;

@WebService
public interface NoticeBoardService {
	public String getList(String paramString) throws Exception;
	public String getDetail(String paramString) throws Exception;
	public String getAttachedFile(String paramString) throws Exception;
	public DataHandler exeDownload(String paramString) throws Exception;
	public String exeInsert(String paramString, List<DataHandler> dataHandlerList) throws Exception;
	public String exeUpdate(String paramString, List<DataHandler> dataHandlerList) throws Exception;
	public String exeDelete(String paramString) throws Exception;
	public DataHandler exeExport(String paramString) throws Exception;
}