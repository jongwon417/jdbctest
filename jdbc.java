package gradleJdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

class Member{

    protected String userid;
    private String username;
    private int age;

    Member(String userid, String username, int age){
        this.userid = userid;
        this.username = username;
        this.age = age;
    }

    public String getUserid() {
        return userid;
    }
    public String getUsername() {
        return username;
    }    
    public int getAge() {
        return age;
    }

    public void setAge(int age) {
		this.age = age;
	}

	static class Builder{
		private String userid;
        private String username;
        private int age;

        public Builder userid(String userid){
            this.userid = userid;
            return this;
        }
        public Builder username(String username){
            this.username = username;
            return this;
        }
        public Builder age(int age){
            this.age = age;
            return this;
        }
        public Member build(){
            if(userid == null || username == null || age == 0)
                throw new IllegalStateException("»ý¼º¾ÈµÊ");
            return new Member(userid, username, age);
        }

    }
    
}

public class jdbc {

    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        
        //Member mem = new Member("°¡", "³ª", 1);
        //System.out.println(mem.getAge());
        
        
        String url = "jdbc:mariadb://127.0.0.1:3306/webdev";
        String userid = "root";
        String userpw = "root";
        String query = "select userid, username, age from tbl_test";

        Connection con = null;
        Statement stmt = null;
        ResultSet rs = null;

        Class.forName("org.mariadb.jdbc.Driver");
        con = DriverManager.getConnection(url, userid, userpw);
        stmt = con.createStatement();
        rs = stmt.executeQuery(query);

        List<Member> list = new ArrayList<>();

        while(rs.next()){
            //list.add(new Member(rs.getString("userid"), rs.getString("username"), rs.getInt("age")));
            list.add(new Member.Builder()
            .userid(rs.getString("userid"))
            .username(rs.getString("username"))
            .age(rs.getInt("age"))
            .build()
            );
        }
        for(Member mem:list){
            System.out.println(mem.getUserid()+" "+mem.getUsername()+" "+mem.getAge());
        }

        if(rs != null) rs.close();
        if(stmt != null) stmt.close();
        if(con != null) con.close();
	System.out.println("지발!");
    }    
}
