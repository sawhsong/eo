package zebra.example.common.webservice.rest.noticeboard;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

import org.apache.cxf.jaxrs.ext.multipart.MultipartBody;

@Path("/")
public interface NoticeBoardService {
	/*!
	 * Internal use
	 */
	@POST
	@Path("getList")
	public Response getList(String paramString) throws Exception;

	@POST
	@Path("getDetail")
	public Response getDetail(String paramString) throws Exception;

	@POST
	@Path("getAttachedFile")
	public Response getAttachedFile(String paramString) throws Exception;

	@POST
	@Path("download")
	public MultipartBody download(String paramString) throws Exception;

	@POST
	@Path("exeInsert")
	public Response exeInsert(MultipartBody paramMultipartBody) throws Exception;

	@POST
	@Path("exeUpdate")
	public Response exeUpdate(MultipartBody paramMultipartBody) throws Exception;

	@POST
	@Path("exeDelete")
	public Response exeDelete(String paramString) throws Exception;

	@POST
	@Path("exeExport")
	public MultipartBody exeExport(String paramString) throws Exception;


	/*!
	 * External use
	 */
/*
	@GET
	@Path("getList")
	public Response getList() throws Exception;
*/
}