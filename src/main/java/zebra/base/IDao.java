package zebra.base;

public interface IDao {
	public boolean isMultipleDatasource();
	public void setMultipleDatasource(boolean isMultipleDatasource);
	public String getDataSourceName();
	public void setDataSourceName(String dataSourceName);
	public void resetDataSourceName();
	public void init();
}