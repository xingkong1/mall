package mall.Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;


import java.util.ArrayList;
import java.util.List;

import mall.bean.Category;
import mall.util.DBUtil;

/**
 * 用于建立对于Category对象的ORM映射
 * 
 * @author xingkong
 * 
 */
public class CategoryDAO {

	/**
	 * 插入Category对象
	 * @param category
	 */
	public void add(Category category){
		String sql="insert into category values(null,?)";
		try(Connection conn=DBUtil.getConnection();
		PreparedStatement pStatement=conn.prepareStatement(sql);) {
			pStatement.setString(1, category.getName());
			pStatement.execute();
			ResultSet rs=pStatement.getGeneratedKeys();
			while(rs.next()){
				int id=rs.getInt(1);
				category.setId(id);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 删除Category对象
	 * @param id
	 */
	public void delete(int id){
		try(Connection conn=DBUtil.getConnection();
				Statement statement=conn.createStatement();) {
				   statement.execute("delete from category where id="+id);
				} catch (Exception e) {
					e.printStackTrace();
				}
	}
	
	/**
	 * 修改Category对象
	 * @param id
	 */
	public void update(Category category){
		String sql="update category set name=? where id=?";
		try(Connection conn=DBUtil.getConnection();
				PreparedStatement pStatement=conn.prepareStatement(sql);) {
				 pStatement.setString(1, category.getName());
				 pStatement.setInt(2, category.getId());
				 pStatement.execute();
				} catch (Exception e) {
					e.printStackTrace();
				}
	}
	
	/**
	 * 根据id获取Category对象
	 * @param id
	 * @return
	 */
	public Category get(int id){
		String sql="select * from category where id=?";
		Category category=null;
		try(Connection conn=DBUtil.getConnection();
				PreparedStatement pStatement=conn.prepareStatement(sql);) {
				 pStatement.setInt(1, id);
				 ResultSet rs=pStatement.executeQuery();
				 while(rs.next()){
					 category=new Category();
					 category.setId(id);
					 category.setName(rs.getString(2));
				 }
				} catch (Exception e) {
					e.printStackTrace();
				}
		return category;
	}
	
	/**
	 * 分页查询
	 * @param args
	 */
	public List<Category> list(int start,int count){
		String sql="select * from category order by id desc limit ?,?";
		List<Category> categories=new ArrayList<>();
		try(Connection conn=DBUtil.getConnection();
				PreparedStatement pStatement=conn.prepareStatement(sql);) {
				pStatement.setInt(1, start);
				pStatement.setInt(2, count);
				ResultSet rs=pStatement.executeQuery();
				while(rs.next()){
					Category category=new Category();
					category.setId(rs.getInt(1));
					category.setName(rs.getString(2));
					categories.add(category);
				}
				} catch (Exception e) {
					e.printStackTrace();
				}
		return categories;
	}
	
	/**
	 * 查询所有的Category对象
	 * @return
	 */
	public List<Category> list(){
		return list(0, Short.MAX_VALUE);
	}
	/**
	 * 获取Category对象总数
	 * @return
	 */
	public int getTotal(){
		int count=0;
		try(Connection conn=DBUtil.getConnection();
				Statement statement=conn.createStatement();) {
			    String sql="select count(*) from category";
				   ResultSet rs=statement.executeQuery(sql);
				   while(rs.next()){
					  count= rs.getInt(1);
				   }
				} catch (Exception e) {
					e.printStackTrace();
				}
		return count;
	}
	
	//测试方法
	public static void main(String[] args) {
		Category category=new Category();
		category.setName("笔记本");
		category.setId(3);
		CategoryDAO dao=new CategoryDAO();
		dao.add(category);
		System.out.println(dao.list(0,1));
		System.out.println(dao.list());
		System.out.println(dao.getTotal());
	}

}
