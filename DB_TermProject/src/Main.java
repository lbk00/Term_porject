
import java.sql.*; 
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;
import java.sql.Statement;
//C:\Users\LBK6661\Downloads\mysql-connector-j-8.2.0.tar.gz\mysql-connector-j-8.2.0
public class Main 
{
	/**
	 * @param args
	 */
	public static void main(String args[])
	{ 
		Scanner sc = new Scanner(System.in);
		int num = 1;
			while(num != 0) {
				
				
				System.out.println("--------------------------------");
				System.out.println("1.신발 리스트 출력  2. 신발 데이터 입력");
				System.out.println("3.신발 데이터 삭제  4. 신발 데이터 검색");
				System.out.println("5.공급자 출력      6. 구매자 출력");
				System.out.println("0.종료");
				System.out.println("--------------------------------");
				
				num = sc.nextInt();
				
				if (num == 1) {
					try{
						Class.forName("com.mysql.cj.jdbc.Driver"); 
						Connection con= DriverManager.getConnection(
									"jdbc:mysql://192.168.56.101:4567/shoes_db",
									"bklee","1234"); 
						Statement stmt = con.createStatement(); //select * from shoes_info ,(select shoes_id ,group_concat(color) as color from shoes_color group by shoes_id) B where shoes_info.shoes_id = B.shoes_id
						ResultSet rs = stmt.executeQuery("select * from shoes_info ,(select shoes_id ,group_concat(color) as color from shoes_color group by shoes_id) B where shoes_info.shoes_id = B.shoes_id");
						while(rs.next()) 
							System.out.println(rs.getInt(1) + " " + rs.getString(2) + " " + rs.getString(3) + " " + 
						rs.getString(4) + " " + rs.getString(5) + " " + rs.getInt(6) + " " + rs.getInt(7)+ " " + rs.getString(8)+ " " 
									+ rs.getString(9)+ " " + rs.getInt(10)+ " " + rs.getInt(11) + " " + rs.getString(13)); 
						con.close(); 
						}catch(Exception e){ 
							System.out.println(e);
						} 
					} 
				else if (num == 2) {
					try{
						
						System.out.println("추가할 신발을 입력하세요");
						
						System.out.println("shoes_id : ");
						int shoes_id  = sc.nextInt();
	
						System.out.println("brand : ");
						String brand  = sc.next();
						
						System.out.println("gender ( 남성용, 여성용, 공용 ): ");
						String gender  = sc.next();
						
						System.out.println("size : ");
						String size  = sc.next();
						
						System.out.println("type ( 운동화, 슬리퍼, 샌들, 구두, 부츠 ) : ");
						String type  = sc.next();
						
						System.out.println("original_cost: ");
						int original_cost  = sc.nextInt();
						
						System.out.println("shoes_name : ");
						String shoes_name = sc.next();
						
						System.out.println("condition ( 최상, 상, 중, 하 ) : ");
						String condition  = sc.next();
						
						System.out.println("color (여러색이면 ,로 구분): ");
						String color = sc.next();
						
						String[] strarr = color.split(",");
						
						Class.forName("com.mysql.cj.jdbc.Driver"); 
						Connection con= DriverManager.getConnection(
									"jdbc:mysql://192.168.56.101:4567/shoes_db",
									"bklee","1234"); 
						
						int discount_rate = 1;
						double price = 1;
						
						if (condition.equals("최상")) discount_rate = 10;
						else if (condition.equals("상")) discount_rate = 30;
						else if (condition.equals("중")) discount_rate = 40;
						else if (condition.equals("하")) discount_rate = 60;
						else condition = null;
						
						if (!(gender.equals("남성용") || gender.equals("여성용") || gender.equals("공용"))) gender = null;
						if (!(type.equals("운동화") || type.equals("슬리퍼") || type.equals("구두") || type.equals("샌들") || type.equals("부츠"))) type = null;
						
						
						
						double rate = discount_rate*0.01;
						double rate_cost = original_cost*rate;
						price = original_cost - rate_cost;
						
						Statement stmt = null;
						
						String res = "INSERT INTO shoes_info VALUES (" + shoes_id + ",'"+ brand + "' ,'"+ gender + "','"+ size + "','"+ type + "',"+ original_cost + ", 111 ,'"+shoes_name + "','" + condition + "',"+ discount_rate + ","+ price + ")";
						
						stmt = con.createStatement();
						
						stmt.executeUpdate(res);
						int col_cnt = strarr.length;
						
						for (int i=0 ; i< col_cnt ; i++) {
							stmt.executeUpdate("INSERT INTO shoes_color(shoes_id , color) VALUES (" + shoes_id + ",'" + strarr[i] + "')");
						}
								
						stmt.close();
						con.close();
						}catch(Exception e){ 
							System.out.println(e);
						} 
					} 
				else if (num == 3) {
					try{
						System.out.println("삭제할 신발의 shoes_id를 입력하세요:");
						int id;
						id = sc.nextInt();
						Class.forName("com.mysql.cj.jdbc.Driver"); 
						Connection con= DriverManager.getConnection(
									"jdbc:mysql://192.168.56.101:4567/shoes_db",
									"bklee","1234"); 
						
						String res = "DELETE FROM shoes_info WHERE shoes_id = " + id;
						Statement stmt = null;
						stmt = con.createStatement();
						stmt.executeUpdate(res);
						con.close(); 
						}catch(Exception e){ 
							System.out.println(e);
						} 
					} 
				else if (num == 4) {
					try{
						System.out.println("검색할 신발의 shoes_id를 입력하세요:");
						int id;
						id = sc.nextInt();
						Class.forName("com.mysql.cj.jdbc.Driver"); 
						Connection con= DriverManager.getConnection(
									"jdbc:mysql://192.168.56.101:4567/shoes_db",
									"bklee","1234"); 
						
						Statement stmt =con.createStatement(); 
						ResultSet rs=stmt.executeQuery("select * from shoes_info ,(select shoes_id ,group_concat(color) as color from shoes_color group by shoes_id having shoes_id = " + id  +") B where shoes_info.shoes_id = B.shoes_id ");
						while(rs.next()) 
							System.out.println(rs.getInt(1) + " " + rs.getString(2) + " " + rs.getString(3) + " " + 
									rs.getString(4) + " " + rs.getString(5) + " " + rs.getInt(6) + " " + rs.getInt(7)+ " " + rs.getString(8)+ " " 
												+ rs.getString(9)+ " " + rs.getInt(10)+ " " + rs.getInt(11) + " " + rs.getString(13)); 
						con.close(); 
						}catch(Exception e){ 
							System.out.println(e);
						} 
					} 
				else if (num == 5) {
					try{
						Class.forName("com.mysql.cj.jdbc.Driver"); 
						Connection con= DriverManager.getConnection(
									"jdbc:mysql://192.168.56.101:4567/shoes_db",
									"bklee","1234"); 
						Statement stmt =con.createStatement(); 
						ResultSet rs=stmt.executeQuery("SELECT * FROM provider"); 
						while(rs.next()) 
							System.out.println(rs.getInt(1) + " " + rs.getString(2) + " " + rs.getString(3)); 
						con.close(); 
						}catch(Exception e){ 
							System.out.println(e);
						} 
					} 
				else if (num == 6) {
					try{
						Class.forName("com.mysql.cj.jdbc.Driver"); 
						Connection con= DriverManager.getConnection(
									"jdbc:mysql://192.168.56.101:4567/shoes_db",
									"bklee","1234"); 
						Statement stmt =con.createStatement(); 
						ResultSet rs=stmt.executeQuery("SELECT * FROM customer"); 
						while(rs.next()) 
							System.out.println(rs.getInt(1) + " " + rs.getString(2)); 
						con.close(); 
						}catch(Exception e){ 
							System.out.println(e);
							} 
						} 
					} 
				
		}
		
}

