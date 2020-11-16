package orange;

import java.sql.*;
import java.util.Date;


public class CommentDAO {

	private Connection conn;
	
	public CommentDAO(Connection conn) {
		this.conn = conn;
	}
	
	public boolean insertComment(CommentBean comment){
		
		try (PreparedStatement pstmt
				 = conn.prepareStatement(" INSERT INTO BAORD VALUES (?,?,?,?,?)");)
				
				{
			System.out.println("test");
			pstmt.setString(1, comment.getName());
			pstmt.setInt(2, comment.getStars());
//			java.util.Date date = new Date(); 
			java.sql.Date sqlDate = new java.sql.Date(comment.getDate().getTime());
			pstmt.setDate(3, sqlDate);
			pstmt.setString(4, comment.getContext());
			pstmt.setString(5, comment.getPhoto());
			int update = pstmt.executeUpdate();
			if(update>=1)
				return true;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
		
		
	}
}

// 1. class，會議類別
// 2. 專題目前能做的方向
// 3. 能怎麼做
		
	