/**************************************************************************************************
 * Framework Generated DTO Source
 * - SYS_BOARD - Bulletin Board
 *************************************************************************************************/
package project.conf.resource.ormapper.dto.oracle;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.Date;

import zebra.data.DataSet;
import zebra.util.CommonUtil;
import project.common.extend.BaseDto;

@SuppressWarnings("unused")
public class SysBoard extends BaseDto implements Serializable {
	/**
	 * Columns
	 */
	private String articleId;
	private String ARTICLE_ID;
	private String boardType;
	private String BOARD_TYPE;
	private double hitCnt;
	private String HIT_CNT;
	private String parentArticleId;
	private String PARENT_ARTICLE_ID;
	private String writerName;
	private String WRITER_NAME;
	private String articleContents;
	private String ARTICLE_CONTENTS;
	private String articlePassword;
	private String ARTICLE_PASSWORD;
	private String articleSubject;
	private String ARTICLE_SUBJECT;
	private Date insertDate;
	private String INSERT_DATE;
	private String insertUserId;
	private String INSERT_USER_ID;
	private String isConfidential;
	private String IS_CONFIDENTIAL;
	private Date updateDate;
	private String UPDATE_DATE;
	private String updateUserId;
	private String UPDATE_USER_ID;
	private String writerEmail;
	private String WRITER_EMAIL;
	private String writerId;
	private String WRITER_ID;
	private String writerIpAddress;
	private String WRITER_IP_ADDRESS;
	private String insertUserName;
	private String INSERT_USER_NAME;
	private String updateUserName;
	private String UPDATE_USER_NAME;

	/**
	 * Constructor
	 */
	@SuppressWarnings("rawtypes")
	public SysBoard() throws Exception {
		Class cls = getClass();
		Field field[] = cls.getDeclaredFields();

		for (int i=0; i<field.length; i++) {
			if (field[i].getType().isInstance(dataSet) || field[i].getType().isInstance(updateColumnsDataSet) || field[i].getName().equals("updateColumnsDataSetHeader") ||
				field[i].getName().equals("FRW_VAR_PRIMARY_KEY") || field[i].getName().equals("FRW_VAR_DATE_COLUMN") ||
				field[i].getName().equals("FRW_VAR_NUMBER_COLUMN") || field[i].getName().equals("FRW_VAR_CLOB_COLUMN") ||
				field[i].getName().equals("FRW_VAR_DEFAULT_COLUMN") || field[i].getName().equals("FRW_VAR_DEFAULT_VALUE") ||
				!CommonUtil.isUpperCaseWithNumeric(CommonUtil.remove(field[i].getName(), "_"))) {
				continue;
			}

			dataSet.addName(field[i].getName());
		}

		dataSet.addRow();
		updateColumnsDataSet.addName(updateColumnsDataSetHeader);
		setFrwVarPrimaryKey("ARTICLE_ID");
		setFrwVarDateColumn("INSERT_DATE,UPDATE_DATE");
		setFrwVarNumberColumn("HIT_CNT");
		setFrwVarClobColumn("ARTICLE_CONTENTS");
		setFrwVarDefaultColumn("HIT_CNT,ARTICLE_CONTENTS,INSERT_DATE,IS_CONFIDENTIAL");
		setFrwVarDefaultValue("0,EMPTY_CLOB(),sysdate,N");
		setDefaultValue();
	}

	/**
	 * Accessors
	 */
	public String getArticleId() {
		return articleId;
	}

	public void setArticleId(String articleId) throws Exception {
		this.articleId = articleId;
		setValueFromAccessor("ARTICLE_ID", articleId);
	}

	public String getBoardType() {
		return boardType;
	}

	public void setBoardType(String boardType) throws Exception {
		this.boardType = boardType;
		setValueFromAccessor("BOARD_TYPE", boardType);
	}

	public double getHitCnt() {
		return hitCnt;
	}

	public void setHitCnt(double hitCnt) throws Exception {
		this.hitCnt = hitCnt;
		setValueFromAccessor("HIT_CNT", CommonUtil.toString(hitCnt));
	}

	public String getParentArticleId() {
		return parentArticleId;
	}

	public void setParentArticleId(String parentArticleId) throws Exception {
		this.parentArticleId = parentArticleId;
		setValueFromAccessor("PARENT_ARTICLE_ID", parentArticleId);
	}

	public String getWriterName() {
		return writerName;
	}

	public void setWriterName(String writerName) throws Exception {
		this.writerName = writerName;
		setValueFromAccessor("WRITER_NAME", writerName);
	}

	public String getArticleContents() {
		return articleContents;
	}

	public void setArticleContents(String articleContents) throws Exception {
		this.articleContents = articleContents;
		setValueFromAccessor("ARTICLE_CONTENTS", articleContents);
	}

	public String getArticlePassword() {
		return articlePassword;
	}

	public void setArticlePassword(String articlePassword) throws Exception {
		this.articlePassword = articlePassword;
		setValueFromAccessor("ARTICLE_PASSWORD", articlePassword);
	}

	public String getArticleSubject() {
		return articleSubject;
	}

	public void setArticleSubject(String articleSubject) throws Exception {
		this.articleSubject = articleSubject;
		setValueFromAccessor("ARTICLE_SUBJECT", articleSubject);
	}

	public Date getInsertDate() {
		return insertDate;
	}

	public void setInsertDate(Date insertDate) throws Exception {
		this.insertDate = insertDate;
		setValueFromAccessor("INSERT_DATE", CommonUtil.toString(insertDate));
	}

	public String getInsertUserId() {
		return insertUserId;
	}

	public void setInsertUserId(String insertUserId) throws Exception {
		this.insertUserId = insertUserId;
		setValueFromAccessor("INSERT_USER_ID", insertUserId);
	}

	public String getIsConfidential() {
		return isConfidential;
	}

	public void setIsConfidential(String isConfidential) throws Exception {
		this.isConfidential = isConfidential;
		setValueFromAccessor("IS_CONFIDENTIAL", isConfidential);
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) throws Exception {
		this.updateDate = updateDate;
		setValueFromAccessor("UPDATE_DATE", CommonUtil.toString(updateDate));
	}

	public String getUpdateUserId() {
		return updateUserId;
	}

	public void setUpdateUserId(String updateUserId) throws Exception {
		this.updateUserId = updateUserId;
		setValueFromAccessor("UPDATE_USER_ID", updateUserId);
	}

	public String getWriterEmail() {
		return writerEmail;
	}

	public void setWriterEmail(String writerEmail) throws Exception {
		this.writerEmail = writerEmail;
		setValueFromAccessor("WRITER_EMAIL", writerEmail);
	}

	public String getWriterId() {
		return writerId;
	}

	public void setWriterId(String writerId) throws Exception {
		this.writerId = writerId;
		setValueFromAccessor("WRITER_ID", writerId);
	}

	public String getWriterIpAddress() {
		return writerIpAddress;
	}

	public void setWriterIpAddress(String writerIpAddress) throws Exception {
		this.writerIpAddress = writerIpAddress;
		setValueFromAccessor("WRITER_IP_ADDRESS", writerIpAddress);
	}

	public String getInsertUserName() {
		return insertUserName;
	}

	public void setInsertUserName(String insertUserName) throws Exception {
		this.insertUserName = insertUserName;
		setValueFromAccessor("INSERT_USER_NAME", insertUserName);
	}

	public String getUpdateUserName() {
		return updateUserName;
	}

	public void setUpdateUserName(String updateUserName) throws Exception {
		this.updateUserName = updateUserName;
		setValueFromAccessor("UPDATE_USER_NAME", updateUserName);
	}

	/**
	 * Hibernate Methods - If the primary key is composed of multiple columns
	 */
	
	/**
	 * Framework Methods
	 */
	public void setDefaultValue() throws Exception {
		String columns[] = CommonUtil.split(getFrwVarDefaultColumn(), ",");
		String values[] = CommonUtil.split(getFrwVarDefaultValue(), ",");

		if (CommonUtil.isNotEmpty(columns)) {
			for (int i=0; i<columns.length; i++) {
				setValue(columns[i], values[i]);
			}
		}
	}

	@SuppressWarnings("rawtypes")
	public void setValue(String columnName, String value) throws Exception {
		Class cls = getClass();
		Field field[] = cls.getDeclaredFields();

		dataSet.setValue(dataSet.getRowCnt()-1, columnName, value);
		for (int i=0; i<field.length; i++) {
			if (field[i].getName().equals(CommonUtil.toCamelCaseStartLowerCase(columnName))) {
				if (CommonUtil.containsIgnoreCase(getFrwVarNumberColumn(), columnName)) {
					field[i].set(this, CommonUtil.toDouble(value));
				} else if (CommonUtil.containsIgnoreCase(getFrwVarDateColumn(), columnName)) {
					if (CommonUtil.equalsIgnoreCase(value, "SYSDATE")) {
						field[i].set(this, CommonUtil.toDate(CommonUtil.getSysdate()));
					} else {
						field[i].set(this, CommonUtil.toDate(value));
					}
				} else {
					field[i].set(this, value);
				}
			}
		}
	}

	public void setValues(DataSet dataSet) throws Exception {
		for (int i=0; i<dataSet.getColumnCnt(); i++) {
			setValue(dataSet.getName(i), dataSet.getValue(i));
		}
	}

	private void setValueFromAccessor(String columnName, String value) throws Exception {
		dataSet.setValue(dataSet.getRowCnt()-1, columnName, value);
	}

	public void setConvertedTypeValue(String columnName, String value) throws Exception {
		String numberColumn = "", dateColumn = "";

		numberColumn = getFrwVarNumberColumn();
		dateColumn = getFrwVarDateColumn();

		setValue(columnName, value);

		numberColumn += (CommonUtil.isEmpty(numberColumn)) ? CommonUtil.upperCase(columnName) : "," + CommonUtil.upperCase(columnName);
		dateColumn = CommonUtil.replace(dateColumn, columnName, "");

		setFrwVarNumberColumn(numberColumn);
		setFrwVarDateColumn(dateColumn);
	}

	public String getValue(String columnName) throws Exception {
		return dataSet.getValue(dataSet.getRowCnt()-1, columnName);
	}

	public void addUpdateColumn(String columnName, String columnValue) throws Exception {
		String dataType = "";

		if (CommonUtil.containsIgnoreCase(getFrwVarNumberColumn(), columnName)) {
			dataType = "Number";
		} else if (CommonUtil.containsIgnoreCase(getFrwVarDateColumn(), columnName)) {
			dataType = "Date";
		} else {
			dataType = "String";
		}

		addUpdateColumn(columnName, columnValue, dataType);
	}

	/**
	 * dataType : String / Number / Date
	 */
	public void addUpdateColumn(String columnName, String columnValue, String dataType) throws Exception {
		updateColumnsDataSet.addRow();
		updateColumnsDataSet.setValue(updateColumnsDataSet.getRowCnt()-1, "COLUMN_NAME", columnName);
		updateColumnsDataSet.setValue(updateColumnsDataSet.getRowCnt()-1, "COLUMN_VALUE", columnValue);
		updateColumnsDataSet.setValue(updateColumnsDataSet.getRowCnt()-1, "DATA_TYPE", CommonUtil.nvl(dataType, "String"));
	}

	public void addUpdateColumnFromField() throws Exception {
		for (int i=0; i<dataSet.getColumnCnt(); i++) {
			if (CommonUtil.isNotBlank(dataSet.getValue(i))) {
				if (CommonUtil.equalsIgnoreCase(dataSet.getName(i), "INSERT_DATE") && CommonUtil.equalsIgnoreCase(dataSet.getValue(i), "SYSDATE")) {
					continue;
				}
				addUpdateColumn(dataSet.getName(i), dataSet.getValue(i));
			}
		}
	}

	/**
	 * toString
	 */
	public String toString() {
		String str = "";

		str += "articleId : "+articleId+"\n";
		str += "boardType : "+boardType+"\n";
		str += "hitCnt : "+hitCnt+"\n";
		str += "parentArticleId : "+parentArticleId+"\n";
		str += "writerName : "+writerName+"\n";
		str += "articleContents : "+articleContents+"\n";
		str += "articlePassword : "+articlePassword+"\n";
		str += "articleSubject : "+articleSubject+"\n";
		str += "insertDate : "+insertDate+"\n";
		str += "insertUserId : "+insertUserId+"\n";
		str += "isConfidential : "+isConfidential+"\n";
		str += "updateDate : "+updateDate+"\n";
		str += "updateUserId : "+updateUserId+"\n";
		str += "writerEmail : "+writerEmail+"\n";
		str += "writerId : "+writerId+"\n";
		str += "writerIpAddress : "+writerIpAddress+"\n";
		str += "insertUserName : "+insertUserName+"\n";
		str += "updateUserName : "+updateUserName+"\n";

		return str;
	}

	/**
	 * toXmlString
	 */
	public String toXmlString() {
		String str = "";

		str += "<column name=\"articleId\" value=\""+articleId+"\">";
		str += "<column name=\"boardType\" value=\""+boardType+"\">";
		str += "<column name=\"hitCnt\" value=\""+hitCnt+"\">";
		str += "<column name=\"parentArticleId\" value=\""+parentArticleId+"\">";
		str += "<column name=\"writerName\" value=\""+writerName+"\">";
		str += "<column name=\"articleContents\" value=\""+articleContents+"\">";
		str += "<column name=\"articlePassword\" value=\""+articlePassword+"\">";
		str += "<column name=\"articleSubject\" value=\""+articleSubject+"\">";
		str += "<column name=\"insertDate\" value=\""+insertDate+"\">";
		str += "<column name=\"insertUserId\" value=\""+insertUserId+"\">";
		str += "<column name=\"isConfidential\" value=\""+isConfidential+"\">";
		str += "<column name=\"updateDate\" value=\""+updateDate+"\">";
		str += "<column name=\"updateUserId\" value=\""+updateUserId+"\">";
		str += "<column name=\"writerEmail\" value=\""+writerEmail+"\">";
		str += "<column name=\"writerId\" value=\""+writerId+"\">";
		str += "<column name=\"writerIpAddress\" value=\""+writerIpAddress+"\">";
		str += "<column name=\"insertUserName\" value=\""+insertUserName+"\">";
		str += "<column name=\"updateUserName\" value=\""+updateUserName+"\">";

		return str;
	}

	/**
	 * toJsonString
	 */
	public String toJsonString() {
		String str = "";

		str += "\"articleId\":\""+articleId+"\", ";
		str += "\"boardType\":\""+boardType+"\", ";
		str += "\"hitCnt\":\""+hitCnt+"\", ";
		str += "\"parentArticleId\":\""+parentArticleId+"\", ";
		str += "\"writerName\":\""+writerName+"\", ";
		str += "\"articleContents\":\""+articleContents+"\", ";
		str += "\"articlePassword\":\""+articlePassword+"\", ";
		str += "\"articleSubject\":\""+articleSubject+"\", ";
		str += "\"insertDate\":\""+insertDate+"\", ";
		str += "\"insertUserId\":\""+insertUserId+"\", ";
		str += "\"isConfidential\":\""+isConfidential+"\", ";
		str += "\"updateDate\":\""+updateDate+"\", ";
		str += "\"updateUserId\":\""+updateUserId+"\", ";
		str += "\"writerEmail\":\""+writerEmail+"\", ";
		str += "\"writerId\":\""+writerId+"\", ";
		str += "\"writerIpAddress\":\""+writerIpAddress+"\", ";
		str += "\"insertUserName\":\""+insertUserName+"\", ";
		str += "\"updateUserName\":\""+updateUserName+"\"";

		return str;
	}
}